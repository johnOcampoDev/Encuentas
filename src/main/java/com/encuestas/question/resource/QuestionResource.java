package com.encuestas.question.resource;

import com.encuestas.question.entity.Question;
import com.encuestas.question.service.QuestionService;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/questions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class QuestionResource {

	@Inject
	QuestionService questionService;

	@GET
	public Multi<Question> listAll() {
		return questionService.listAll();
	}

	@GET
	@Path("/{id}")
	public Uni<Response> getById(@PathParam("id") Long id) {
		return questionService.findById(id).onItem().ifNotNull().transform(q -> Response.ok(q).build()).onItem()
				.ifNull().continueWith(Response.status(Response.Status.NOT_FOUND)::build);
	}

	@POST
	public Uni<Response> create(Question question, @QueryParam("surveyId") Long surveyId) {
		return questionService.create(question, surveyId).onItem()
				.transform(q -> Response.status(Response.Status.CREATED).entity(q).build());
	}

	@PUT
	@Path("/{id}")
	public Uni<Response> update(@PathParam("id") Long id, Question question) {
		return questionService.update(id, question).onItem().ifNotNull().transform(q -> Response.ok(q).build()).onItem()
				.ifNull().continueWith(Response.status(Response.Status.NOT_FOUND)::build);
	}

	@DELETE
	@Path("/{id}")
	public Uni<Response> delete(@PathParam("id") Long id) {
		return questionService.delete(id).onItem().transform(
				deleted -> deleted ? Response.noContent().build() : Response.status(Response.Status.NOT_FOUND).build());
	}
}