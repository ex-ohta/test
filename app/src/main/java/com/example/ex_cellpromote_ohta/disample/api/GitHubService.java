package com.example.ex_cellpromote_ohta.disample.api;

import com.example.ex_cellpromote_ohta.disample.database.Contributor;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by ex-cellpromote-ohta on 2017/05/02.
 */

public interface GitHubService {

    @GET("repos/{owner}/{repo}/contributors")
    Observable<List<Contributor>> fetchContributors(
            @Path("owner") String owner,
            @Path("repo") String repository
    );

}
