package jblog.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jblog.repository.CategoryRepository;
import jblog.vo.CategoryVo;

@Service
public class CategoryService {
	private CategoryRepository categoryRepository;

	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public void join(String blogId) {
		categoryRepository.insert(blogId);
	}

	public List<CategoryVo> getCategoryByBlogId(String blogId) {
		return categoryRepository.findByBlogId(blogId);
	}

	public int add(CategoryVo categoryVo) {
		return categoryRepository.insert(categoryVo);
	}

	public int delete(Long id) {
		return categoryRepository.delete(id);
	}
}
