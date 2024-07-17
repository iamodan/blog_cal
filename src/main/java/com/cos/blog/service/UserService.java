package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;



@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	//	@Transactional
	//	public int 会員登録(User user) {
	//		try {
	//			userRepository.save(user);
	//			return 1;
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//			System.out.println("UserService: 会員登録（）: " + e.getMessage());
	//		}
	//		return -1;
	//	}

	@Transactional
	public void 会員登録(User user) {
		userRepository.save(user);
	}
	
//	@Transactional(readOnly = true) // Select할 떄 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료
//	public User ログイン(User user) {
//		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//	}

}
