package com.fuar.config.security;

import com.fuar.service.log.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    TokenService tokenService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Object token = authentication.getPrincipal();
        if(token != null) {
            if(tokenService.isActive(token.toString())) {
                authentication.setAuthenticated(true);
                return authentication;
            }
        }
        throw new BadCredentialsException("Token geçersiz.");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(AuthenticationToken.class);
    }
}
