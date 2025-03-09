package org.githubfetcher.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record GitHubRepository(
        String name,
        GitHubOwner owner,
        @JsonIgnore boolean fork,
        List<GitHubBranch> branches
) {
    public GitHubRepository(
            @JsonProperty("name") String name,
            @JsonProperty("owner") GitHubOwner owner,
            @JsonProperty("fork") boolean fork,
            @JsonProperty("branches") List<GitHubBranch> branches
    ) {
        this.name = name;
        this.owner = owner;
        this.fork = fork;
        this.branches = branches;
    }
}
