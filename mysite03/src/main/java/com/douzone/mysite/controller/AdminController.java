package com.douzone.mysite.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.security.Auth;
import com.douzone.mysite.service.FileUploadService;
import com.douzone.mysite.service.SiteService;
import com.douzone.mysite.vo.SiteVo;

@Auth(role = "ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private SiteService siteService;

	@Autowired
	private FileUploadService fileUploadService;

	@RequestMapping("")
	public String main() {
		return "admin/main";
	}

	@RequestMapping("/main/update")
	public String update(@RequestParam(value = "file", required = false) MultipartFile multipartFile, SiteVo vo) {
		String url = fileUploadService.restore(multipartFile);
		if (multipartFile != null) {
			vo.setProfileURL(url);
		}
		System.out.println(vo);
		siteService.updateSite(vo);

		vo = siteService.getSite();
		servletContext.setAttribute("site", vo);
		return "redirect:/admin";
	}

	@RequestMapping("/guestbook")
	public String guestbook() {
		return "admin/guestbook";
	}

	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}

	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}
}
