package com.douzone.mysite.web.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.repository.UserRepository;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.WebUtil;

public class UpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String gender = request.getParameter("gender");
		
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		UserVo userVo = new UserRepository().findByNo(authUser.getNo());
				
		if(password != null) {
			userVo.setPassword(password);
		}
		
		userVo.setName(name);
		userVo.setGender(gender);
		new UserRepository().updateUser(userVo);;
		WebUtil.redirect(request, response, request.getContextPath()+"/user?a=updateform");
		
		authUser.setName(name);		
	}

}
