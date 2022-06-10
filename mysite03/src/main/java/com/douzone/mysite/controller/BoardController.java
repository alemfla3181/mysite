package com.douzone.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.PageVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;

	@RequestMapping("")
	public String index(@RequestParam(value = "pg", required = true, defaultValue = "1") String pg,
			@RequestParam(value = "kwd", required = true, defaultValue = "") String kwd, Model model) {
		PageVo page = boardService.getPage(pg, kwd);
		List<BoardVo> list = boardService.getList(page, kwd);
		model.addAttribute("page", page);
		model.addAttribute("list", list);
		return "board/index";
	}

	@RequestMapping("/view/{no}")
	public String index(HttpServletResponse response,
			@CookieValue(value = "view", required = false, defaultValue = "") String view, @PathVariable("no") Long no,
			Model model) {
		BoardVo vo = boardService.getView(no);
		boardService.viewHit(response, view, vo);
		model.addAttribute("vo", vo);
		return "board/view";
	}

	@RequestMapping(value = { "/write", "/write/{no}" }, method = RequestMethod.GET)
	public String add(@PathVariable(value = "no", required = false) Long no, Model model) {
		model.addAttribute("no", no);
		return "board/write";
	}

	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String add(BoardVo vo) {
		boardService.insert(vo);
		return "redirect:/board";
	}

	@RequestMapping(value = "/delete/{no}", method = RequestMethod.GET)
	public String delete(@PathVariable("no") Long no) {
		boardService.delete(no);
		return "redirect:/board";
	}

	@RequestMapping(value = "/modify/{no}", method = RequestMethod.GET)
	public String modify(@PathVariable("no") Long no, Model model) {
		BoardVo vo = boardService.getView(no);
		model.addAttribute("vo", vo);
		return "board/modify";
	}

	@RequestMapping(value = "/modify/{no}", method = RequestMethod.POST)
	public String modify(BoardVo vo) {
		boardService.modify(vo);
		return "redirect:/board";
	}

}
