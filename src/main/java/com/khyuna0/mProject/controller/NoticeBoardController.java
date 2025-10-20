package com.khyuna0.mProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.khyuna0.mProject.repository.NoticeBoardRepository;
import com.khyuna0.mProject.repository.UserRepository;

@Controller
public class NoticeBoardController {
	
	@Autowired
	private NoticeBoardRepository noticeBoardRepository;
	
	@Autowired
	private UserRepository userRepository;
}
