package com.oauth2.oauth2.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.oauth2.oauth2.handler.MyAuthenticationFailHandler;
import com.oauth2.oauth2.handler.MyAuthenticationSuccessHandler;
import com.oauth2.oauth2.properties.SecurityProperties;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private SecurityProperties securityProperties;

  @Autowired
  private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

  @Autowired
  private MyAuthenticationFailHandler myAuthenticationFailHandler;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    String redirectUrl = securityProperties.getBrowser().getLoginPage();

    // @formatter:off
    http.formLogin()
      .loginPage("/authentication/require")
      .loginProcessingUrl("/authentication/form")
      .successHandler(myAuthenticationSuccessHandler)
      .failureHandler(myAuthenticationFailHandler)
      .and()
      .authorizeRequests()
      .antMatchers("/authentication/require", redirectUrl).permitAll()
      .anyRequest()
      .authenticated()
      .and()
      .csrf().disable();
    // @formatter:on
  }

}
