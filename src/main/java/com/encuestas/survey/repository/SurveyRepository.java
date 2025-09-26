package com.encuestas.survey.repository;

import com.encuestas.survey.entity.Survey;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SurveyRepository implements PanacheRepository<Survey> {
}
