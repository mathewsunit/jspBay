package com.jspBay.web.configuration.rest;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by sunit on 4/13/17.
 */

/**
 * An authentication logout success handler implementation adapted to a REST approach.
 */
public class RESTLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        // Do... absolutely... nothing! If you've reached this, the user session has been removed already!
    }
}