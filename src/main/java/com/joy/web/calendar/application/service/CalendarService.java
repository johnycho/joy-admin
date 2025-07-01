package com.joy.web.calendar.application.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.joy.config.exception.constant.ExceptionInfo;
import com.joy.web.calendar.application.dto.CalendarEventResponse;
import com.joy.web.calendar.application.dto.CalendarTypeResponse;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalendarService {

  private static final List<String> CALENDAR_IDS = List.of("ko.south_korea#holiday@group.v.calendar.google.com",
                                                           "joylangcenter@gmail.com",
                                                           "4cd1779464bc2ff2098b7ca7498ba05d74b7b1cafa7b176bf417018489249392@group.calendar.google.com");

  private final OAuth2AuthorizedClientService authorizedClientService;

  public List<CalendarTypeResponse> findTypes(final OAuth2AuthenticationToken authentication) {
    return CALENDAR_IDS.stream()
                       .map(calendarId -> mapToCalendarInfo(authentication, calendarId))
                       .toList();
  }

  public List<CalendarEventResponse> findSchedules(final OAuth2AuthenticationToken authentication, final String start, final String end) {
    return CALENDAR_IDS.stream()
                       .flatMap(calendarId -> getEvents(authentication, calendarId, start, end).stream().map(event -> mapToEventInfo(event, calendarId)))
                       .toList();
  }

  @NotNull
  private static DateTime convertToDateTime(final String isoDateTime) {
    return new DateTime(Date.from(OffsetDateTime.parse(isoDateTime.replace(" ", "+"))
                                                .toInstant()));
  }

  @NotNull
  private CalendarTypeResponse mapToCalendarInfo(final OAuth2AuthenticationToken authentication, final String calendarId) {
    return new CalendarTypeResponse(calendarId, getCalendarName(authentication, calendarId));
  }

  private String getCalendarName(final OAuth2AuthenticationToken authentication, final String calendarId) {
    try {
      return getCalendar(authentication).calendars()
                                        .get(calendarId)
                                        .execute()
                                        .getSummary();
    } catch (Exception e) {
      log.warn(ExceptionInfo.FAILED_TO_FETCH_CALENDAR_INFO.getMessage(), calendarId, e);
      throw ExceptionInfo.FAILED_TO_FETCH_CALENDAR_INFO.exception();
    }
  }

  private List<Event> getEvents(final OAuth2AuthenticationToken authentication, final String calendarId, final String start, final String end) {
    try {
      final EventPeriod period = getEventPeriod(start, end);
      return getCalendar(authentication).events()
                                        .list(calendarId)
                                        .setOrderBy("startTime")
                                        .setSingleEvents(true)
                                        .setTimeMin(period.start())
                                        .setTimeMax(period.end())
                                        .execute()
                                        .getItems();
    } catch (Exception e) {
      log.warn(ExceptionInfo.FAILED_TO_FETCH_CALENDAR_EVENTS.getMessage(), calendarId, e);
      throw ExceptionInfo.FAILED_TO_FETCH_CALENDAR_EVENTS.exception();
    }
  }

  @NotNull
  private EventPeriod getEventPeriod(final String start, final String end) {
    return new EventPeriod(convertToDateTime(start), convertToDateTime(end));
  }

  private CalendarEventResponse mapToEventInfo(final Event event, final String calendarId) {
    return new CalendarEventResponse(event.getId(),
                                     calendarId,
                                     event.getSummary() != null ? event.getSummary() : "(제목 없음)",
                                     extractDate(event.getStart()),
                                     extractDate(event.getEnd()));
  }

  private String extractDate(final EventDateTime dateTime) {
    return Optional.ofNullable(dateTime)
                   .map(eventDateTime -> {
                     if (Objects.nonNull(eventDateTime.getDateTime())) {
                       return eventDateTime.getDateTime().toStringRfc3339();
                     }
                     if (Objects.nonNull(eventDateTime.getDate())) {
                       return eventDateTime.getDate().toString();
                     }
                     throw ExceptionInfo.INVALID_EVENT_DATETIME.exception();
                   })
                   .orElseThrow(ExceptionInfo.INVALID_EVENT_DATETIME::exception);
  }

  @NotNull
  private Calendar getCalendar(final OAuth2AuthenticationToken authentication) {
    try {
      return new Calendar.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                                  GsonFactory.getDefaultInstance(),
                                  new HttpCredentialsAdapter(GoogleCredentials.create(getAccessToken(authentication))))
          .setApplicationName("joy-admin")
          .build();
    } catch (Exception e) {
      log.warn(ExceptionInfo.FAILED_TO_FETCH_CALENDAR.getMessage(), e);
      throw ExceptionInfo.FAILED_TO_FETCH_CALENDAR.exception();
    }
  }

  @NotNull
  private AccessToken getAccessToken(final OAuth2AuthenticationToken authentication) {
    final OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(),
                                                                                                 authentication.getName());
    return new AccessToken(authorizedClient.getAccessToken().getTokenValue(), null);
  }

  private record EventPeriod(DateTime start, DateTime end) {}
}
