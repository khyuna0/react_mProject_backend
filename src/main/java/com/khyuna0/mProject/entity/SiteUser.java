package com.khyuna0.mProject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class SiteUser { // 회원 정보
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Long id; // 유저 고유 회원 번호
	
	@Id
	@NotBlank
	private String username;
	
	@Column
	@NotBlank
	private String password;
	
}
