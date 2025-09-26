package com.encuestas.answer.resource;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.Test;

import com.encuestas.answer.entity.Answer;
import com.encuestas.question.entity.Question;
import com.encuestas.survey.entity.Survey;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

@QuarkusTest
public class AnswerResourceTest {

	private Long createSurvey() {
		Survey survey = new Survey();
		survey.title = "Encuesta para respuestas";
		survey.description = "Encuesta base";

		return given().contentType(ContentType.JSON).body(survey).when().post("/surveys").then().extract().path("id");
	}

	private Long createQuestion(Long surveyId) {
		Question question = new Question();
		question.text = "¿Qué opinas del servicio?";
		question.type = "text";

		return given().contentType(ContentType.JSON).body(question).queryParam("surveyId", surveyId) // si manejas
																										// surveyId por
																										// query param
				.when().post("/questions").then().extract().path("id");
	}

	@Test
	public void shouldCreateAnswer() {
		Long surveyId = createSurvey();
		Long questionId = createQuestion(surveyId);

		Answer answer = new Answer();
		answer.value = "Muy bueno";

		given().contentType(ContentType.JSON).body(answer).queryParam("questionId", questionId).when().post("/answers")
				.then().statusCode(201).body("id", notNullValue()).body("value", is("Muy bueno"));
	}

	@Test
	public void shouldGetAnswerById() {
		Long surveyId = createSurvey();
		Long questionId = createQuestion(surveyId);

		Answer answer = new Answer();
		answer.value = "Excelente";

		Long id = given().contentType(ContentType.JSON).body(answer).queryParam("questionId", questionId).when()
				.post("/answers").then().extract().path("id");

		given().when().get("/answers/" + id).then().statusCode(200).body("value", is("Excelente"));
	}

	@Test
	public void shouldReturn404ForNonExistingAnswer() {
		given().when().get("/answers/9999").then().statusCode(404);
	}

	@Test
	public void shouldDeleteAnswer() {
		Long surveyId = createSurvey();
		Long questionId = createQuestion(surveyId);

		Answer answer = new Answer();
		answer.value = "Respuesta a eliminar";

		Long id = given().contentType(ContentType.JSON).body(answer).queryParam("questionId", questionId).when()
				.post("/answers").then().extract().path("id");

		given().when().delete("/answers/" + id).then().statusCode(204);

		given().when().get("/answers/" + id).then().statusCode(404);
	}
}