package com.joy.web.home.controller;

import com.joy.web.blog.application.service.BlogService;
import com.joy.web.student.application.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

  private final StudentService studentService;
  private final BlogService blogService;

  @GetMapping("/")
  public String mainPage(final Model model) {
    model.addAttribute("students", studentService.findAll());
    model.addAttribute("blogPosts", blogService.findAll());
    return "home";
  }

  @GetMapping(value = "/auth/login")
  public String login() {
    return "login";
  }

  @GetMapping(value = "/auth/logout")
  public String logout(HttpServletRequest request, HttpServletResponse response) {
    final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) {
      new SecurityContextLogoutHandler().logout(request, response, auth);
    }
    return "redirect:/home";
  }
}
