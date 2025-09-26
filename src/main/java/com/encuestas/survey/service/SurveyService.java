package com.encuestas.survey.service;

import com.encuestas.survey.entity.Survey;
import com.encuestas.survey.repository.SurveyRepository;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class SurveyService {

	@Inject
	SurveyRepository surveyRepository;

	public Multi<Survey> listAll() {
		return Multi.createFrom().iterable(surveyRepository.listAll());
	}

	public Uni<Survey> findById(Long id) {
		return Uni.createFrom().item(() -> surveyRepository.findById(id));
	}

	@Transactional
	public Uni<Survey> create(Survey survey) {
		return Uni.createFrom().item(() -> {
			surveyRepository.persist(survey);
			return survey;
		});
	}

	@Transactional
	public Uni<Survey> update(Long id, Survey survey) {
		return Uni.createFrom().item(() -> {
			Survey entity = surveyRepository.findById(id);
			if (entity == null)
				return null;
			entity.title = survey.title;
			entity.description = survey.description;
			return entity;
		});
	}

	@Transactional
	public Uni<Boolean> delete(Long id) {
		return Uni.createFrom().item(() -> surveyRepository.deleteById(id));
	}
}