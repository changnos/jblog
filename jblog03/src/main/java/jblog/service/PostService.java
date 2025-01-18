package jblog.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jblog.repository.PostRepository;
import jblog.vo.PostVo;

@Service
public class PostService {
	private PostRepository postRepository;

	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	public List<PostVo> getPostByCategoryId(Long categoryId) {
		return postRepository.findByCategoryId(categoryId);
	}

	public int checkByCategoryId(Long categoryId) {
		return postRepository.checkByCategoryId(categoryId);
	}

	public void add(PostVo postVo) {
		postRepository.insert(postVo);
	}
}
