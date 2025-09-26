package com.encuestas.question.resource;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.Test;

import com.encuestas.question.entity.Question;
import com.encuestas.survey.entity.Survey;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

@QuarkusTest
public class QuestionResourceTest {

	private Long createSurvey() {
		Survey survey = new Survey();
		survey.title = "Encuesta para preguntas";
		survey.description = "Encuesta base";

		return given().contentType(ContentType.JSON).body(survey).when().post("/surveys").then().extract().path("id");
	}

	@Test
	public void shouldCreateQuestion() {
		Long surveyId = createSurvey();

		Question question = new Question();
		question.text = "¿Cómo calificas el servicio?";
		question.type = "text";

		// Vinculamos surveyId manualmente
		given().contentType(ContentType.JSON).body(question).queryParam("surveyId", surveyId) // si decides manejar
																								// surveyId por query
																								// param
				.when().post("/questions").then().statusCode(201).body("id", notNullValue())
				.body("text", is("¿Cómo calificas el servicio?"));
	}

	@Test
	public void shouldGetQuestionById() {
		Long surveyId = createSurvey();

		Question question = new Question();
		question.text = "¿Te gusta el producto?";
		question.type = "yes-no";

		Long id = given().contentType(ContentType.JSON).body(question).queryParam("surveyId", surveyId).when()
				.post("/questions").then().extract().path("id");

		given().when().get("/questions/" + id).then().statusCode(200).body("text", is("¿Te gusta el producto?"));
	}

	@Test
	public void shouldReturn404ForNonExistingQuestion() {
		given().when().get("/questions/9999").then().statusCode(404);
	}

	@Test
	public void shouldDeleteQuestion() {
		Long surveyId = createSurvey();

		Question question = new Question();
		question.text = "Pregunta a eliminar";
		question.type = "text";

		Long id = given().contentType(ContentType.JSON).body(question).queryParam("surveyId", surveyId).when()
				.post("/questions").then().extract().path("id");

		given().when().delete("/questions/" + id).then().statusCode(204);

		given().when().get("/questions/" + id).then().statusCode(404);
	}
}