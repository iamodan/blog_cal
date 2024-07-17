package com.cos.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // IoC
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }

    @SuppressWarnings({ "removal", "deprecation" })
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests()
            .requestMatchers("/auth/**").permitAll() // '/auth/**' 경로에 대한 요청은 인증 없이 접근 허용
            .anyRequest().authenticated() // 그 외의 모든 요청은 인증 필요
            .and()
            .formLogin()
            .loginPage("/auth/loginForm") // 로그인 페이지의 URL을 '/auth/loginForm'으로 설정
            .loginProcessingUrl("/auth/login") // 로그인 처리 URL 설정
            .defaultSuccessUrl("/", true) // 로그인 성공 시 리디렉션할 기본 URL
            .permitAll()
            .and()
            .logout()
            .logoutUrl("/auth/logout")
            .logoutSuccessUrl("/auth/loginForm")
            .permitAll()
            .and()
            .csrf().disable(); // CSRF 보호 비활성화

        return http.build();
    }
}
