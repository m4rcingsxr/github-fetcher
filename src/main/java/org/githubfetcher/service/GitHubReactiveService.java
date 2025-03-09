package org.githubfetcher.service;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.githubfetcher.client.GitHubReactiveClient;
import org.githubfetcher.dto.GitHubRepository;

import java.util.List;

@ApplicationScoped
public class GitHubReactiveService {

    private final GitHubReactiveClient gitHubApiClient;

    public GitHubReactiveService(@RestClient GitHubReactiveClient gitHubApiClient) {
        this.gitHubApiClient = gitHubApiClient;
    }

    public Multi<GitHubRepository> getRepositories(String username) {
        return gitHubApiClient.getRepositories(username)
                .onItem().transformToMulti(repos ->
                                                   Multi.createFrom().iterable(repos)
                                                           .filter(repo -> !repo.fork())
                                                           .onItem().transformToUniAndMerge(this::fetchBranches)
                );
    }

    private Uni<GitHubRepository> fetchBranches(GitHubRepository repo) {
        return gitHubApiClient.getBranches(repo.owner().login(), repo.name())
                .onItem().transform(branches -> new GitHubRepository(repo.name(), repo.owner(), false, branches))
                .onFailure().recoverWithItem(new GitHubRepository(repo.name(), repo.owner(), false, List.of()));
    }

}
