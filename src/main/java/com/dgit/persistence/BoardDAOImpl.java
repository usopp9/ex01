package com.dgit.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dgit.domain.BoardVO;
import com.dgit.domain.Criteria;
import com.dgit.domain.SearchCriteria;

@Repository
public class BoardDAOImpl implements BoardDAO{
	
	@Autowired
	SqlSession session;
	
	private static final String namespace = "com.dgit.mapper.BoardMapper";
	
	@Override
	public void create(BoardVO vo) throws Exception {
		
		session.insert(namespace+".create",vo);
	}

	@Override
	public BoardVO read(int bno) throws Exception {
											/*mapper 아이디와 같아야함*/
		return session.selectOne(namespace+".read",bno);
	}
	@Override
	public void readCnt(int bno) throws Exception {
		session.update(namespace+".readCnt",bno);
		
	}
	@Override
	public void update(BoardVO vo) throws Exception {
		
		session.update(namespace+".update",vo);
		
	}

	@Override
	public void delete(int bno) throws Exception {
		session.delete(namespace+".delete",bno );
		
	}

	@Override
	public List<BoardVO> listAll() throws Exception {
		
		return session.selectList(namespace+".listAll");
	}

	@Override
	public List<BoardVO> listPage(int page) throws Exception {
		page=(page-1)*10;
		return session.selectList(namespace+".listPage",page);
	}

	@Override
	public List<BoardVO> listCriteria(Criteria cri) throws Exception {
		
		return session.selectList(namespace+".listCriteria",cri);
	}

	@Override
	public int totalCount() throws Exception {
		
		return session.selectOne(namespace+".totalCount");
	}

	@Override
	public List<BoardVO> listSearch(SearchCriteria cri) throws Exception {
		
		return session.selectList(namespace+".listSearch",cri);
	}

	@Override
	public int totalSearchCount(SearchCriteria cri) throws Exception {
		
		return session.selectOne(namespace+".totalSearchCount",cri);
	}

	@Override
	public void updateReplyCnt(int bno, int amount) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bno", bno);
		map.put("amount", amount);
		session.update(namespace+".updateReplyCnt",map);
		
	}

	@Override
	public void addAttach(String fullName) throws Exception {
		
		session.insert(namespace+".addAttach",fullName);
	}

	@Override
	public List<String> getAttach(int bno) throws Exception {
		
		return session.selectList(namespace+".getAttach",bno);
	}

	@Override
	public void deleteAttach(int bno) throws Exception {
		
		session.delete(namespace+".deleteAttach",bno);
	}

}
