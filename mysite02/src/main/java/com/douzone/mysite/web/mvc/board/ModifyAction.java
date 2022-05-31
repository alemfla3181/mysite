package com.douzone.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.WebUtil;

public class ModifyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String no = request.getParameter("no");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		BoardVo vo = new BoardVo();
		vo.setNo(Long.parseLong(no));
		vo.setTitle(title);
		vo.setContents(content);

		new BoardRepository().update(vo);
		WebUtil.redirect(request, response, request.getContextPath() + "/board?a=view&no=" + no);
	}

}
