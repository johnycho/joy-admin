package com.joy.web.student.infra.jpa;

import com.joy.web.student.domain.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentJpa extends JpaRepository<Student, Long> {

  void deleteByUuid(final String uuid);
}
