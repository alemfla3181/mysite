package com.douzone.mysite.web.mvc.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.guestbookRepository;
import com.douzone.mysite.vo.guestbookVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.WebUtil;

public class RecommendAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String no = request.getParameter("no");
		String ud = request.getParameter("ud");
		
		Long count = new guestbookRepository().findCount(Long.parseLong(no));
		guestbookVo vo = new guestbookVo();
		vo.setNo(Long.parseLong(no));
		vo.setCount(count);
		new guestbookRepository().recommend(vo, ud);
		WebUtil.redirect(request, response, request.getContextPath()+"/guestbook");
	}

}