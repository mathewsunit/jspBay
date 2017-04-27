package com.jspBay.web.configuration;

import com.jspBay.web.configuration.cors.CORSFilter;
import com.jspBay.web.configuration.rest.RESTAuthenticationEntryPoint;
import com.jspBay.web.configuration.rest.RESTAuthenticationSuccessHandler;
import com.jspBay.web.configuration.rest.RESTLogoutSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * Created by sunit on 3/22/17.
 */

@Configuration
public class SecurityConfiguration {

    @Bean
    public RESTAuthenticationEntryPoint authenticationEntryPoint() {
        return new RESTAuthenticationEntryPoint();
    }

    @Bean
    public SimpleUrlAuthenticationFailureHandler authenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler();
    }

    @Bean
    public RESTAuthenticationSuccessHandler authenticationSuccessHandler() {
        return new RESTAuthenticationSuccessHandler();
    }

    @Bean
    public CORSFilter corsFilter() {
        return new CORSFilter();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new RESTLogoutSuccessHandler();
    }
}