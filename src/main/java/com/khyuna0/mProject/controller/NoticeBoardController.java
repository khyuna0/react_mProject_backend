package com.khyuna0.mProject.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.khyuna0.mProject.dto.NoticeboardRequestDto;
import com.khyuna0.mProject.entity.NoticeBoard;
import com.khyuna0.mProject.entity.SiteUser;
import com.khyuna0.mProject.repository.NoticeBoardRepository;
import com.khyuna0.mProject.repository.UserRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/api/notice")
public class NoticeBoardController { // 공지 게시판 - admin만 작성 가능 예정
	
	@Autowired
	private NoticeBoardRepository noticeBoardRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	// 게시판 모든 글 목록 (페이징 처리 x)
		@GetMapping
		public ResponseEntity<?> getBoard() {
			List<NoticeBoard> boardlist = noticeBoardRepository.findAll();
			return ResponseEntity.ok(boardlist); 
		}
		
		// 아이디로 특정 게시판 글 불러오기
		@GetMapping("/{id}")
		public ResponseEntity<?> getById(@PathVariable("id") Long id) { 
			Optional<NoticeBoard> board = noticeBoardRepository.findById(id);
			NoticeBoard freeBoard = board.get();
			freeBoard.setHit(freeBoard.getHit()+1); // 조회수
					
			if(board.isPresent()) {
				noticeBoardRepository.save(freeBoard); 
				return ResponseEntity.ok(board.get());
			} else {
				return ResponseEntity.status(404).body("해당 게시물을 찾을 수 없습니다.");
			}
		}
		
		// 게시판 글 쓰기
		@PostMapping
		public ResponseEntity<?> write(@RequestBody @Valid NoticeboardRequestDto boardDto, BindingResult bindingResult, Authentication auth) {
			
			// 게시글 작성 권한 확인 (로그아웃 상태 방어)
			if (auth == null) {
			return ResponseEntity.badRequest().body("로그아웃 상태에서는 접근 불가능합니다.");
			}
		
			if(bindingResult.hasErrors()) {
				Map<String, String>  errors = new HashMap<>();
				bindingResult.getFieldErrors().forEach(
					err -> {
					errors.put(err.getField(), err.getDefaultMessage());
				});
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
			}
			
			Optional<SiteUser> user = userRepository.findByUsername(auth.getName());
			NoticeBoard board = new NoticeBoard();
			board.setAuthor(user.get());
			board.setTitle(boardDto.getTitle());
			board.setContent(board.getContent());
			board.setCreateDate(LocalDateTime.now());
			board.setHit(0);
			noticeBoardRepository.save(board);
			
			return ResponseEntity.ok().body("글쓰기 성공");
		}
			
		// 게시판 글 수정
		@PostMapping("/{id}")
		public ResponseEntity<?> Edit(@PathVariable("id") Long id, @RequestBody @Valid NoticeboardRequestDto boardDto, BindingResult bindingResult, Authentication auth) {
			
			Optional<NoticeBoard> board = noticeBoardRepository.findById(id);
			
			if(board.isEmpty()) { // 게시글 존재하지 않은 경우 방어
				return ResponseEntity.badRequest().body("없는 게시물입니다.");
			}
			
			if (auth == null) { // 게시글 작성 권한 확인 (로그아웃 상태 방어)
				return ResponseEntity.badRequest().body("로그아웃 상태에서는 접근 불가능합니다.");
			}
			// 해당 글 작성 유저만 권한 부여
			if (!board.get().getAuthor().getUsername().equals(auth.getName())) {
				return ResponseEntity.badRequest().body("해당 글에 대한 권한이 없습니다.");
			}
			
			// valid
			if(bindingResult.hasErrors()) {
				Map<String, String>  errors = new HashMap<>();
				bindingResult.getFieldErrors().forEach(
					err -> {
					errors.put(err.getField(), err.getDefaultMessage());
				});
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
			}
			
			NoticeBoard noticeBoard = board.get();
			noticeBoard.setTitle(boardDto.getTitle());
			noticeBoard.setContent(boardDto.getContent());
			noticeBoardRepository.save(noticeBoard);
			
			return ResponseEntity.ok().body("수정 성공");
		}
				
		// 게시판 글 삭제
		@DeleteMapping("/{id}")
		public ResponseEntity<?> DeleteById(@PathVariable("id") Long id , Authentication auth) { 
			Optional<NoticeBoard> board = noticeBoardRepository.findById(id);
			
			if (auth == null) {
				return ResponseEntity.badRequest().body("로그아웃 상태에서는 접근 불가능합니다.");
				}
			
			// 해당 글 작성 유저만 권한 부여
			if (!board.get().getAuthor().getUsername().equals(auth.getName())) {
				return ResponseEntity.badRequest().body("해당 글에 대한 권한이 없습니다.");
			}
			
			if(!board.isPresent()) {
				return ResponseEntity.status(404).body("해당 게시물을 찾을 수 없습니다.");
			}
			
			noticeBoardRepository.deleteById(id);
			return ResponseEntity.ok().body("삭제 성공");
			
		}
}
