package com.douzone.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;

@Service
public class BoardService {
	@Autowired
	private BoardRepository boardRepository;

	public Map<String, Object> getList(Integer prevPage, String query) {
		int count = boardRepository.findBoardCount(query);
		int TotalPage = (int) ((count - 1) / 5 + 1);
		int beginPage, endPage;

		if (prevPage < 4 || TotalPage <= 5) {
			beginPage = 1;
			endPage = 5;
		} else if (TotalPage - prevPage > 1) {
			beginPage = prevPage - 2;
			endPage = prevPage + 2;
		} else {
			beginPage = prevPage - 4;
			endPage = TotalPage;
		}
		List<BoardVo> list = boardRepository.findAll(prevPage, query);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("count", count); // 총 글의 수
		map.put("TotalPage", TotalPage); // 총 페이지 수
		map.put("beginPage", beginPage); // 페이징에 보일 첫 페이지
		map.put("prevPage", prevPage); // 페이징에 보일 현재 페이지
		map.put("endPage", endPage); // 페이징에 보일 마지막 페이지
		map.put("count", count);
		map.put("count", count);
		map.put("query", query);

		return map;
	}

	public boolean insert(BoardVo vo) {
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

	public void delete(Long no) {
		boardRepository.delete(no);
	}

	public void modify(BoardVo vo) {
		boardRepository.update(vo);
	}
}
