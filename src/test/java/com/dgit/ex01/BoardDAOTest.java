package com.dgit.ex01;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dgit.domain.BoardVO;
import com.dgit.persistence.BoardDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class BoardDAOTest {
	
	@Autowired
	BoardDAO dao;
	
	//@Test
	public void testCreate() throws Exception{
		BoardVO vo  = new BoardVO();
		vo.setTitle("집에간다");
		vo.setContent("집에가자가자가자자");
		vo.setWriter("user00");
		
		dao.create(vo);
	}
	@Test
	public void testread() throws Exception{
		BoardVO vo  = dao.read(2);
		dao.readCnt(2);
		System.out.println(vo);		
	}
	//@Test
	public void testUpdate() throws Exception{
		BoardVO vo  = new BoardVO();
		vo.setTitle("집에간다");
		vo.setContent("집에가자가자가자자");
		vo.setBno(1);
		
		dao.update(vo);
	}
	//@Test
	public void testdelete() throws Exception{
		dao.delete(1);	
	}
	//@Test
	public void testSelectAll() throws Exception{
		List<BoardVO> list = dao.listAll();
		for(BoardVO b:list){
			System.out.println(b);
		}
	}
}
