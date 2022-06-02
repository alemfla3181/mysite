package com.douzone.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.WebUtil;

public class ViewAction implements Action {
	private static final String COOKIE_NAME = "view";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String no = request.getParameter("no");

		BoardVo vo = new BoardVo();
		vo = new BoardRepository().findByNo(Long.parseLong(no));

		Cookie viewCookie = null;
		String viewPage = "";
		// 쿠키 읽기
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (COOKIE_NAME.equals(cookie.getName())) {
					viewCookie = cookie;
					viewPage = cookie.getValue();
				}
			}
		}

		// 쿠키 쓰기
		if (viewPage.indexOf("[" + no + "]") < 0) {
			vo.setHit(vo.getHit() + 1);
			new BoardRepository().updateHit(vo);
			viewCookie = new Cookie(COOKIE_NAME, viewPage += "[" + no + "]");
			response.addCookie(viewCookie);
		}else if(viewCookie == null){
			vo.setHit(vo.getHit() + 1);
			new BoardRepository().updateHit(vo);
			Cookie cookie = new Cookie(COOKIE_NAME, "[" + no + "]");
			cookie.setPath(request.getContextPath());
			cookie.setMaxAge(24 * 60 * 60); // 1day
			response.addCookie(cookie);
		}

		request.setAttribute("vo", vo);
		WebUtil.forward(request, response, "board/view");
	}

}
