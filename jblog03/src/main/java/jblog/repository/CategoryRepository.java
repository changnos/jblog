package jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import jblog.vo.CategoryVo;

@Repository
public class CategoryRepository {
	private SqlSession sqlSession;

	public CategoryRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public int insert(String blogId) {
		return sqlSession.insert("category.insertDefault", blogId);
	}

	public int insert(CategoryVo categoryVo) {
		return sqlSession.insert("category.insert", categoryVo);
	}

	public List<CategoryVo> findByBlogId(String blogId) {
		return sqlSession.selectList("category.findByBlogId", blogId);
	}

	public int delete(Long id) {
		return sqlSession.delete("category.deleteById", id);
	}
}
