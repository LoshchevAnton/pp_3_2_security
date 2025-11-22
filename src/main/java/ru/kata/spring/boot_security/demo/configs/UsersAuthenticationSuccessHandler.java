package ru.kata.spring.boot_security.demo.configs;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UsersAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String url = "";
        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Set<String> authMap = authorities.stream().map(auth -> auth.getAuthority().toUpperCase()).collect(Collectors.toSet());
        if (authMap.contains("ADMIN")) {
            url = "/admin";
        } else if (authMap.contains("USER")) {
            url = "/user";
        }

        if (url.isEmpty()) {
            throw new IllegalStateException("Invalid url");
        }
        redirectStrategy.sendRedirect(request, response, url);
    }
}
