package com.encuestas.common.util;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.core.Response;

public class ResponseUtil {
	public static <T> Uni<Response> okOrNotFound(Uni<T> entity) {
		return entity.onItem().ifNotNull().transform(e -> Response.ok(e).build()).onItem().ifNull()
				.continueWith(Response.status(Response.Status.NOT_FOUND)::build);
	}

	public static <T> Uni<Response> created(Uni<T> entity) {
		return entity.onItem().transform(e -> Response.status(Response.Status.CREATED).entity(e).build());
	}
}