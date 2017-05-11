package com.example.ex_cellpromote_ohta.disample.api;

import com.example.ex_cellpromote_ohta.disample.database.Contributor;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by ex-cellpromote-ohta on 2017/05/02.
 */

public class GitHubClient {

    private GitHubService service;

    @Inject
    public GitHubClient(GitHubService service) {
        this.service = service;
    }

    public Observable<List<Contributor>> fetchContributors() {
        return service.fetchContributors("DroidKaigi", "conference-app-2017");
    }
}
