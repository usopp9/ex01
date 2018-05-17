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
import com.dgit.domain.SearchCriteria;
import com.dgit.service.BoardService;

@Controller
@RequestMapping("/sboard/*")//page +  search
public class SearchBoardController {
	private static final Logger logger = LoggerFactory.getLogger(SearchBoardController.class);
	@Autowired
	private BoardService service;
	
	@RequestMapping(value="/listPage", method=RequestMethod.GET)
	public void listPage(Model model,@ModelAttribute("cri") SearchCriteria cri) throws Exception{
		logger.info("board listPage Get.......");
		logger.info("cri : "+cri.toString());
		
		List<BoardVO> list = service.listSearchCriteria(cri);
		model.addAttribute("list", list);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.totalSearchCount(cri));
		model.addAttribute("pageMaker", pageMaker);
	}
	
	@RequestMapping(value="/readPage",method=RequestMethod.GET)
	public void readPage(Model model, int bno,boolean isUpdateViewCnt,@ModelAttribute("cri") SearchCriteria cri) throws Exception{
		logger.info("board readPage Get.......");
		logger.info("bno : "+bno);
		logger.info("cri : "+cri.toString());
		
		BoardVO vo = service.read(bno,isUpdateViewCnt);
	//	service.readCnt(bno);
		model.addAttribute("boardVO",vo);		
	}
	
	@RequestMapping(value="/removePage",method=RequestMethod.GET)
	public String removePage(int bno,int page,SearchCriteria cri,Model model) throws Exception{
		logger.info("board removePage Get.......");
		service.remove(bno); 
		logger.info("crikeyword:"+cri.getKeyword());
		model.addAttribute("page",page);
		model.addAttribute("searchType", cri.getSearchType());
		model.addAttribute("keyword", cri.getKeyword());
		return "redirect:/sboard/listPage";
	}
	
	@RequestMapping(value="/modifyPage",method=RequestMethod.GET)
	public void modifyPageGet(int bno,Model model,boolean isUpdateViewCnt,@ModelAttribute("cri") SearchCriteria cri) throws Exception{
		logger.info("board modifyPage Get.......");
		
		isUpdateViewCnt=false;
		BoardVO vo = service.read(bno, isUpdateViewCnt);
		model.addAttribute("boardVO", vo);
		
	}
	
	@RequestMapping(value="/modifyPage",method=RequestMethod.POST)
	public String modifyPage(BoardVO vo,int page,boolean isUpdateViewCnt,SearchCriteria cri,Model model) throws Exception{
		logger.info("board modifyPage Post.......");
		
		service.modify(vo);
		
	/*	model.addAttribute("bno",vo.getBno());
	 *  //?bno=2 같은 의미
	 */		
		model.addAttribute("bno",vo.getBno());
		model.addAttribute("page",page);
		model.addAttribute("searchType", cri.getSearchType());
		model.addAttribute("keyword", cri.getKeyword());
		model.addAttribute("isUpdateViewCnt",false);
		return "redirect:/sboard/readPage";	
	}
}
