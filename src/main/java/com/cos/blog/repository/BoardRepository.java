package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cos.blog.model.Board;
import com.cos.blog.model.User;

// DAO
// 자동으로 빈등록
@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
	
//	Optional<User> findByUsername(String username);
	
}
