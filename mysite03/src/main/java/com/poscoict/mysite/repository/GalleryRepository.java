package com.poscoict.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscoict.mysite.vo.GalleryVo;

@Repository
public class GalleryRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	public List<GalleryVo> getImages() {
		
		return sqlSession.selectList("gallery.getImages");
	}

	public Boolean remove(Long no) {
		int count = sqlSession.delete("gallery.removeByNo", no);
		return count == 1;
	}
	
}
