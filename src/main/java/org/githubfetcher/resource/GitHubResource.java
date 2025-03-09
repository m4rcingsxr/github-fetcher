package org.githubfetcher.resource;

import io.smallrye.mutiny.Multi;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.githubfetcher.dto.GitHubRepository;
import org.githubfetcher.service.GitHubReactiveService;

@Path("/github")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GitHubResource {

    private final GitHubReactiveService gitHubService;

    public GitHubResource(GitHubReactiveService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GET
    @Path("/{username}")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Multi<GitHubRepository> getUserRepositories(@PathParam("username") String username) {
        return gitHubService.getRepositories(username);
    }
}