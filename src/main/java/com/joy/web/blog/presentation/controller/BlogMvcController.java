package com.joy.web.blog.presentation.controller;

import com.joy.web.blog.application.dto.BlogDto.BlogPostMvcRequest;
import static com.joy.web.blog.application.dto.BlogDto.EMPTY_BLOG_POST_REQUEST;
import com.joy.web.blog.application.service.BlogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class BlogMvcController {

  private final BlogService blogService;

  @GetMapping("/blog/register")
  public String displayRegisterPage(final Model model) {
    model.addAttribute("blogPost", EMPTY_BLOG_POST_REQUEST);
    return "blog/register";
  }

  @GetMapping("/blog/list")
  public String displayListPage(final Model model) {
    model.addAttribute("blogPosts", blogService.findAll());
    return "blog/list";
  }

  @PostMapping("/blog/register")
  public String register(@Validated @ModelAttribute("blogPost") final BlogPostMvcRequest request) {
    blogService.register(request);
    return "redirect:list";
  }

  @DeleteMapping("/blog/post")
  public String delete(final HttpServletRequest request, @RequestParam(name = "uuid") final String uuid) {
    blogService.delete(uuid);
    return "redirect:" + request.getHeader("Referer");
  }
}
