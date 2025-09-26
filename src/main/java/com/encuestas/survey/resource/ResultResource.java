package com.encuestas.survey.resource;

import com.encuestas.survey.service.ResultService;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/results")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ResultResource {

	@Inject
	ResultService resultService;

	@GET
	@Path("/{surveyId}")
	public Uni<Response> getResults(@PathParam("surveyId") Long surveyId) {
		return resultService.getResultsBySurvey(surveyId).onItem().ifNotNull()
				.transform(results -> Response.ok(results).build()).onItem().ifNull()
				.continueWith(Response.status(Response.Status.NOT_FOUND)::build);
	}
}