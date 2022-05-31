package com.douzone.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.WebUtil;

public class AddWriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String Userno = request.getParameter("user_no");
		String no = request.getParameter("no");
		
		BoardVo vo = new BoardVo();
		// 새글
		if (no.equals("")) {			
			vo.setO_no(1L);
			vo.setDepth(1L);
		// 답글
		} else {
			vo = new BoardRepository().findByNo(Long.parseLong(no));	
			new BoardRepository().updateO(vo);
			vo.setO_no(vo.getO_no() + 1);
			vo.setDepth(vo.getDepth() + 1);
		}
		vo.setTitle(title);
		vo.setContents(content);
		vo.setUser_no(Long.parseLong(Userno));
		
		new BoardRepository().insert(vo);
		WebUtil.redirect(request, response, request.getContextPath() + "/board");
	}

}
