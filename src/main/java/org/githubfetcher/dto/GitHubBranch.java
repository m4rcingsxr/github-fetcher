package org.githubfetcher.dto;

public record GitHubBranch(
        String name,
        GitHubCommit commit
) {
}
