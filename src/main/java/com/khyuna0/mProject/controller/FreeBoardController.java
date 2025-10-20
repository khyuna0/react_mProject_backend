package com.khyuna0.mProject.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.khyuna0.mProject.entity.FreeBoard;
import com.khyuna0.mProject.repository.FreeBoardRepository;
import com.khyuna0.mProject.repository.UserRepository;

@Controller
@RequestMapping("/api/board")
public class FreeBoardController {
		
	@Autowired
	private FreeBoardRepository freeBoardRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	// 게시판 모든 글 목록 (페이징 처리 x)
	@GetMapping
	public ResponseEntity<?> getBoard() {
		List<FreeBoard> boardlist = freeBoardRepository.findAll();
		return ResponseEntity.ok(boardlist); 
	}
	
	// 아이디로 특정 게시판 글 불러오기
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") Long id) { 
		Optional<FreeBoard> board = freeBoardRepository.findById(id);
		if(board.isPresent()) {
			return ResponseEntity.ok(board.get());
		} else {
			return ResponseEntity.status(404).body("해당 게시물을 찾을 수 없습니다.");
		}
	}
}
