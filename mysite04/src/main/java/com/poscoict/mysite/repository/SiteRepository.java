package com.poscoict.mysite.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscoict.mysite.vo.SiteVo;

@Repository
public class SiteRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	public SiteVo findAll() {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("site.findAll");
	}

	public Boolean update(SiteVo vo) {
		return 1 == sqlSession.update("site.update", vo);
	}

	
}
