package com.encuestas.survey.entity;

import java.util.ArrayList;
import java.util.List;

import com.encuestas.question.entity.Question;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Survey extends PanacheEntity {
	public String title;
	public String description;

	@OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<Question> questions = new ArrayList<>();
}