package com.khyuna0.mProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.khyuna0.mProject.repository.UserRepository;

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
}
