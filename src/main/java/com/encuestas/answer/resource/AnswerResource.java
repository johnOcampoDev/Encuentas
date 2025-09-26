package com.encuestas.answer.resource;

import com.encuestas.answer.entity.Answer;
import com.encuestas.answer.service.AnswerService;
import com.encuestas.common.util.ResponseUtil;

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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/answers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AnswerResource {

	@Inject
	AnswerService answerService;

	@GET
	public Multi<Answer> listAll() {
		return answerService.listAll();
	}

	@GET
	@Path("/{id}")
	public Uni<Response> getById(@PathParam("id") Long id) {
		return ResponseUtil.okOrNotFound(answerService.findById(id));
	}

	@POST
	public Uni<Response> create(Answer answer) {
		return ResponseUtil.created(answerService.create(answer));
	}

	@PUT
	@Path("/{id}")
	public Uni<Response> update(@PathParam("id") Long id, Answer answer) {
		return ResponseUtil.okOrNotFound(answerService.update(id, answer));
	}

	@DELETE
	@Path("/{id}")
	public Uni<Response> delete(@PathParam("id") Long id) {
		return answerService.delete(id).onItem().transform(
				deleted -> deleted ? Response.noContent().build() : Response.status(Response.Status.NOT_FOUND).build());
	}
}