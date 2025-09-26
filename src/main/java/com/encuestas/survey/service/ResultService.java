package com.encuestas.survey.service;

import java.util.List;
import java.util.stream.Collectors;

import com.encuestas.common.dto.QuestionResultDTO;
import com.encuestas.survey.entity.Survey;
import com.encuestas.survey.repository.SurveyRepository;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ResultService {

	@Inject
	SurveyRepository surveyRepository;

	public Uni<List<QuestionResultDTO>> getResultsBySurvey(Long surveyId) {
		return Uni.createFrom().item(() -> {
			Survey survey = surveyRepository.findById(surveyId);
			if (survey == null)
				return null;

			return survey.questions.stream()
					.map(q -> new QuestionResultDTO(q.id, q.text,
							q.answers.stream().map(a -> a.value).collect(Collectors.toList())))
					.collect(Collectors.toList());
		});
	}
}