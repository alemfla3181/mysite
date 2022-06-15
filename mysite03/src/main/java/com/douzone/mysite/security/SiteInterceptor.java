package com.douzone.mysite.security;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import com.douzone.mysite.service.SiteService;
import com.douzone.mysite.vo.SiteVo;


public class SiteInterceptor implements HandlerInterceptor {
	
	@Autowired
	private SiteService siteService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {	
		// 어플리케이션 범위에 객체를 확인하기 위해 
		ServletContext sc = request.getServletContext();
		
		// site가 없으면 DB에서 가져와서 어플리케이션에 담아줌
		if(sc.getAttribute("site") == null) {
			SiteVo vo = siteService.getSite();
			sc.setAttribute("site", vo);
			response.sendRedirect(request.getContextPath());
			return false;
		}		
		
		// 있으면 컨트롤러가 실행되도록 리턴 
		return true;		
	}	
}
