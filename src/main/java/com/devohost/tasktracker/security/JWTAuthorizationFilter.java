package com.devohost.tasktracker.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(SecurityTokenConstants.HEADER);
        if (header == null || !header.startsWith(SecurityTokenConstants.PREFIX)) {
            chain.doFilter(request, response);
        } else {
            UsernamePasswordAuthenticationToken auth = parseToken(header);
            SecurityContextHolder.getContext().setAuthentication(auth);
            chain.doFilter(request, response);
        }
    }

    private UsernamePasswordAuthenticationToken parseToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SecurityTokenConstants.SECRET)
                .parseClaimsJws(token.replace(SecurityTokenConstants.PREFIX, ""))
                .getBody();
        String userEmail = claims.getSubject();
        List<String> roles = (List<String>) claims.get("roles");

        List<GrantedAuthority> listORles = new ArrayList<>();
        roles.forEach(r -> listORles.add(new SimpleGrantedAuthority(r)));

        if (userEmail != null) {
            return new UsernamePasswordAuthenticationToken(userEmail, null, listORles);
        }

        return null;

    }
}