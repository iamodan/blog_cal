package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cos.blog.model.User;

// DAO
// 자동으로 빈등록
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	// 로그인을 위한 어떤 함수
	// SELECT * FROM user name = ?1 AND password = ?2;
	//	User findByUsernameAndPassword(String username, String password);

	//	@Query(value="SELECT  * From user WHERE username=?1 AND password=?2", nativeQuery = true)
	//	User login(String username, String password);
}
