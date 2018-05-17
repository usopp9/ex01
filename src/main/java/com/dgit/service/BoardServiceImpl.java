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
	
	@Override
	public void regist(BoardVO vo) throws Exception {
		
		dao.create(vo);
	}

	@Transactional
	@Override
	public BoardVO read(int bno,boolean isUpdateViewCnt) throws Exception {
				
		if(isUpdateViewCnt){
			dao.readCnt(bno);
		}
		return	dao.read(bno);
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
	}

	@Override
	public void remove(int bno) throws Exception {
		
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

}
