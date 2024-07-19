package com.cos.blog.test;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncTest {
	
	@Test
	public void hash_test() {
		String encPassword = new BCryptPasswordEncoder().encode("cal");
		System.out.println("cal_hash_暗号化 ::::" +encPassword);
	}

}
