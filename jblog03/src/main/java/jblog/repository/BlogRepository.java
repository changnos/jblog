package jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import jblog.vo.BlogVo;

@Repository
public class BlogRepository {
	private SqlSession sqlSession;

	public BlogRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public int insert(String id) {
		return sqlSession.insert("blog.insert", id);
	}

	public BlogVo findById(String id) {
		return sqlSession.selectOne("blog.findById", id);
	}

	public void update(BlogVo blogVo) {
		sqlSession.update("blog.update", blogVo);
	}

}
