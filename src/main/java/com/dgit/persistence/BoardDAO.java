package com.dgit.persistence;

import java.util.List;

import com.dgit.domain.BoardVO;
import com.dgit.domain.Criteria;
import com.dgit.domain.SearchCriteria;

public interface BoardDAO {
	
	public void create(BoardVO vo) throws Exception;
	
	public BoardVO read(int bno) throws Exception;
	public void readCnt(int bno) throws Exception;
	
	public void update(BoardVO vo) throws Exception;
	
	public void delete(int bno) throws Exception;
	
	public List<BoardVO> listAll() throws Exception;
	
	public List<BoardVO> listPage(int page) throws Exception;
	
	public List<BoardVO> listCriteria(Criteria cri) throws Exception;
	
	public int totalCount() throws Exception;

	public List<BoardVO> listSearch(SearchCriteria cri) throws Exception;
	public int totalSearchCount(SearchCriteria cri) throws Exception;
	
	public void updateReplyCnt(int bno,int amount) throws Exception;
	
	public void addAttach(String fullName) throws Exception;
	
	public List<String> getAttach(int bno) throws Exception;
	
	public void deleteAttach(int bno) throws Exception;
	
	public void updateAttach(String fullName,int bno) throws Exception;
	
	public void updateDeleAttach(String fullName)throws Exception;
}
