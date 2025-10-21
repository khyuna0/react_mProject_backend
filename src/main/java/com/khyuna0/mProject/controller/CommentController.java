package com.khyuna0.mProject.controller;

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

import com.khyuna0.mProject.dto.CommentRequsetDto;
import com.khyuna0.mProject.entity.Comment;
import com.khyuna0.mProject.entity.FreeBoard;
import com.khyuna0.mProject.entity.SiteUser;
import com.khyuna0.mProject.repository.CommentRepository;
import com.khyuna0.mProject.repository.FreeBoardRepository;
import com.khyuna0.mProject.repository.UserRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/api/comments")
public class CommentController {
	
	@Autowired
	private FreeBoardRepository freeBoardRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	// 모든 댓글 목록 불러오기
	@GetMapping
	public ResponseEntity<?> getFreeBoardComments(@PathVariable("id") Long id) {
		List<Comment> commentlist = commentRepository.findByBoard(freeBoardRepository.findById(id).get());
		return ResponseEntity.ok(commentlist); 
	}
	
	//  댓글 쓰기
		@PostMapping
		public ResponseEntity<?> write(@PathVariable("id") Long id, @RequestBody @Valid CommentRequsetDto commentDto,
				BindingResult bindingResult, Authentication auth) {
			
			// 게시글 작성 권한 확인 (로그아웃 상태 방어)
			if (auth == null) {
			return ResponseEntity.badRequest().body("로그아웃 상태에서는 접근 불가능합니다.");
			}
		
			if(bindingResult.hasErrors()) { // 유효성 체크
				Map<String, String>  errors = new HashMap<>();
				bindingResult.getFieldErrors().forEach(
					err -> {
					errors.put(err.getField(), err.getDefaultMessage());
				});
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
			}
			Optional<SiteUser> user = userRepository.findByUsername(auth.getName());
			Comment comment = new Comment();
			comment.setAuthor(user.get());
			comment.setFreeBoard(freeBoardRepository.findById(id).get());
			comment.setContent(commentDto.getContent());
			
			return ResponseEntity.ok().body("댓글 작성 성공");
		
		}
		
		// 댓글 삭제
		@DeleteMapping("/{id}")
		public ResponseEntity<?> DeleteById(@PathVariable("id") Long id) { 
			Optional<Comment> comment = commentRepository.findById(id);
			
			//TODO : 게시글 권한 확인 추가
	
			
			if(!comment.isPresent()) {
				return ResponseEntity.status(404).body("해당 게시물을 찾을 수 없습니다.");
			}
			
			freeBoardRepository.deleteById(id);
			return ResponseEntity.ok().body("삭제 성공");
			
		}
}
