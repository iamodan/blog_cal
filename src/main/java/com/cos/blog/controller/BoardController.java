package com.cos.blog.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.cos.blog.config.auth.PrincipalDetail;

@Controller
public class BoardController {
	
//	@Autowired
//	private PrincipalDetail principal;
	
//	@AuthenticationPrincipal PrincipalDetail principal
	@GetMapping({"","/"})
	public String index(@AuthenticationPrincipal PrincipalDetail principal) {
		System.out.println("Login ID: " + principal.getUsername());
		return "index";
	}
}
