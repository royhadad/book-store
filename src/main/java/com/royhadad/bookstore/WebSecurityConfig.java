package com.royhadad.bookstore;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().antMatchers("/styles/*", "/scripts/*", "/error", "/books", "/admin")
                .permitAll().and().authorizeRequests().antMatchers("/api/shopping-cart").access("hasRole('ROLE_USER')")
                .antMatchers("/api/books", "/api/books/*").access("hasRole('ROLE_ADMIN')").anyRequest().authenticated()
                .and().formLogin().loginPage("/login").permitAll().and().logout().permitAll();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder().username("user").password("password").roles("USER")
                .build();
        UserDetails admin = User.withDefaultPasswordEncoder().username("admin").password("pass").roles("ADMIN").build();
        return new InMemoryUserDetailsManager(user, admin);
    }
}