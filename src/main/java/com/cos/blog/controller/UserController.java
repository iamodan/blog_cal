package com.cos.blog.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.blog.config.auth.PrincipalDetail;

//인증이 안된 사용자들이 출입할 수 있는 경로를/auth/**허용
// 그냥 주소가 /이면 index.jsp허용
// static이하에 있는 /js/**./css/**./image/**

@Controller
public class UserController {

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
	public @ResponseBody String kakaoCallback(String code) { // Data를 리턴해주는 컨트롤러 함수
		return "カカオ引証コード値 :::" + code;
	}

	@GetMapping("/user/updateForm")
	public String updateForm(@AuthenticationPrincipal PrincipalDetail principal) {
		return "user/updateForm";
	}

}
