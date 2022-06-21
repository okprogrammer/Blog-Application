package com.blog.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

import static com.blog.config.AppConstants.UNABLE_TO_GET_TOKEN;
import static com.blog.config.AppConstants.JWT_TOKEN_EXPIRED;
import static com.blog.config.AppConstants.AUTHORIZATION_BEARER_NAME;;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. get token
        String requestToken = request.getHeader("Authorization");

        // Bearer 23344443fggg

        System.out.println(requestToken);

        String username = null;

        String token = null;

        if (requestToken != null && requestToken.startsWith(AUTHORIZATION_BEARER_NAME)) {
            token = requestToken.substring(7);
            try {
                username = this.jwtTokenHelper.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                System.out.println(UNABLE_TO_GET_TOKEN);
            } catch (ExpiredJwtException e) {
                System.out.println(JWT_TOKEN_EXPIRED);
            } catch (MalformedJwtException e) {
                System.out.println("Invalid jwt exception");
            }

        } else {
            System.out.println("JWT token doesn't begin with Bearer");
        }

        // once we get the token, now validate
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (this.jwtTokenHelper.validateToken(token, userDetails)) {
                // going right
                // do authentication
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        } else {
            System.out.println("Invalid jwt token");
        }

        filterChain.doFilter(request, response);

    }

}
