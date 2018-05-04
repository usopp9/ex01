package com.dgit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dgit.domain.BoardVO;
import com.dgit.persistence.BoardDAO;

@Service
public class BoardServiceImpl implements BoardService{

	@Autowired
	BoardDAO dao;
	
	@Override
	public void regist(BoardVO vo) throws Exception {
		
		dao.create(vo);
	}

	@Override
	public BoardVO read(int bno) throws Exception {
		
		return	dao.read(bno);
	}
	
	@Override
	public void readCnt(int bno) throws Exception {
	
		dao.readCnt(bno);		
	}

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

}
