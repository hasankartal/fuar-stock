package com.fuar.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class TokenAuthenticationFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if(servletRequest instanceof HttpServletRequest && isAuthenticatedRequired()) {
            AuthenticationToken token = extractToken(servletRequest);
            SecurityContextHolder.getContext().setAuthentication(token);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private AuthenticationToken extractToken(ServletRequest request) {
        return new AuthenticationToken(request.getParameter("token"));
    }

    private boolean isAuthenticatedRequired() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || !auth.isAuthenticated()) {
            return true;
        }
        return false;
    }
}
