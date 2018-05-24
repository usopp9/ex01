package com.dgit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgit.domain.BoardVO;
import com.dgit.domain.Criteria;
import com.dgit.domain.SearchCriteria;
import com.dgit.persistence.BoardDAO;

@Service
public class BoardServiceImpl implements BoardService{

	@Autowired
	BoardDAO dao;
	
	@Transactional
	@Override
	public void regist(BoardVO vo) throws Exception {
		
		dao.create(vo);
		/*이미지파일첨부*/
		/*if(vo.getFiles() == null){ //파일선택없이 게시물 등록시를 대비함
			return;
		}
		
		for(String filename: vo.getFiles()){
			dao.addAttach(filename);
		}*/
		if(vo.getFiles() != null){ 
			for(String filename: vo.getFiles()){
				dao.addAttach(filename);
			}
		}
		
		
		
	}

	@Transactional
	@Override
	public BoardVO read(int bno,boolean isUpdateViewCnt) throws Exception {
				
		if(isUpdateViewCnt){
			dao.readCnt(bno);
		}
		BoardVO vo = dao.read(bno);
		List<String> files = dao.getAttach(bno);
		vo.setFiles(files.toArray(new String[files.size()]));
		return vo;
	}
	  
	/*@Override
	public void readCnt(int bno) throws Exception {
	
		dao.readCnt(bno);	 	
	}*/

	@Override
	public List<BoardVO> listAll() throws Exception {
		
		return dao.listAll();
	}

	@Override
	public void modify(BoardVO vo) throws Exception {
		
		dao.update(vo);		
		/*이미지파일첨부*/
		if(vo.getFiles() == null){ //파일선택없이 게시물 등록시를 대비함
			return;
		}
		
		for(String filename: vo.getFiles()){
			dao.updateAttach(filename,vo.getBno());
		}
		
	}
	
	@Transactional
	@Override
	public void remove(int bno) throws Exception {
		dao.deleteAttach(bno);
		dao.delete(bno);
		
	}

	@Override
	public List<BoardVO> listCriteria(Criteria cri) throws Exception {
		
		return dao.listCriteria(cri);
	}

	@Override
	public int totalCount() throws Exception {
		
		return dao.totalCount();
	}

	@Override
	public List<BoardVO> listSearchCriteria(SearchCriteria cri) throws Exception {
		
		return dao.listSearch(cri);
	}

	@Override
	public int totalSearchCount(SearchCriteria cri) throws Exception {
		
		return dao.totalSearchCount(cri);
	}

	@Override
	public void deleteAttach(int bno) throws Exception {
		
		dao.deleteAttach(bno);
	}

	@Override
	public void updateDeleAttach(String fullName) throws Exception {
		
		dao.updateDeleAttach(fullName);
	}



}
