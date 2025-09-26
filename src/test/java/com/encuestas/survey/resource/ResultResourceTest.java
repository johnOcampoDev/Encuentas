package com.encuestas.survey.resource;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;

import com.encuestas.answer.entity.Answer;
import com.encuestas.question.entity.Question;
import com.encuestas.survey.entity.Survey;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

@QuarkusTest
public class ResultResourceTest {

	private Long createSurvey() {
		Survey survey = new Survey();
		survey.title = "Encuesta de resultados";
		survey.description = "Prueba resultados";

		return given().contentType(ContentType.JSON).body(survey).when().post("/surveys").then().extract().path("id");
	}

	private Long createQuestion(Long surveyId, String text, String type) {
		Question question = new Question();
		question.text = text;
		question.type = type;

		return given().contentType(ContentType.JSON).body(question).queryParam("surveyId", surveyId).when()
				.post("/questions").then().extract().path("id");
	}

	private Long createAnswer(Long questionId, String value) {
		Answer answer = new Answer();
		answer.value = value;

		return given().contentType(ContentType.JSON).body(answer).queryParam("questionId", questionId).when()
				.post("/answers").then().extract().path("id");
	}

	@Test
	public void shouldReturnResultsForSurvey() {
		// 1. Crear encuesta
		Long surveyId = createSurvey();

		// 2. Crear preguntas
		Long q1 = createQuestion(surveyId, "¿Te gusta el producto?", "yes-no");
		Long q2 = createQuestion(surveyId, "Califica el servicio", "text");

		// 3. Crear respuestas
		createAnswer(q1, "Sí");
		createAnswer(q1, "No");
		createAnswer(q1, "Sí");

		createAnswer(q2, "Excelente");
		createAnswer(q2, "Bueno");

		// 4. Consultar resultados
		given().when().get("/results/" + surveyId).then().statusCode(200).body("size()", is(2))
				.body("[0].questionText", anyOf(is("¿Te gusta el producto?"), is("Califica el servicio")))
				.body("[0].answers.size()", greaterThan(0)).body("[1].answers.size()", greaterThan(0));
	}

	@Test
	public void shouldReturn404ForNonExistingSurvey() {
		given().when().get("/results/9999").then().statusCode(404);
	}
}