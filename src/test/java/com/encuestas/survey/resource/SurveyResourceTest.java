package com.encuestas.survey.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import com.encuestas.survey.entity.Survey;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class SurveyResourceTest {

	@Test
	public void shouldListSurveysInitiallyEmpty() {
		given().when().get("/surveys").then().statusCode(200).body("size()", is(0));
	}

	@Test
	public void shouldCreateSurvey() {
		Survey survey = new Survey();
		survey.title = "Encuesta de satisfacción";
		survey.description = "Queremos conocer tu opinión";

		given().contentType(ContentType.JSON).body(survey).when().post("/surveys").then().statusCode(201)
				.body("id", notNullValue()).body("title", is("Encuesta de satisfacción"));
	}

	@Test
	public void shouldGetSurveyById() {
		Survey survey = new Survey();
		survey.title = "Encuesta de prueba";
		survey.description = "Testing survey";

		Integer idInt = given().contentType(ContentType.JSON).body(survey).when().post("/surveys").then().extract()
				.path("id");

		Long id = idInt.longValue();

		given().when().get("/surveys/" + id).then().statusCode(200).body("title", is("Encuesta de prueba"));
	}

	@Test
	public void shouldReturn404ForNonExistingSurvey() {
		given().when().get("/surveys/9999").then().statusCode(404);
	}

	@Test
	public void shouldDeleteSurvey() {
		Survey survey = new Survey();
		survey.title = "Encuesta a eliminar";
		survey.description = "Test delete";

		Integer idInt = given().contentType(ContentType.JSON).body(survey).when().post("/surveys").then().extract()
				.path("id");

		Long id = idInt.longValue();

		given().when().delete("/surveys/" + id).then().statusCode(204);

		given().when().get("/surveys/" + id).then().statusCode(404);
	}
}