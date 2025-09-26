package com.encuestas.answer.service;

import com.encuestas.answer.entity.Answer;
import com.encuestas.answer.repository.AnswerRepository;
import com.encuestas.question.entity.Question;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;

@ApplicationScoped
public class AnswerService {

	@Inject
	AnswerRepository answerRepository;

	public Multi<Answer> listAll() {
		return Multi.createFrom().iterable(answerRepository.listAll());
	}

	public Uni<Answer> findById(Long id) {
		return Uni.createFrom().item(() -> answerRepository.findById(id));
	}

	@Transactional
	public Uni<Answer> create(Answer answer, Long questionId) {
		return Uni.createFrom().item(() -> {
			Question question = Question.findById(questionId);
			if (question == null) {
				throw new WebApplicationException("Question not found", 404);
			}
			answer.question = question; // 🔑 aquí seteamos la relación
			answerRepository.persist(answer);
			return answer;
		});
	}

	@Transactional
	public Uni<Answer> update(Long id, Answer answer) {
		return Uni.createFrom().item(() -> {
			Answer entity = answerRepository.findById(id);
			if (entity == null)
				return null;
			entity.value = answer.value;
			return entity;
		});
	}

	@Transactional
	public Uni<Boolean> delete(Long id) {
		return Uni.createFrom().item(() -> answerRepository.deleteById(id));
	}
}