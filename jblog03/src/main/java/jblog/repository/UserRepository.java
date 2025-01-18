package jblog.repository;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import jblog.vo.UserVo;

@Repository
public class UserRepository {
	private SqlSession sqlSession;

	public UserRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public int insert(UserVo vo) {
		return sqlSession.insert("user.insert", vo);
	}

	public UserVo findByIdAndPassword(String id, String password) {
		UserVo userVo = sqlSession.selectOne("user.findByIdAndPassword", Map.of("id", id, "password", password));
		return userVo;
	}

	public UserVo findById(String userId) {
		return sqlSession.selectOne("user.findById", userId);
	}

	public int update(UserVo vo) {
		return sqlSession.update("user.update", vo);
	}

}