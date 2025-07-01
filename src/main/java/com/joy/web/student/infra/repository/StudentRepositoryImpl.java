package com.joy.web.student.infra.repository;

import com.joy.web.student.domain.entity.Student;
import com.joy.web.student.domain.repository.StudentRepository;
import com.joy.web.student.infra.jpa.StudentJpa;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StudentRepositoryImpl implements StudentRepository {

  private final StudentJpa studentJpa;

  @Override
  public void save(final Student student) {
    studentJpa.save(student);
  }

  @Override
  public List<Student> findAll() {
    return studentJpa.findAll();
  }

  @Override
  public void deleteByUuid(final String uuid) {
    studentJpa.deleteByUuid(uuid);
  }
}
