package org.githubfetcher.client;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.githubfetcher.dto.GitHubBranch;
import org.githubfetcher.dto.GitHubRepository;

import java.util.List;

@RegisterRestClient(configKey = "github-api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface GitHubReactiveClient {

    @GET
    @Path("/users/{username}/repos")
    Uni<List<GitHubRepository>> getRepositories(
            @PathParam("username") String username
    );

    @GET
    @Path("/repos/{owner}/{repo}/branches")
    Uni<List<GitHubBranch>> getBranches(
            @PathParam("owner") String owner,
            @PathParam("repo") String repo
    );


}
