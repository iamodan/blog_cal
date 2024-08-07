package com.cos.blog.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//인증이 안된 사용자들이 출입할 수 있는 경로를/auth/**허용
// 그냥 주소가 /이면 index.jsp허용
// static이하에 있는 /js/**./css/**./image/**

@Controller
public class UserController {

	@Value("${cos.key}")
	private String cosKey;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}

	@GetMapping("/auth/loginForm")
	public String loginForm() {
		System.out.println("check point(GetMapping) ::: /auth/loginForm");
		return "user/loginForm";
	}

	@GetMapping("/auth/kakao/callback")
	public String kakaoCallback(String code) { // Data를 리턴해주는 컨트롤러 함수

		// POST방식으로 Key=value 데이터를 요청(카카오쪽)으로

		/*
		 * d https://kauth.kakao.com/oauth/token grant_type=authorization_code
		 * client_id=a6dc7c061bba38ecf26964ea6933d38e
		 * redirect_uri=http://localhost:8080/auth/kakao/callback code=｛動的→今、分からない｝
		 * client_secret=必須ｘ
		 */

		RestTemplate rt = new RestTemplate();

		// HTTpHeader Object 生成
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// HTTpBody Object 生成
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "a6dc7c061bba38ecf26964ea6933d38e");
		params.add("redirect_uri", "http://localhost:8080/auth/kakao/callback");
		params.add("code", code);

		// ①HttpHeader ②HTTpBodyを一つのobjectにまとめる
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

		// Httpを送信する - POST方式で - そしてresponse変数に応答を受け取る
		ResponseEntity<String> response = rt.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST, kakaoTokenRequest, String.class);

		// Gson, Json Simple, Object Mapper
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oauthToken = null;

		try {
			oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		System.out.println("カカオ_Access_Token :" + oauthToken.getAccess_token());

		RestTemplate rt2 = new RestTemplate();

		// HTTpHeader Object 生成
		HttpHeaders headers2 = new HttpHeaders();

		headers2.add("Authorization", "Bearer " + oauthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// ①HttpHeader ②HTTpBodyを一つのobjectにまとめる
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = new HttpEntity<>(headers2);

		// Httpを送信する - POST方式で - そしてresponse変数に応答を受け取る
		ResponseEntity<String> response2 = rt2.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST, kakaoProfileRequest2, String.class);

//		return"カカオ引証完了! token access requestに対するresponse ::: "+response;

		System.out.println(response2.getBody());

		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;

		try {
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		if (kakaoProfile.getKakao_account().getEmail() == null) {
			kakaoProfile.getKakao_account().setEmail("kakao");
		} 
		
		// 현재 날짜와 시간 가져오기
		LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedNow = now.format(formatter);
        
        System.out.println("kakao id(no): " +formattedNow+"_"+kakaoProfile.getId() + "@" + kakaoProfile.getKakao_account().getEmail()+".com");
		System.out.println("kakao email: " + kakaoProfile.getKakao_account().getEmail());

		System.out.println("blog server username : " + kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId());
		System.out.println("blog server email : " + kakaoProfile.getKakao_account().getEmail());
		
		// UUID란 -> 중복되지 않는 어떤 특정 값을 만들어내는 알고리즘 그래서 여기에 못씀 중복되지 앟는 패스트워드라서 인증이 안됨 ㅇㅋ?
		System.out.println("blog server password : "+cosKey);
			
		User kakaoUser = User.builder()
				.username(kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId())
				.password(cosKey)
				.email(formattedNow+"_"+kakaoProfile.getId() + "@" + kakaoProfile.getKakao_account().getEmail()+".com")
				.build();
		
		// 가입자 혹은 비가입자 체크 해서 처리
		User originUser =  userService.会員探し(kakaoUser.getUsername());
		
		if(originUser.getUsername() == null) {
			System.out.println("会員情報がないので、自動会員登録実行");
			userService.会員登録(kakaoUser);
		}else {
			System.out.println("기존회원");
		}
		
		// null이 아니면 로그인 처리 하면 됨
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), cosKey));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
        // Authentication 객체의 주요 속성 출력
        System.out.println("Authentication Name: " + authentication.getName());
        System.out.println("Authentication Authorities: " + authentication.getAuthorities());
        System.out.println("Authentication Details: " + authentication.getDetails());
        System.out.println("Authentication Principal: " + authentication.getPrincipal());
        System.out.println("Authentication Credentials: " + authentication.getCredentials());
        System.out.println("Is Authentication Authenticated: " + authentication.isAuthenticated());
		
		return"redirect:/";
	}

	@GetMapping("/user/updateForm")
	public String updateForm(@AuthenticationPrincipal PrincipalDetail principal) {
		return "user/updateForm";
	}

}
