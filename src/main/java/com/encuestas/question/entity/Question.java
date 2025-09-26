package com.encuestas.question.entity;

import java.util.List;

import com.encuestas.answer.entity.Answer;
import com.encuestas.survey.entity.Survey;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "questions")
public class Question extends PanacheEntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	@NotBlank
	public String text;

	public String type; // Ej: "text", "multiple-choice"

	@ManyToOne
	@JoinColumn(name = "survey_id")
	public Survey survey;

	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<Answer> answers;
}
