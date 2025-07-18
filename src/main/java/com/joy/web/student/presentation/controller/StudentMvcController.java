package com.joy.web.student.presentation.controller;

import static com.joy.web.student.application.dto.StudentDto.EMPTY_STUDENT_REQUEST;
import com.joy.web.student.application.dto.StudentDto.StudentMvcRequest;
import com.joy.web.student.application.service.StudentService;
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
public class StudentMvcController {

  private final StudentService studentService;

  @GetMapping("/student/register")
  public String displayRegisterPage(final Model model) {
    model.addAttribute("student", EMPTY_STUDENT_REQUEST);
    return "student/register";
  }

  @GetMapping("/student/list")
  public String displayListPage(final Model model) {
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
}
