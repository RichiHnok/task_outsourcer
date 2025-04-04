package com.richi.web_part.configuration;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices.RememberMeTokenAlgorithm;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    UserDetailsService userDetailsService() {
        return new MyUserDetailsService();
    }

    @Bean
    AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        SecurityFilterChain securityFilterChain = http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> {
            auth.requestMatchers("/").permitAll();
            auth.requestMatchers("/css/**", "/js/**", "/images/**").permitAll();
            auth.requestMatchers("/register", "/login").anonymous();
            auth.requestMatchers(
                "/task"
                , "/task/**"
                , "/taskHistory"
                , "/personal"
                , "/tasks"
                , "/placeholder"
                , "/download/taskToProc/**"
            ).hasAnyAuthority("ROLE_USER", "ROLE_ADMIN");
            auth.requestMatchers(
                "/editor"
                , "/editor/**"
                , "/controlPanel/**"
                , "/test/**"
                , "/download/taskSample/**"
            ).hasAuthority("ROLE_ADMIN");
        })
        // .rememberMe(remember -> remember
        //     .rememberMeServices(rememberMeServices(userDetailsService()))
        // )
        .formLogin(form -> form
            .loginPage("/login")
            .successHandler(customAuthenticationSuccessHandler())
            .defaultSuccessUrl("/", true)
            .permitAll(false)
        )
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/")
            .deleteCookies("remember-me")
            .permitAll()
        )
        .build();
        return securityFilterChain;
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(5);
    }

    public AuthenticationSuccessHandler customAuthenticationSuccessHandler(){
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request
                , HttpServletResponse response
                , Authentication authentication
            ) throws IOException
                , ServletException
            {
                boolean isAdmin = authentication.getAuthorities().stream()
                        .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("admin"));
                if(isAdmin){
                    response.sendRedirect("/");
                }else{
                    response.sendRedirect("/");
                }
            }
        };
    }

    @Bean //TODO Из-за сохранения сессий пользователей в редисе эта штука стала не нужна? Я её пока убиарть не буду, просто из filterchain уберу
    RememberMeServices rememberMeServices(UserDetailsService userDetailsService){
        RememberMeTokenAlgorithm encodingAlgorithm = RememberMeTokenAlgorithm.SHA256;
        TokenBasedRememberMeServices rememberMe = new TokenBasedRememberMeServices("key", userDetailsService, encodingAlgorithm);
        rememberMe.setMatchingAlgorithm(RememberMeTokenAlgorithm.MD5);
        rememberMe.setTokenValiditySeconds(3600);
        return rememberMe;
    }
}
