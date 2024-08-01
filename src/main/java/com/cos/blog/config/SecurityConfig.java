package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.cos.blog.config.auth.PrincipalDetailService;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private PrincipalDetailService principalDetailService;

//	@Bean
//	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//		return authenticationConfiguration.getAuthenticationManager();
//	}

	@Bean // IoC
	public BCryptPasswordEncoder encodePWD() {
//		String encPassword = new BCryptPasswordEncoder().encode("1234");
		return new BCryptPasswordEncoder();
	}

	// 시큐리티가 대신 로그인해주는데 password를 가로채기를 하는데
	// 해당 password가 뭘로 해쉬가 되어 회원가입이 되었는지 알아야
	// 같은 ㅎ새쉬로 암호화해서 DB에 있는 해쉬랑 비교할 수 있음.
	// AuthenticationManager 설정
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, PrincipalDetailService userDetailService) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(userDetailService).passwordEncoder(bCryptPasswordEncoder);
		return authenticationManagerBuilder.build();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()) // ?? 잘모르지만 일단
				.authorizeHttpRequests(authorize -> authorize.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll() // **FORWARDリクエスト例外**
						/*
						 * セキュリティ6.0バージョンからは、FORWARDリクエストに対して例外を設定する必要があります。 そうしないと、デフォルトで認証が必要となります。
						 */
						.requestMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**", "/dummy/**").permitAll().anyRequest().authenticated())
				.formLogin(formLogin -> formLogin.loginPage("/auth/loginForm").loginProcessingUrl("/auth/loginProc") // 추가한 부분
						.defaultSuccessUrl("/") // 추가한 부분
//                    .failureUrl("/fail")
						.permitAll());
		return http.build();
	}
}
