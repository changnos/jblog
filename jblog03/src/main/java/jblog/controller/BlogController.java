package jblog.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;
import jblog.security.Auth;
import jblog.service.BlogService;
import jblog.service.CategoryService;
import jblog.service.FileUploadService;
import jblog.service.PostService;
import jblog.vo.BlogVo;
import jblog.vo.CategoryVo;
import jblog.vo.PostVo;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class BlogController {
	@Autowired(required = false)
	private BlogService blogService;

	@Autowired(required = false)
	private CategoryService categoryService;

	@Autowired(required = false)
	private PostService postService;

	private final FileUploadService fileUploadService;
	private final ServletContext servletContext;

	public BlogController(BlogService blogService, CategoryService categoryService, PostService postService,
			FileUploadService fileUploadService, ServletContext servletContext) {
		this.blogService = blogService;
		this.categoryService = categoryService;
		this.postService = postService;
		this.fileUploadService = fileUploadService;
		this.servletContext = servletContext;
	}

	@RequestMapping({ "", "/{path1}", "/{path1}/{path2}" })
	public String index(@PathVariable("id") String id, @PathVariable("path1") Optional<Long> path1,
			@PathVariable("path2") Optional<Long> path2, Model model) {

		Long categoryId = 0L;
		Long postId = 0L;

		if (path2.isPresent()) {
			categoryId = path1.get();
			postId = path2.get();
		} else if (path1.isPresent()) {
			categoryId = path1.get();
		}

		BlogVo blogVo = blogService.getBlog(id);
		model.addAttribute("blogVo", blogVo);

		List<CategoryVo> categoryList = categoryService.getCategoryByBlogId(id);
		model.addAttribute("categoryList", categoryList);
		if (categoryId == 0L) {
			categoryId = categoryList.get(0).getId();
		}

		List<PostVo> postList = postService.getPostByCategoryId(categoryId);
		model.addAttribute("postList", postList);

		if (postId == 0L && !postList.isEmpty()) {
			postId = postList.get(0).getId();
		}

		model.addAttribute("postId", postId);

		return "blog/main";
	}

	@Auth
	@RequestMapping({ "/admin", "/admin/basic" })
	public String adminDefault(@PathVariable("id") String id, Model model) {
		model.addAttribute("blogVo", blogService.getBlog(id));
		return "blog/admin-default";
	}

	@Auth
	@RequestMapping("/admin/default/update")
	public String updateDefault(@PathVariable("id") String id, BlogVo blogVo,
			@RequestParam("file") MultipartFile multipartFile) {

		System.out.println("id: " + id);
		blogVo.setId(id);

		String profile = fileUploadService.restore(multipartFile);
		if (profile != null) {
			blogVo.setProfile(profile);
		}

		blogService.updateDefault(blogVo);

		// update servlet context bean
		servletContext.setAttribute("blogVo", blogVo);

		return "redirect:/{id}/admin";

	}

	@Auth
	@RequestMapping("/admin/category")
	public String adminCategory(@PathVariable("id") String id, Model model) {
		List<CategoryVo> categoryList = categoryService.getCategoryByBlogId(id);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("blogVo", blogService.getBlog(id));

		return "blog/admin-category";
	}

	@Auth
	@GetMapping("/admin/write")
	public String adminWrite(@PathVariable("id") String id, Model model) {
		List<CategoryVo> categoryList = categoryService.getCategoryByBlogId(id);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("blogVo", blogService.getBlog(id));

		return "blog/admin-write";
	}

	@Auth
	@PostMapping("/admin/write")
	public String adminWrite(@PathVariable("id") String id, @ModelAttribute PostVo postVo) {
		postService.add(postVo);
		return "redirect:/{id}/" + postVo.getCategoryId();
	}
}
