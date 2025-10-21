package com.khyuna0.mProject.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FreeBoard { // 자유 게시판
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Long id; // 게시판 고유 회원 번호
	
	@Column
	@NotBlank
	private String title; // 글 제목
	
	@Column
	@NotBlank
	private String content; // 글 내용
	
	@Column
	@CreationTimestamp 
	private LocalDateTime createDate;
	
	@Column
	private int hit; // 글 조회수
	
	@ManyToOne
	private SiteUser author; // siteUser 조인, N : 1 , 게시판 글쓴이
}
