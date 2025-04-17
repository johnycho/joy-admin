package com.joy.web.calendar.presentation.controller.mvc;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/calendar")
@RequiredArgsConstructor
public class CalendarController {

  private static final List<String> CALENDAR_IDS = List.of("ko.south_korea#holiday@group.v.calendar.google.com",
                                                           "4cd1779464bc2ff2098b7ca7498ba05d74b7b1cafa7b176bf417018489249392@group.calendar.google.com");
  private final OAuth2AuthorizedClientService authorizedClientService;

  @GetMapping("/types")
  public ResponseEntity<List<Map<String, String>>> findTypes(OAuth2AuthenticationToken authentication) {
    try {
      final OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
          authentication.getAuthorizedClientRegistrationId(),
          authentication.getName()
      );

      final String accessTokenValue = authorizedClient.getAccessToken().getTokenValue();
      final AccessToken accessToken = new AccessToken(accessTokenValue, null);

      final GoogleCredentials credentials = GoogleCredentials.create(accessToken);
      final Calendar service = new Calendar.Builder(
          GoogleNetHttpTransport.newTrustedTransport(),
          GsonFactory.getDefaultInstance(),
          new HttpCredentialsAdapter(credentials)
      ).setApplicationName("joy-admin").build();

      final List<Map<String, String>> calendarInfoList = new ArrayList<>();

      for (final String calendarId : CALENDAR_IDS) {
        final String calendarName = service.calendarList().get(calendarId).execute().getSummary();
        Map<String, String> map = new HashMap<>();
        map.put("id", calendarId);
        map.put("summary", calendarName);
        calendarInfoList.add(map);
      }

      return ResponseEntity.ok(calendarInfoList);
    } catch (Exception e) {
      log.error("Failed findTypes", e);
      throw new RuntimeException(e);
    }
  }

  @GetMapping("/schedules")
  public ResponseEntity<List<Map<String, String>>> findSchedules(OAuth2AuthenticationToken authentication) {
    try {
      final OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(),
                                                                                                   authentication.getName());

      final String accessTokenValue = authorizedClient.getAccessToken().getTokenValue();
      final AccessToken accessToken = new AccessToken(accessTokenValue, null);

      final GoogleCredentials credentials = GoogleCredentials.create(accessToken);
      Calendar service = new Calendar.Builder(
          GoogleNetHttpTransport.newTrustedTransport(),
          GsonFactory.getDefaultInstance(),
          new HttpCredentialsAdapter(credentials)
      ).setApplicationName("joy-admin").build();

      final List<Map<String, String>> eventList = new ArrayList<>();

      for (final String calendarId : CALENDAR_IDS) {
        final Events events = service.events()
                                     .list(calendarId)
                                     .setOrderBy("startTime")
                                     .setSingleEvents(true)
                                     .execute();

        for (final Event event : events.getItems()) {
          String start = null;
          String end = null;

          if (event.getStart() != null) {
            start = event.getStart().getDateTime() != null
                    ? event.getStart().getDateTime().toStringRfc3339()
                    : event.getStart().getDate().toString();
          }

          if (event.getEnd() != null) {
            end = event.getEnd().getDateTime() != null
                  ? event.getEnd().getDateTime().toStringRfc3339()
                  : event.getEnd().getDate().toString();
          }

          Map<String, String> map = new HashMap<>();
          map.put("id", event.getId());
          map.put("calendarId", calendarId);
          map.put("title", (event.getSummary() != null ? event.getSummary() : "(제목 없음)"));
          map.put("start", start);
          map.put("end", end);
          eventList.add(map);
        }
      }

      return ResponseEntity.ok(eventList);
    } catch (Exception e) {
      log.error("Failed findSchedules", e);
      throw new RuntimeException(e);
    }
  }
}
