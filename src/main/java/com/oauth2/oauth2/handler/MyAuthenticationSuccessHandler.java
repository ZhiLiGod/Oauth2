package com.oauth2.oauth2.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oauth2.oauth2.properties.LoginType;
import com.oauth2.oauth2.properties.SecurityProperties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

  private ObjectMapper objectMapper;

  @Autowired
  private SecurityProperties securityProperties;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws ServletException, IOException {
    log.info("Login Successful");

    if (LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
      response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
      response.getWriter().write(objectMapper.writeValueAsString(authentication));
    } else {
      super.onAuthenticationSuccess(request, response, authentication);
    }
  }

}
