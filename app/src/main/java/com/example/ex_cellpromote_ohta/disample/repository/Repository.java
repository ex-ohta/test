package com.example.ex_cellpromote_ohta.disample.repository;

import com.example.ex_cellpromote_ohta.disample.api.GitHubClient;
import com.example.ex_cellpromote_ohta.disample.database.Contributor;
import com.example.ex_cellpromote_ohta.disample.database.Dao;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by ex-cellpromote-ohta on 2017/05/02.
 */

public class Repository {

    private GitHubClient client;
    private Dao dao;

    @Inject
    public Repository(GitHubClient client, Dao dao) {
        this.client = client;
        this.dao = dao;
    }

    public Observable<List<Contributor>> getContributors() {
        return client.fetchContributors()
                // onNextが呼ばれたら(通信に成功したらDBにインサートする)
                .doOnNext(contributors -> {
                    dao.insert(contributors);
                })
                // onErrorになったら(通信に失敗したらDBから値を持ってくる)
                .onErrorReturn(throwable -> {
                    throwable.printStackTrace();
                    return dao.findAll().blockingGet();
                });
    }
}
