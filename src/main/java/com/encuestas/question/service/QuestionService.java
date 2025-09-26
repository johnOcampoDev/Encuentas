package com.encuestas.question.service;

import com.encuestas.question.entity.Question;
import com.encuestas.question.repository.QuestionRepository;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class QuestionService {

    @Inject
    QuestionRepository questionRepository;

    public Multi<Question> listAll() {
        return Multi.createFrom().iterable(questionRepository.listAll());
    }

    public Uni<Question> findById(Long id) {
        return Uni.createFrom().item(() -> questionRepository.findById(id));
    }

    @Transactional
    public Uni<Question> create(Question question) {
        return Uni.createFrom().item(() -> {
            questionRepository.persist(question);
            return question;
        });
    }

    @Transactional
    public Uni<Question> update(Long id, Question question) {
        return Uni.createFrom().item(() -> {
            Question entity = questionRepository.findById(id);
            if (entity == null) return null;
            entity.text = question.text;
            entity.type = question.type;
            return entity;
        });
    }

    @Transactional
    public Uni<Boolean> delete(Long id) {
        return Uni.createFrom().item(() -> questionRepository.deleteById(id));
    }
}
