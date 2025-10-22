package com.khyuna0.mProject.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.khyuna0.mProject.dto.SiteUserRequestDto;
import com.khyuna0.mProject.entity.SiteUser;
import com.khyuna0.mProject.repository.UserRepository;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/api/auth")
public class AuthController {

	private final SecurityFilterChain filterChain;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	public AuthController(SecurityFilterChain filterChain) {
		this.filterChain = filterChain;
	}
	
	// 회원가입
	@PostMapping("/signup")
	public ResponseEntity<?> join(@RequestBody @Valid SiteUserRequestDto req, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) { //참이면 유효성 체크 실패->error 발생
			Map<String, String> errors = new HashMap<>();
			bindingResult.getFieldErrors().forEach(
				err -> {
					errors.put(err.getField(), err.getDefaultMessage());					
				}
			);
			if (!req.getPassword().equals(req.getPasswordCheck())) {
				errors.put("비밀번호 확인이 일치하지 않습니다.", "비밀번호 확인이 일치하지 않습니다.");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호 불일치");
			}
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
		}
		SiteUser siteUser = new SiteUser();
		siteUser.setUsername(req.getUsername());
		siteUser.setPassword(req.getPassword());
		
		if(userRepository.findByUsername(siteUser.getUsername()).isPresent()) { // 아이디 중복 여부 검사
			
			Map<String, String> error = new HashMap<>();
			error.put("아이디 중복", "이미 존재하는 아이디입니다.");					
			return ResponseEntity.badRequest().body(error);
		}
		siteUser.setPassword(passwordEncoder.encode(req.getPassword()));
		userRepository.save(siteUser);
		
		return ResponseEntity.ok().body("가입 성공");
		
	}
	
	// 현재 로그인한 username 가져오기
	@GetMapping("/me")
	   public ResponseEntity<?> getCurrentUser(Authentication authentication) {
	       if (authentication == null || !authentication.isAuthenticated()) {
	           return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED)
	                   .body(Map.of("error", "로그인되지 않음"));
	       }
	       return ResponseEntity.ok(Map.of("username", authentication.getName()));
	   }
	
}
