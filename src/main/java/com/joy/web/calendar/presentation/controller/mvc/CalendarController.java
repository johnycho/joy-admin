package com.joy.web.calendar.presentation.controller.mvc;

import com.joy.web.calendar.application.dto.CalendarEventResponse;
import com.joy.web.calendar.application.dto.CalendarTypeResponse;
import com.joy.web.calendar.application.service.CalendarService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/calendar")
@RequiredArgsConstructor
public class CalendarController {

  private final CalendarService calendarService;

  @GetMapping("/types")
  public ResponseEntity<List<CalendarTypeResponse>> findTypes(OAuth2AuthenticationToken authentication) {
    return ResponseEntity.ok(calendarService.findTypes(authentication));
  }

  @GetMapping("/schedules")
  public ResponseEntity<List<CalendarEventResponse>> findSchedules(OAuth2AuthenticationToken authentication,
                                                                   @RequestParam String start,
                                                                   @RequestParam String end) {
    return ResponseEntity.ok(calendarService.findSchedules(authentication, start, end));
  }
}
