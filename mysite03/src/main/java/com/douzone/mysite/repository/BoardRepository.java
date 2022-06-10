package com.douzone.mysite.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	public List<BoardVo> findAll(Map<String, Object> params) {
		return sqlSession.selectList("board.findAll", params);
	}

	public Long findPage(String query) {
		return sqlSession.selectOne("board.findPage", query);
	}
	
	public boolean insert(BoardVo vo) {
		return sqlSession.insert("board.insert", vo) == 1;
	}	

	public BoardVo findByNo(Long no) {
		return sqlSession.selectOne("board.findByNo", no);
	}
	
	public void update(BoardVo vo) {
		sqlSession.update("board.update", vo);
	}

	public void updateHit(Long no) {
		sqlSession.update("board.updateHit", no);
	}

	public void updateO(BoardVo vo) {
		sqlSession.update("board.updateO", vo);
	}

	public void delete(Long no) {
		sqlSession.delete("board.delete", no);
	}

}
