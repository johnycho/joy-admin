package com.joy.web.student.application.service;

import com.joy.config.exception.constant.ExceptionInfo;
import com.joy.web.student.application.dto.StudentDto.StudentMvcRequest;
import com.joy.web.student.application.dto.StudentDto.StudentMvcResponse;
import com.joy.web.student.domain.repository.StudentRepository;
import com.joy.web.student.presentation.mapper.StudentEntityMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 민팅 이벤트 서비스.
 */
@Service
@RequiredArgsConstructor
public class StudentService {

  private final StudentEntityMapper studentEntityMapper;
  private final StudentRepository studentRepository;

  @Transactional
  public void register(final StudentMvcRequest request) {
    validateRequest(request);
    studentRepository.save(studentEntityMapper.toEntity(request));
  }

  @Transactional
  public void delete(final String uuid) {
    studentRepository.deleteByUuid(uuid);
  }

  @Transactional(readOnly = true)
  public List<StudentMvcResponse> findAll() {
    return studentRepository.findAll()
                            .stream()
                            .map(StudentMvcResponse::of)
                            .toList();
  }

  private void validateRequest(final StudentMvcRequest request) {
    if (Objects.isNull(request.startDate()) || Objects.isNull(request.endDate())) {
      return;
    }
    if ((request.startDate().isBefore(LocalDateTime.now())) || (request.startDate().isAfter(request.endDate()))) {
      throw ExceptionInfo.INVALID_SESSION_PERIOD.exception();
    }
  }
}
