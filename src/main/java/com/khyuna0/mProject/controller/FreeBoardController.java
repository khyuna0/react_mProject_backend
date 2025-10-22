package com.khyuna0.mProject.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.khyuna0.mProject.dto.BoardRequestDto;
import com.khyuna0.mProject.entity.FreeBoard;
import com.khyuna0.mProject.entity.SiteUser;
import com.khyuna0.mProject.repository.FreeBoardRepository;
import com.khyuna0.mProject.repository.UserRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/api/board")
public class FreeBoardController {
		
	@Autowired
	private FreeBoardRepository freeBoardRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping
	public ResponseEntity<?> getPagedBoard(@RequestParam(name="page", defaultValue = "0") int page, 
			@RequestParam(name = "size" , defaultValue = "10") int size) {
		if(page < 0) {
			page = 0;
		}
		if(size <=0 ) {
			size = 10;
		}
		
		Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
		Page<FreeBoard> pagedBoard = freeBoardRepository.findAll(pageable);
		
		Map<String, Object> pagingMap = new HashMap<>();
		pagingMap.put("posts", pagedBoard.getContent());
		pagingMap.put("currentPage", pagedBoard.getNumber());
		pagingMap.put("totalPages", pagedBoard.getTotalPages());
		pagingMap.put("totalItems", pagedBoard.getTotalElements());
		
		return ResponseEntity.ok(pagingMap);
	}
	
	// 아이디로 특정 게시판 글 불러오기
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") Long id) { 
		Optional<FreeBoard> board = freeBoardRepository.findById(id);
		FreeBoard freeBoard = board.get();
		freeBoard.setHit(freeBoard.getHit()+1); // 조회수
				
		if(board.isPresent()) {
			freeBoardRepository.save(freeBoard); 
			return ResponseEntity.ok(board.get());
		} else {
			return ResponseEntity.status(404).body("해당 게시물을 찾을 수 없습니다.");
		}
	}
	
	// 게시판 글 쓰기
	@PostMapping
	public ResponseEntity<?> write(@RequestBody @Valid BoardRequestDto boardDto, BindingResult bindingResult, 
			Authentication auth) {
		
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
		FreeBoard board = new FreeBoard();
		board.setAuthor(user.get());
		board.setTitle(boardDto.getTitle());
		board.setContent(boardDto.getContent());
		board.setCreateDate(LocalDateTime.now());
		board.setHit(0);
		freeBoardRepository.save(board);
		
		return ResponseEntity.ok().body("글쓰기 성공");
	}
		
	// 게시판 글 수정
	@PostMapping("/{id}")
	public ResponseEntity<?> Edit(@PathVariable("id") Long id, @RequestBody @Valid BoardRequestDto boardDto, BindingResult bindingResult, Authentication auth) {
		
		Optional<FreeBoard> board = freeBoardRepository.findById(id);
		
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
		
		FreeBoard freeBoard = board.get();
		freeBoard.setTitle(boardDto.getTitle());
		freeBoard.setContent(boardDto.getContent());
		freeBoardRepository.save(freeBoard);
		
		return ResponseEntity.ok().body("수정 성공");
	}
			
	// 게시판 글 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<?> DeleteById(@PathVariable("id") Long id , Authentication auth) { 
		Optional<FreeBoard> board = freeBoardRepository.findById(id);
		
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
		
		freeBoardRepository.deleteById(id);
		return ResponseEntity.ok().body("삭제 성공");
		
	}
	
}
