package com.poscoict.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
	private SqlSession sqlSession ;
	
	public int insert(BoardVo boardVo) {
		return sqlSession.insert("board.insert", boardVo);
	}
	
	public List<BoardVo> findAllByPageAndKeword(String keyword, Integer page, Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("keyword", "%" + keyword + "%");
		map.put("startPage", (page-1)* size);
		map.put("size", size);
		
		return sqlSession.selectList("board.findAllByPageAndKeyword", map);
	}

	public int update(BoardVo boardVo) {
		return sqlSession.update("board.update", boardVo);
	}
	
	public int delete(Long no, Long userNo) {
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("no", no);
		map.put("userNo", userNo);
		
		return sqlSession.delete("board.delete", map);
	}

	public BoardVo findByNo(Long no) {
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("no", no);
		return sqlSession.selectOne("board.findByNo", map);
		
	}
	
	public BoardVo findByNoAndUserNo(Long no, Long userNo) {
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("no", no);
		map.put("userNo", userNo);
		return sqlSession.selectOne("board.findByNoAndUserNo", map);
	}

	public int updateHit(Long no) {
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("no", no);
		return sqlSession.update("board.updateHit", map);
	}
	
	public int updateOrderNo(Integer groupNo, Integer orderNo) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("groupNo", groupNo);
		map.put("orderNo", orderNo);
		return sqlSession.update("board.updateOrderNo", map);	
	}
	
	public int getTotalCount(String keyword) {
		return sqlSession.selectOne("board.gettotalCount", "%" + keyword + "%");
		
	}
}