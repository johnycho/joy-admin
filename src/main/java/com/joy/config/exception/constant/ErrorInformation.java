package com.joy.config.exception.constant;

import org.springframework.http.HttpStatus;

public interface ErrorInformation {

  String getMessage();

  HttpStatus getStatus();

  ErrorLevel getLevel();
}
