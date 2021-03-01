package com.devohost.tasktracker.security;

import com.devohost.tasktracker.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/auth", "POST"));

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            User current = new ObjectMapper().readValue(request.getInputStream(), User.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(current.getEmail(), current.getPassword(), new ArrayList<>()));

        } catch (IOException e) {
            e.printStackTrace();
        }

        // ROLE_ADMIN

        return super.attemptAuthentication(request, response);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        if (authResult.getPrincipal() != null){
           org.springframework.security.core.userdetails.User currentUser = (org.springframework.security.core.userdetails.User)authResult.getPrincipal();
           String email = currentUser.getUsername();
           if (email != null && email.length() > 0){
               Claims claims = Jwts.claims().setSubject(email);

               List<String> roles = new ArrayList<>();
               currentUser.getAuthorities().forEach(auth -> roles.add(auth.getAuthority()));

               claims.put("roles", roles);

               String token = Jwts.builder()
                       .setClaims(claims)
                       .setExpiration(new Date(System.currentTimeMillis() + SecurityTokenConstants.EXPIRATION))
                       .signWith(SignatureAlgorithm.HS512, SecurityTokenConstants.SECRET)
                       .compact();
               response.addHeader(SecurityTokenConstants.HEADER, SecurityTokenConstants.PREFIX + token);
           }
        }

    }
}