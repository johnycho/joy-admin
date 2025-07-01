package com.joy.web.student.presentation.mapper;

import com.joy.config.JoyMapperConfig;
import com.joy.config.mapper.EntityMapper;
import com.joy.web.student.application.dto.StudentDto.StudentMvcRequest;
import com.joy.web.student.domain.entity.Student;
import org.mapstruct.Mapper;

@Mapper(config = JoyMapperConfig.class)
public interface StudentEntityMapper extends EntityMapper<StudentMvcRequest, Student> {
}
