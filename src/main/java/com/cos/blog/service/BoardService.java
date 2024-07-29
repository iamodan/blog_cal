package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.Board;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;

	public void 書き込み(Board board, User user) { // title content
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}

	@Transactional(readOnly = true)
	public Page<Board> 書き込み一覧(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public Board 書き込み詳細(int id) {
		return boardRepository.findById(id).orElseThrow(() -> {
			throw new IllegalArgumentException("書き込み詳細 失敗");
		});
	}

	@Transactional
	public void 書き込み削除(int id) {
		boardRepository.deleteById(id);
	}

	@Transactional
	public void 書き込み修正(int id, Board requestBoard) {
		Board board = boardRepository.findById(id).orElseThrow(() -> {
			throw new IllegalArgumentException("書き込み修正 失敗");
		}); // 영속화 완료 
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		// 해당 함수로 종료시(Service가 종료될 때) 트랜잭션이 종료됩니다. 이때 더티체킹이 일어남
	   // 자동 업데이트 db flush = commit

	}

}
