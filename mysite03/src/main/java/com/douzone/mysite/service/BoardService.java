package com.douzone.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.PageVo;
import com.douzone.mysite.vo.UserVo;

@Service
public class BoardService {
	@Autowired
	private BoardRepository boardRepository;
	@Autowired
	HttpSession session;

	public List<BoardVo> getList(PageVo page, String query) {
		Map<String, Object> params = new HashMap<String, Object>();
		Long pg = (page.getPage() - 1) * 5;
		params.put("pg", pg);
		params.put("query", query);
		return boardRepository.findAll(params);
	}

	public PageVo getPage(String pg, String query) {
		PageVo page = new PageVo();
		page.setCount(boardRepository.findPage(query));
		page.setPage(Long.parseLong(pg));
		page.setLastPage((page.getCount() - 1) / 5 + 1);

		if (page.getPage() < 4 || page.getLastPage() <= 5) {
			page.setStartPage(1L);
			page.setTotalSize(5L);
		} else if ((page.getLastPage() - page.getPage()) > 1) {
			page.setStartPage(page.getPage() - 2);
			page.setTotalSize(page.getPage() + 2);
		} else {
			page.setStartPage(page.getLastPage() - 4);
			page.setTotalSize(page.getLastPage());
		}
		return page;
	}

	public boolean insert(BoardVo vo) {
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null)
			return false;
		// 새글
		if (vo.getNo() == null) {
			vo.setO_no(1L);
			vo.setDepth(1L);
			// 답글
		} else {
			BoardVo super_vo = boardRepository.findByNo((vo.getNo()));
			boardRepository.updateO(super_vo);
			vo.setG_no(super_vo.getG_no());
			vo.setO_no(super_vo.getO_no() + 1);
			vo.setDepth(super_vo.getDepth() + 1);
		}
		return boardRepository.insert(vo);
	}

	public BoardVo getView(Long no) {
		return boardRepository.findByNo(no);
	}

	public void viewHit(HttpServletResponse response, String view, BoardVo vo) {
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if((authUser == null) || (authUser.getNo() != vo.getUser_no())) {
			if (view == "") {
				boardRepository.updateHit(vo.getNo());
				Cookie cookie = new Cookie("view", "[" + vo.getNo() + "]");
				cookie.setMaxAge(60 * 60 * 24); // 1day
				response.addCookie(cookie);
			} else if (view.indexOf("[" + vo.getNo() + "]") < 0) {
				boardRepository.updateHit(vo.getNo());
				Cookie cookie = new Cookie("view", (view += "[" + vo.getNo() + "]"));
				cookie.setMaxAge(60 * 60 * 24); // 1day
				response.addCookie(cookie);
			}
		}		
	}

	public void delete(Long no) {
		boardRepository.delete(no);
	}

	public void modify(BoardVo vo) {
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null)
			return;
		if (vo.getUser_no() == authUser.getNo())
			boardRepository.update(vo);
	}
}
