package com.jspBay.web.configuration;

import com.jspBay.web.configuration.cors.CORSFilter;
import com.jspBay.web.configuration.csrf.CsrfTokenResponseCookieBindingFilter;
import com.jspBay.web.security.UserDetailsServiceImpl;
import com.jspBay.web.service.WebUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;

import javax.annotation.Resource;

/**
 * Created by sunit on 4/13/17.
 */
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {

    @Resource
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Resource
    private AuthenticationFailureHandler authenticationFailureHandler;
    @Resource
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    @Resource
    private CORSFilter corsFilter;
    @Resource
    private LogoutSuccessHandler logoutSuccessHandler;
    @Autowired
    private WebUserService webUserService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
//                .inMemoryAuthentication().withUser("user").password("user").roles("USER").and().withUser("admin")
//                .password("admin").roles("ADMIN");
                .userDetailsService(new UserDetailsServiceImpl(webUserService))
                .passwordEncoder(new BCryptPasswordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/*/**").permitAll()
                .antMatchers("/login", "/", "/bower_components/**","/js/**","/css/**").permitAll()
                .antMatchers("/logout", "/user/**", "/resource/**").authenticated();

        // Handlers and entry points
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
        http.formLogin().successHandler(authenticationSuccessHandler);
        http.formLogin().failureHandler(authenticationFailureHandler);

        // Logout
        http.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);

        // CORS
        http.addFilterBefore(corsFilter, ChannelProcessingFilter.class);

        // CSRF
        http.csrf().requireCsrfProtectionMatcher(
                new AndRequestMatcher(
                        // Apply CSRF protection to all paths that do NOT match the ones below

                        // We disable CSRF at login/logout, but only for OPTIONS methods
                        new NegatedRequestMatcher(new AntPathRequestMatcher("/login*/**", HttpMethod.OPTIONS.toString())),
                        new NegatedRequestMatcher(new AntPathRequestMatcher("/logout*/**", HttpMethod.OPTIONS.toString())),

                        new NegatedRequestMatcher(new AntPathRequestMatcher("/user", HttpMethod.GET.toString())),
                        new NegatedRequestMatcher(new AntPathRequestMatcher("/resource*/**", HttpMethod.GET.toString())),

                        new NegatedRequestMatcher(new AntPathRequestMatcher("/", HttpMethod.GET.toString())),
                        new NegatedRequestMatcher(new AntPathRequestMatcher("/bower_components/**", HttpMethod.GET.toString())),
                        new NegatedRequestMatcher(new AntPathRequestMatcher("/css/**", HttpMethod.GET.toString())),
                        new NegatedRequestMatcher(new AntPathRequestMatcher("/js/**", HttpMethod.GET.toString()))
                )
        );
        http.addFilterAfter(new CsrfTokenResponseCookieBindingFilter(), CsrfFilter.class); // CSRF tokens handling
    }
}