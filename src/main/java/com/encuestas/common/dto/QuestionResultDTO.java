package com.encuestas.common.dto;

import java.util.List;

public class QuestionResultDTO {
	public Long questionId;
	public String questionText;
	public List<String> answers;

	public QuestionResultDTO(Long questionId, String questionText, List<String> answers) {
		this.questionId = questionId;
		this.questionText = questionText;
		this.answers = answers;
	}
}