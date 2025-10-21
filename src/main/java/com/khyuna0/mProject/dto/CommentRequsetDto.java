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
public class CommentRequsetDto {// vaild용

	@NotBlank ( message = "내용을 입력해 주세요.")
	@Size( min = 1, message = "댓글 내용은 최소 1글자 이상이어야 합니다.")
	private String content; // 글 내용
}
