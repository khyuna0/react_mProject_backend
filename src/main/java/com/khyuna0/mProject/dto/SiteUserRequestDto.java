package com.khyuna0.mProject.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Entity;
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
public class SiteUserRequestDto {

	@NotBlank
	@Length (min = 5, message = "아이디는 5글자 이상 입력해주세요")
	private Long id;
	
	@NotBlank
	@Length (min = 5, message = "비밀번호는 5글자 이상 입력해주세요")
	private String password;
	
	@NotBlank
	private String passwordCheck; // 비밀번호 확인 용
}
