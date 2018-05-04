package com.dgit.persistence;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dgit.domain.BoardVO;

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

	

}
