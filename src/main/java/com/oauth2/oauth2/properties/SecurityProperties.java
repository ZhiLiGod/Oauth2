package com.oauth2.oauth2.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "zhi.security")
public class SecurityProperties {

  private BrowserProperties browser = new BrowserProperties();

  public BrowserProperties getBrowser() {
    return browser;
  }

  public void setBrowser(BrowserProperties browser) {
    this.browser = browser;
  }

}
