package com.khyuna0.mProject.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SiteUserRequestDto {

	@NotBlank
	@Size (min = 5, message = "아이디는 5글자 이상 입력해주세요")
	private String username;
	
	@NotBlank
	@Size (min = 5, message = "비밀번호는 5글자 이상 입력해주세요")
	private String password;
	
	@NotBlank
	@Size (min = 5, message = "비밀번호는 5글자 이상 입력해주세요")
	private String passwordCheck; // 비밀번호 확인 용
}
