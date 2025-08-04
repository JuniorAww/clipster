package com.challengr.server.config;

import com.challengr.server.dto.AuthToken;
import com.challengr.server.dto.UserDto;
import com.challengr.server.model.user.User;
import com.challengr.server.service.JwtService;
import com.challengr.server.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String HEADER_NAME = "Authorization";
    @Autowired private final JwtService jwtService;
    @Autowired private final UserService userService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader(HEADER_NAME);

        if (!StringUtils.hasLength(authHeader) || !StringUtils.startsWithIgnoreCase(authHeader, BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(BEARER_PREFIX.length());
        String userId = jwtService.extractUserID(jwt);

        if (StringUtils.hasLength(userId) && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDto user = userService.getUser(Long.valueOf(userId));

            System.out.println("valid? ");

            // Если токен валиден, то аутентифицируем пользователя
            if (jwtService.isTokenValid(jwt, user.getId())) {
                SecurityContext context = SecurityContextHolder.createEmptyContext();

                var authToken = new UsernamePasswordAuthenticationToken(
                        user, null, Collections.emptyList()
                        //user.getAuthorities()
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            }
        }

        filterChain.doFilter(request, response);
    }
}
