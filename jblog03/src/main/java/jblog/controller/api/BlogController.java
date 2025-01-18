package jblog.controller.api;

import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jblog.dto.JsonResult;
import jblog.service.CategoryService;
import jblog.service.PostService;
import jblog.vo.CategoryVo;

@RestController("blogApiController")
@RequestMapping("/{id:(?!assets).*}/api")
public class BlogController {
	private CategoryService categoryService;
	private PostService postService;

	public BlogController(CategoryService categoryService, PostService postService) {
		this.categoryService = categoryService;
		this.postService = postService;
	}

	@PostMapping("/category/add")
	public JsonResult insertCategory(@PathVariable("id") String id,
			@RequestParam(value = "name", required = true, defaultValue = "") String name,
			@RequestParam(value = "description", required = true, defaultValue = "") String description) {
		CategoryVo categoryVo = new CategoryVo();
		categoryVo.setBlogId(id);
		categoryVo.setName(name);
		categoryVo.setDescription(description);

		int ret = categoryService.add(categoryVo);

		return JsonResult.success(Map.of("success", ret));
	}

	@PostMapping("/category/delete")
	public JsonResult deleteCategory(
			@RequestParam(value = "category_id", required = true, defaultValue = "") Long category_id) {
		int ret1 = postService.checkByCategoryId(category_id);
		
		System.out.println("category_id: " + category_id);
		System.out.println("ret1: " + ret1);

		if (ret1 > 0) {
			return JsonResult.fail("글이 1개 이상 존재하는 카테고리는 삭제할 수 없습니다.");
		}

		int ret2 = categoryService.delete(category_id);

		return JsonResult.success(Map.of("success", ret2));

	}
}
