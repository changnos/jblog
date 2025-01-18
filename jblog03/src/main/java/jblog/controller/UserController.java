package jblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jblog.service.BlogService;
import jblog.service.CategoryService;
import jblog.service.UserService;
import jblog.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired(required = false)
	private UserService userService;

	@Autowired(required = false)
	private BlogService blogService;

	@Autowired(required = false)
	private CategoryService categoryService;

	public UserController(UserService userService, BlogService blogService, CategoryService categoryService) {
		this.userService = userService;
		this.blogService = blogService;
		this.categoryService = categoryService;
	}

	@GetMapping("/join")
	public String join(@ModelAttribute UserVo userVo) {
		return "user/join";
	}

	@PostMapping("/join")
	public String join(@ModelAttribute @Valid UserVo userVo, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAllAttributes(result.getModel());
			return "user/join";
		}
		userService.join(userVo);
		blogService.join(userVo.getId());
		categoryService.join(userVo.getId());

		return "redirect:/user/joinsuccess";
	}

	@RequestMapping("/joinsuccess")
	public String joinSuccess() {
		return "user/joinsuccess";
	}

	@RequestMapping("/login")
	public String login() {
		return "user/login";
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("authUser");
		session.invalidate();

		return "redirect:/";
	}
}
