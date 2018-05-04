package com.dgit.ex01;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dgit.domain.BoardVO;
import com.dgit.service.BoardService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class BoardServiceTest {

	@Autowired
	BoardService service;
	
	@Test
	public void testRegister() throws Exception{
		BoardVO vo = new BoardVO();
		vo.setTitle("집에");
		vo.setContent("간다요");
		vo.setWriter("매미");
		
		service.regist(vo);
	}
}
