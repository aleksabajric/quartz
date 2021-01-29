package com.demo.quartz.config;

import com.demo.quartz.domain.tokendata.TokenData;
import com.demo.quartz.service.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class BearerTokenFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        if(authorizationHeader != null) {
            String[] tokens = authorizationHeader.trim().split(" ");
            if("Bearer".equals(tokens[0]) && !"".equals(tokens[1])) {
                TokenData tokenData = tokenProvider.decryptToken(tokens[1]);
                Authentication authentication = new UsernamePasswordAuthenticationToken(new UserDetailsDto(tokenData), null, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
