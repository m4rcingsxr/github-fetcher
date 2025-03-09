package org.githubfetcher.dto;

public record ErrorResponse(
        int status,
        String message
) {
}
