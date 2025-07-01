package com.joy.web.student.presentation.controller;

import static com.joy.web.student.application.dto.StudentDto.EMPTY_STUDENT_REQUEST;
import com.joy.web.student.application.dto.StudentDto.StudentMvcRequest;
import com.joy.web.student.application.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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
public class StudentMvcController {

  private final StudentService studentService;

  @GetMapping("/")
  public String mainPage(final Model model) {
    model.addAttribute("students", studentService.findAll());
    return "home";
  }

  @GetMapping("/student/register")
  public String displayStudentRegisterPage(final Model model) {
    model.addAttribute("student", EMPTY_STUDENT_REQUEST);
    return "student/register";
  }

  @GetMapping("/student/list")
  public String displayStudentListPage(final Model model) {
    model.addAttribute("students", studentService.findAll());
    return "student/list";
  }

  @PostMapping("/student/register")
  public String register(@Validated @ModelAttribute("student") final StudentMvcRequest request) {
    studentService.register(request);
    return "redirect:list";
  }

  @DeleteMapping("/student")
  public String delete(final HttpServletRequest request, @RequestParam(name = "uuid") final String uuid) {
    studentService.delete(uuid);
    return "redirect:" + request.getHeader("Referer");
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
