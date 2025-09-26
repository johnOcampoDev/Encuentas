package com.encuestas.question.entity;

import java.util.ArrayList;
import java.util.List;

import com.encuestas.answer.entity.Answer;
import com.encuestas.survey.entity.Survey;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question extends PanacheEntity {
	public String text;
	public String type;

	@ManyToOne
	@JoinColumn(name = "survey_id", nullable = false)
	public Survey survey;

	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<Answer> answers = new ArrayList<>();
}
