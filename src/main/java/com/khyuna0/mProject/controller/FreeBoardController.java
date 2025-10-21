package com.khyuna0.mProject.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.khyuna0.mProject.dto.BoardRequestDto;
import com.khyuna0.mProject.entity.FreeBoard;
import com.khyuna0.mProject.repository.FreeBoardRepository;
import com.khyuna0.mProject.repository.UserRepository;
import com.sun.tools.javac.resources.CompilerProperties.Errors;

import jakarta.validation.Valid;

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
	
	// 게시판 글 쓰기
	@PostMapping
	public ResponseEntity<?> write(@RequestBody @Valid BoardRequestDto BoardDto, BindingResult bindingResult) {
		
		//TODO : 게시글 작성 권한 확인
		if(bindingResult.hasErrors()) {
			Map<String, String>  errors = new HashMap<>();
			bindingResult.getFieldErrors().forEach(
				err -> {
				errors.put(err.getField(), err.getDefaultMessage());
			});
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
		}
		
	}
	
	
	// 게시판 글 수정
	
	
	
	
	// 게시판 글 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<?> DeleteById(@PathVariable("id") Long id) { 
		Optional<FreeBoard> board = freeBoardRepository.findById(id);
		
		//TODO : 게시글 권한 확인 추가
		
		
		if(!board.isPresent()) {
			return ResponseEntity.status(404).body("해당 게시물을 찾을 수 없습니다.");
		}
		
		freeBoardRepository.deleteById(id);
		return ResponseEntity.ok().body("삭제 성공");
		
	}
	
	
	
	// 게시판 글 검색
}
