package com.douzone.mysite.web.mvc.guestbook;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.guestbookRepository;
import com.douzone.mysite.vo.guestbookVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.WebUtil;

public class IndexAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String sort = request.getParameter("sort");
		if(sort==null) sort = "2";
		List<guestbookVo> list = new guestbookRepository().findAll(sort);
		request.setAttribute("list", list);
		WebUtil.forward(request, response, "guestbook/list");
		
	}

}
