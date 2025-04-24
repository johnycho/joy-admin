package com.joy.web.calendar.application.dto;

public record CalendarEventResponse(
    String id,
    String calendarId,
    String title,
    String start,
    String end
) {}
