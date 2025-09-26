package com.encuestas.answer.service;

import com.encuestas.answer.entity.Answer;
import com.encuestas.answer.repository.AnswerRepository;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

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
	public Uni<Answer> create(Answer answer) {
		return Uni.createFrom().item(() -> {
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