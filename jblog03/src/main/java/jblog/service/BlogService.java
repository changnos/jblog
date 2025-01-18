package jblog.service;

import org.springframework.stereotype.Service;

import jblog.repository.BlogRepository;
import jblog.vo.BlogVo;

@Service
public class BlogService {
	private BlogRepository blogRepository;

	public BlogService(BlogRepository blogRepository) {
		this.blogRepository = blogRepository;
	}

	public void join(String id) {
		blogRepository.insert(id);
	}

	public BlogVo getBlog(String id) {
		return blogRepository.findById(id);
	}

	public void updateDefault(BlogVo blogVo) {
		blogRepository.update(blogVo);
	}
}
