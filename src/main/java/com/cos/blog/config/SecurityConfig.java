package com.cos.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean // IoC
	public BCryptPasswordEncoder encodePWD() {
//		String encPassword = new BCryptPasswordEncoder().encode("1234");
		return new BCryptPasswordEncoder();
	}

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // ?? 잘모르지만 일단 
            .authorizeHttpRequests(authorize -> authorize
                .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll() // **FORWARDリクエスト例外**
/*
セキュリティ6.0バージョンからは、FORWARDリクエストに対して例外を設定する必要があります。
そうしないと、デフォルトで認証が必要となります。
 */
                .requestMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**").permitAll() 
                .anyRequest().authenticated()
            )
            .formLogin(formLogin -> formLogin
                .loginPage("/auth/loginForm")
                .permitAll() 
            );
        return http.build();
    }
}
