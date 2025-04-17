package com.joy.web.calendar.presentation.controller.mvc;

import static com.joy.web.calendar.application.dto.MintingEventDto.EMPTY_MINTING_EVENT_REQUEST;
import com.joy.web.calendar.application.dto.MintingEventDto.MintingEventMvcRequest;
import com.joy.web.calendar.application.service.MintingEventService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class WebMvcController {

  private final MintingEventService mintingEventService;

  @GetMapping("/")
  public String mainPage(final Model model) {
    model.addAttribute("events", mintingEventService.getAllEvents());
    return "home";
  }

  @GetMapping("/minting/event/register")
  public String displayMintingEventRegisterPage(final Model model) {
    model.addAttribute("mintingEvent", EMPTY_MINTING_EVENT_REQUEST);
    return "minting/event/register";
  }

  @GetMapping("/minting/event/list")
  public String displayMintingEventListPage(final Model model) {
    model.addAttribute("events", mintingEventService.getAllEvents());
    return "minting/event/list";
  }

  @PostMapping("/minting/event/register")
  public String addMintingEvent(@Validated @ModelAttribute("mintingEvent") final MintingEventMvcRequest request) {
    mintingEventService.addMintingEvent(request);
    return "redirect:list";
  }

  @DeleteMapping("/minting/event")
  public String deleteMintingEvent(final HttpServletRequest request, @RequestParam(name = "uuid") final String uuid) {
    mintingEventService.deleteMintingEvent(uuid);
    return "redirect:" + request.getHeader("Referer");
  }

  @RequestMapping(value = "/auth/login", method = RequestMethod.GET)
  public String login() {
    return "login";
  }

  @RequestMapping(value = "/auth/logout", method = RequestMethod.GET)
  public String logout(HttpServletRequest request, HttpServletResponse response) {
    final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) {
      new SecurityContextLogoutHandler().logout(request, response, auth);
    }
    return "redirect:/home";
  }
}
