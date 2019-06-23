package com.oauth2.oauth2.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oauth2.oauth2.properties.LoginType;
import com.oauth2.oauth2.properties.SecurityProperties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MyAuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler {

  private ObjectMapper objectMapper;

  @Autowired
  private SecurityProperties securityProperties;

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
      throws IOException, ServletException {
    log.info("Login Failed");

    if (LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
      response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
      response.getWriter().write(objectMapper.writeValueAsString(exception));
    } else {
      super.onAuthenticationFailure(request, response, exception);
    }
  }

}
