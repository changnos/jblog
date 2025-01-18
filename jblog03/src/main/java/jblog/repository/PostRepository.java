package jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import jblog.vo.PostVo;

@Repository
public class PostRepository {
	private SqlSession sqlSession;

	public PostRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public List<PostVo> findByCategoryId(Long categoryId) {
		return sqlSession.selectList("post.findByCategoryId", categoryId);
	}

	public int checkByCategoryId(Long categoryId) {
		return sqlSession.selectOne("post.checkByCategoryId", categoryId);
	}

	public int insert(PostVo postVo) {
		return sqlSession.insert("post.insert", postVo);
	}
}
