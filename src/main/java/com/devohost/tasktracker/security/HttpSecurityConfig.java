package com.devohost.tasktracker.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.annotation.Resource;

@EnableWebSecurity
public class HttpSecurityConfig {
    @Order(1)
    @Configuration
    public static class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Resource
        private BCryptPasswordEncoder encoder;
        @Resource
        private CustomUserDetailService userService;

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userService).passwordEncoder(encoder);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .antMatcher("/api/**")
                    .authorizeRequests()
                    .antMatchers("/sign-up").permitAll()
                    .anyRequest()
                    .authenticated()

                    .and()
                    .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                    .addFilter(new JWTAuthenticationFilter(authenticationManager()));
        }
    }

    @Order(2)
    @Configuration
    public static class FormLoginConfig extends WebSecurityConfigurerAdapter {

        @Resource
        private BCryptPasswordEncoder encoder;
        @Resource
        private CustomUserDetailService userService;
        @Resource
        private CustomSuccessHandler successHandler;

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userService).passwordEncoder(encoder);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.cors().and().csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/sign-up").permitAll()
                    .antMatchers("/").permitAll()
                    .antMatchers("/sign-in").permitAll()
                    .antMatchers("/tasks").authenticated()
                    .antMatchers("/dashboard/**").hasAnyAuthority("ADMIN")
                    .anyRequest()
                    .authenticated()

                    .and()
                    .formLogin()
                    .loginPage("/sign-in")
                    .permitAll()
                    .failureUrl("/sign-in?error=true")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .successHandler(successHandler)

                    .and()
                    .logout()
                    .permitAll()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessUrl("/")
                    .and()
                    .exceptionHandling();
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers(
                    "/resources/**", "/static/**", "/html/**", "/css/**", "/js/**"
            );
        }
    }
}
