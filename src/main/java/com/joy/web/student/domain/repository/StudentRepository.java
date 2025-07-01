package com.joy.web.student.domain.repository;

import com.joy.web.student.domain.entity.Student;
import java.util.List;

public interface StudentRepository {
  void save(final Student student);

  List<Student> findAll();

  void deleteByUuid(final String uuid);
}
