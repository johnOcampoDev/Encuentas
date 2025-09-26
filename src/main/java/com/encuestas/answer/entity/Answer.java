package com.encuestas.answer.entity;

import com.encuestas.question.entity.Question;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer extends PanacheEntity {
	public String value;

	@ManyToOne
	@JoinColumn(name = "question_id", nullable = false)
	public Question question;
}
