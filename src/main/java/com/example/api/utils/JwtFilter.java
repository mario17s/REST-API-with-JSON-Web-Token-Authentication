package com.example.api.utils;

import com.example.api.serv.UserDetailsImpl;
import com.example.api.serv.UserDetailsServiceImpl;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private JwtUtil util;

    private UserDetailsServiceImpl service;

    public JwtFilter(JwtUtil util, UserDetailsServiceImpl service) {
        this.util = util;
        this.service = service;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String token = extractJwtToken(request);
            if(token != null && util.validateJwtToken(token)){
                String username = util.getUsernameFromJwtToken(token);
                UserDetails ud = service.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken upat =
                       new UsernamePasswordAuthenticationToken(ud, null, ud.getAuthorities());
                upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(upat);
            }
        }catch(Exception e){
            return;
        }
        filterChain.doFilter(request, response);
    }

    private String extractJwtToken(HttpServletRequest httpServletRequest){
        //Bearer egfheghgkehgwhgkgwhkghwergl
        String header = httpServletRequest.getHeader("Authorization");
        if(StringUtils.hasText(header) && header.startsWith("Bearer ")){
            return header.substring(7);
        }
        return null;
    }
}
