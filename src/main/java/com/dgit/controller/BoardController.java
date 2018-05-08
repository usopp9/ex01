package com.dgit.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dgit.domain.BoardVO;
import com.dgit.domain.Criteria;
import com.dgit.domain.PageMaker;
import com.dgit.service.BoardService;

@RequestMapping("/board/*")
@Controller
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	@Autowired
	private BoardService service;
	
	
	@RequestMapping(value="register", method=RequestMethod.GET)
	public void registerGet(){
		logger.info("board register Get.......");
		/*url주소와jsp주소가 같기때문에 생략해도 됨*/
		
		//return "/board/register";
	}
	@RequestMapping(value="register", method=RequestMethod.POST)
	public String registerPost(BoardVO vo) throws Exception{
		logger.info("board register Post.......");
		logger.info(vo.toString());
		service.regist(vo);
			
		return "redirect:/board/listAll";
	}
	@RequestMapping(value="/listAll", method=RequestMethod.GET)
	public void listAll(Model model) throws Exception{
		logger.info("board listAll Get.......");
		
		List<BoardVO> list = service.listAll();
		model.addAttribute("list", list);
	}
	
	@RequestMapping(value="/read",method=RequestMethod.GET)
	public void read(Model model, int bno) throws Exception{
		logger.info("board read Get.......");
		logger.info("bno : "+bno);
		
		BoardVO vo = service.read(bno);
		service.readCnt(bno);
		model.addAttribute("boardVO",vo);		
	}
	
	@RequestMapping(value="/remove",method=RequestMethod.GET)
	public String delete(int bno) throws Exception{
		logger.info("board delete Get.......");
		service.remove(bno);
		return "redirect:/board/listAll";
	}
	
	@RequestMapping(value="/modify",method=RequestMethod.GET)
	public void modify(int bno,Model model) throws Exception{
		logger.info("board modify Get.......");
		
		BoardVO vo = service.read(bno);
		model.addAttribute("boardVO", vo);
		
	}
	@RequestMapping(value="/modify",method=RequestMethod.POST)
	public String modifyPost(BoardVO vo) throws Exception{
		logger.info("board modify Post.......");
		
		service.modify(vo);
		
	/*	model.addAttribute("bno",vo.getBno());
	 *  //?bno=2 같은 의미
	 */		
		return "redirect:/board/read?bno="+vo.getBno();	
	}
	/*---------------------------------------페이징처리------------------------------------------------------------------*/
	//ex01/board/listPage
	@RequestMapping(value="/listPage", method=RequestMethod.GET)
	public void listPage(Model model,Criteria cri) throws Exception{
		logger.info("board listPage Get.......");
		
		List<BoardVO> list = service.listCriteria(cri);
		model.addAttribute("list", list);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.totalCount());
		model.addAttribute("pageMaker", pageMaker);
	}
	
	@RequestMapping(value="/readPage",method=RequestMethod.GET)
	public void readPage(Model model, int bno,@ModelAttribute("cri") Criteria cri) throws Exception{
		logger.info("board readPage Get.......");
		logger.info("bno : "+bno);
		logger.info("cri : "+cri.toString());
		
		BoardVO vo = service.read(bno);
		service.readCnt(bno);
		model.addAttribute("boardVO",vo);		
	}
	
	@RequestMapping(value="/removePage",method=RequestMethod.GET)
	public String removePage(int bno,int page) throws Exception{
		logger.info("board removePage Get.......");
		service.remove(bno);
		return "redirect:/board/listPage?page="+page;
	}
	
	@RequestMapping(value="/modifyPage",method=RequestMethod.GET)
	public void modifyPageGet(int bno,Model model,@ModelAttribute("cri") Criteria cri) throws Exception{
		logger.info("board modifyPage Get.......");
		
		BoardVO vo = service.read(bno);
		model.addAttribute("boardVO", vo);
		
	}
	
	@RequestMapping(value="/modifyPage",method=RequestMethod.POST)
	public String modifyPage(BoardVO vo,int page) throws Exception{
		logger.info("board modifyPage Post.......");
		
		service.modify(vo);
		
	/*	model.addAttribute("bno",vo.getBno());
	 *  //?bno=2 같은 의미
	 */		
		return "redirect:/board/readPage?bno="+vo.getBno()+"&page="+page;	
	}
}
