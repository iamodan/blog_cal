package com.cos.blog.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

// html파일이 아니라 data를 리턴해주는 controller = RestController
@RestController
public class DummyControllerTest {
	
	@Autowired
	private UserRepository userRepository;

	@PostMapping("/dummy/join")
	public String join(User user) {
//		System.out.println("id :::   " + user.getId());
		System.out.println("username :::   " + user.getUsername());
		System.out.println("password :::   " + user.getPassword());
		System.out.println("username :::   " + user.getEmail());
//		System.out.println("role :::   " + user.getRole());
//		System.out.println("createDate :::   " + user.getCreateDate());
		
		user.setRole(RoleType.USER); // ENUM
		userRepository.save(user);
		return "会員登録完了";
	}
} // end class
