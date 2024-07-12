package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

import jakarta.transaction.Transactional;

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

}
