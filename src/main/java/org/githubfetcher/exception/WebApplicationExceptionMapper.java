package org.githubfetcher.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.githubfetcher.dto.ErrorResponse;

@Provider
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {

    @Override
    public Response toResponse(WebApplicationException exception) {
        int status = exception.getResponse().getStatus();
        String message = switch (status) {
            case 404 -> "GitHub user not found";
            case 403 -> "Rate limit exceeded or unauthorized access";
            case 500 -> "Internal Server Error. Try again later.";
            default -> "An unexpected error occurred.";
        };

        return Response.status(status)
                .entity(new ErrorResponse(status, message))
                .build();
    }
}