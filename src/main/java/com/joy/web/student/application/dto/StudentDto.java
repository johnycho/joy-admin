package com.joy.web.student.application.dto;

import com.joy.config.util.DateTimeUtils;
import com.joy.web.student.domain.entity.Student;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Optional;

public class StudentDto {

  public static final StudentMvcRequest EMPTY_STUDENT_REQUEST = new StudentMvcRequest(null, null, null, null, null);

  public record StudentMvcRequest(@NotBlank(message = "이름을 입력하세요.")
                                  String name,
                                  @NotBlank(message = "설명을 입력하세요.")
                                  String description,
                                  @NotBlank(message = "특이 사항을 입력하세요.")
                                  String contents,
                                  LocalDateTime startDate,
                                  LocalDateTime endDate) {
  }

  public record StudentMvcResponse(String uuid,
                                   String name,
                                   String description,
                                   String contents,
                                   LocalDateTime utcStartDate,
                                   LocalDateTime utcEndDate,
                                   LocalDateTime kstStartDate,
                                   LocalDateTime kstEndDate) {

    public static StudentMvcResponse of(final Student student) {
      return new StudentMvcResponse(student.getUuid(),
                                    student.getName(),
                                    student.getDescription(),
                                    student.getContents(),
                                    student.getStartDate(),
                                    student.getEndDate(),
                                    convertToKst(student.getStartDate()),
                                    convertToKst(student.getEndDate()));
    }

    private static LocalDateTime convertToKst(final LocalDateTime dateTime) {
      return Optional.ofNullable(dateTime).map(DateTimeUtils::convertToKstDateTime).orElse(null);
    }
  }
}
