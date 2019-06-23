package com.oauth2.oauth2.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oauth2.oauth2.properties.SecurityProperties;
import com.oauth2.oauth2.response.SimpleResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class BrowerSecurityController {

  private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

  private RequestCache requestCache = new HttpSessionRequestCache();

  @Autowired
  private SecurityProperties securityProperties;

  @GetMapping("/authentication/require")
  public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
    SavedRequest savedRequest = requestCache.getRequest(request, response);

    if (savedRequest != null) {
      String targetUrl = savedRequest.getRedirectUrl();
      log.info("Redirect to " + targetUrl);

      if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
        String redirectUrl = securityProperties.getBrowser().getLoginPage();
        redirectStrategy.sendRedirect(request, response, redirectUrl);
      }
    }

    return new SimpleResponse("Need Authentication");
  }

}
