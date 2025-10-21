package com.khyuna0.mProject.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Long id; // 댓글 고유 회원 번호
	
	@Column
	@NotBlank
	private String content; // 글 내용
	
	@Column
	@CreationTimestamp 
	private LocalDateTime createDate;
	
	@ManyToOne (fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private SiteUser author; // siteUser 조인, N : 1 , 게시판 글쓴이
	
	@ManyToOne (fetch = FetchType.LAZY)
	@JsonIgnore
	private FreeBoard board;
}
