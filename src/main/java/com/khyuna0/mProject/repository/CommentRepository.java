package com.khyuna0.mProject.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.khyuna0.mProject.entity.Comment;
import com.khyuna0.mProject.entity.FreeBoard;

public interface CommentRepository extends JpaRepository<Comment, Long>{
	
	// 자유게시판 댓글 불러오기
	public List<Comment> findByBoard(FreeBoard freeBoard);
	
	//
}
