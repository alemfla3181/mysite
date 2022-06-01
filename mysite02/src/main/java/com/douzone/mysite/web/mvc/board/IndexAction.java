package com.douzone.mysite.web.mvc.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.PageVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.WebUtil;

public class IndexAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PageVo page = new PageVo();		
		
		Long pages = null;
		String i = request.getParameter("i");
		String query = request.getParameter("kwd");
		if(query == null)
			query = "";
		page.setCount(new BoardRepository().findPage(query));
		
		if(i == null || (Integer.parseInt(i)) < 0)
			pages = 1L;
		else
			pages = Long.parseLong(request.getParameter("i"));
		
		page.setPage(pages);
		page.setLastPage((page.getCount()-1)/5+1);
		
		if(page.getPage() > page.getLastPage()) {
			WebUtil.redirect(request, response, request.getContextPath() + "/board");
			return;
		}
		
		if(page.getPage() < 4 || page.getLastPage() <= 5) {
			page.setStartPage(1L);
			page.setTotalSize(5L);
		}
		else if((page.getLastPage()-page.getPage())> 1) {
			page.setStartPage(page.getPage()-2);
			page.setTotalSize(page.getPage()+2);
		} else {
			page.setStartPage(page.getLastPage()-4);
			page.setTotalSize(page.getLastPage());
		}				
				
		List<BoardVo> list = new BoardRepository().findAll(page, query);
		request.setAttribute("list", list);				
		request.setAttribute("page", page);		
		WebUtil.forward(request, response, "board/index");
	}
}
