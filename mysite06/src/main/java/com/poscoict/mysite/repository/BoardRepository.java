package com.poscoict.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscoict.mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	public List<BoardVo> findAll(String kwd, String limit) {
		Map<String, String> map = new HashMap<>();
		map.put("kwd", kwd);
		map.put("limit", limit);
		return sqlSession.selectList("board.findAll", map);
	}

	public boolean insert(BoardVo vo) {
		//update를 따로 빼고
		sqlSession.update("orderNumUp", vo);
		//insert를 분기 
		return 1 == sqlSession.insert("board.insert", vo);
	}

	public boolean delete(Long no) {
		return 1 == sqlSession.delete("board.delete", no);
	}

	public BoardVo findByBno(Long no) {
		return sqlSession.selectOne("board.findByBno", no);
	}
	
	public Integer getTotalRows(String kwd) {
		return sqlSession.selectOne("board.getTotalCount", kwd);
	}
	
	public boolean update(BoardVo vo) {
		return 1 == sqlSession.update("board.update", vo);
	}
	
	public boolean hitUp(Long no) {
		return 1 == sqlSession.update("board.hitUp", no);
	}
}
