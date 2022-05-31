package com.douzone.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.WebUtil;

public class ViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String no = request.getParameter("no");
		HttpSession session = request.getSession(true);

		UserVo user_vo = (UserVo) session.getAttribute("authUser");
		BoardVo vo = new BoardVo();
		vo = new BoardRepository().findByNo(Long.parseLong(no));

		if ((user_vo != null) && (user_vo.getNo() != vo.getUser_no())) {
			vo.setHit(vo.getHit() + 1);
			new BoardRepository().updateHit(vo);
		}
		request.setAttribute("vo", vo);
		WebUtil.forward(request, response, "board/view");
	}

}
