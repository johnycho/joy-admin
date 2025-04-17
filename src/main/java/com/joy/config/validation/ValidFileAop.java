package com.joy.config.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;
import org.springframework.web.multipart.MultipartFile;

public class ValidFileAop implements ConstraintValidator<ValidFile, MultipartFile> {
  @Override
  public boolean isValid(final MultipartFile file, final ConstraintValidatorContext context) {
    return Objects.nonNull(file) && !file.isEmpty();
  }
}
