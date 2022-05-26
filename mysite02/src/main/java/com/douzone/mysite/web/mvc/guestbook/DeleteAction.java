package com.douzone.mysite.web.mvc.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.guestbookRepository;
import com.douzone.mysite.vo.guestbookVo;
import com.douzone.web.mvc.Action;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String no = request.getParameter("no");
		String password = request.getParameter("password");
		
		guestbookVo vo = new guestbookVo();
		vo.setNo(Long.parseLong(no));
		vo.setPassword(password);
		
		if (new guestbookRepository().delete(vo)) {
			response.sendRedirect(request.getContextPath() + "/guestbook");
		}else {			
			response.sendRedirect(request.getContextPath() + "/guestbook?a=deleteform&no=" + no);
		}
		
	}
}
