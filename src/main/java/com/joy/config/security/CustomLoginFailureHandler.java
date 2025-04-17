package com.joy.config.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@RequiredArgsConstructor
public class CustomLoginFailureHandler implements AuthenticationFailureHandler {
  @Override
  public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException exception) throws IOException {
    final HttpSession session = request.getSession();

    final boolean isInvalidUsername = (exception instanceof UsernameNotFoundException);
    final boolean isInvalidPassword = (exception instanceof BadCredentialsException);

    session.setAttribute("invalidUsername", isInvalidUsername);
    session.setAttribute("invalidPassword", isInvalidPassword);

    response.sendRedirect("/auth/login?error");
  }
}
