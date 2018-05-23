package com.dgit.controller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dgit.domain.BoardVO;
import com.dgit.domain.Criteria;
import com.dgit.domain.PageMaker;
import com.dgit.domain.SearchCriteria;
import com.dgit.service.BoardService;
import com.dgit.util.MediaUtils;
import com.dgit.util.UploadFileUtils;

@Controller
@RequestMapping("/sboard/*")//page +  search
public class SearchBoardController {
	private static final Logger logger = LoggerFactory.getLogger(SearchBoardController.class);
	@Autowired
	private BoardService service;
	
	@Resource(name="uploadPath")
	private String uploadPath;
	
	@RequestMapping(value="register", method=RequestMethod.GET)
	public void registerGet(){
		logger.info("board register Get.......");
		/*url주소와jsp주소가 같기때문에 생략해도 됨*/
		
		//return "/board/register";
	}
	@RequestMapping(value="register", method=RequestMethod.POST)
	public String registerPost(BoardVO vo,List<MultipartFile> imageFiles) throws Exception{
		logger.info("board register Post.......");
		logger.info(vo.toString());
		
		ArrayList<String> list = new ArrayList<>();
		for(MultipartFile file : imageFiles){
			logger.info("filename : " + file.getOriginalFilename());
			
			String thumb = UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
			list.add(thumb);
		}
		/*  ArrayList -> String*/
		vo.setFiles(list.toArray(new String[list.size()]));
		service.regist(vo);
			
		return "redirect:/sboard/listPage";
	}
	
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
	public String removePage(int bno,int page,SearchCriteria cri,Model model,String[] files) throws Exception{
		logger.info("board removePage Get.......");
		
		logger.info("filename : "+ files);
		for(String file:files){
			
		UploadFileUtils.deleteFile(uploadPath, file);
		}
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
	public String modifyPage(BoardVO vo,int page,boolean isUpdateViewCnt,SearchCriteria cri,Model model,String[] oldfiles,List<MultipartFile> imageFiles) throws Exception{
		logger.info("board modifyPage Post.......");
		
		if(imageFiles.isEmpty()==false){
		for(MultipartFile file: imageFiles){
			logger.info("imageFiles : "+ file);  
			}
		}
		/*수정중*/
		if(oldfiles.length>0){
		for(String file: oldfiles){
			logger.info("oldfiles : "+ file);  
			}  
		}
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
	
	
	/*카페24는 내부업로드*/
	/*실무는 외무업로드*/
	/*@RestController가 아닐때*/
	/*화면전환이 아닐때  @ResponseBody*/
	@ResponseBody
	@RequestMapping("/displayFile")
	public ResponseEntity<byte[]> displayFile(String filename)throws Exception{
		ResponseEntity<byte[]> entity = null;
		InputStream in = null;
		
		logger.info("[displayFile] filename" + filename);
		try{
			String format = filename.substring(filename.lastIndexOf(".")+1);
			MediaType mType = MediaUtils.getMediaType(format);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(mType);
			
			in = new FileInputStream(uploadPath+"/"+filename);
			
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in),headers,HttpStatus.CREATED);
			
		}catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}finally {
			in.close();
		}
		return entity;
	}
}
