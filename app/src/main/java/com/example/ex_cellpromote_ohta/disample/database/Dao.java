package com.example.ex_cellpromote_ohta.disample.database;

import com.github.gfx.android.orma.annotation.OnConflict;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by ex-cellpromote-ohta on 2017/05/02.
 */

public class Dao {

    private OrmaDatabase orma;

    @Inject
    public Dao(OrmaDatabase orma) {
        this.orma = orma;
    }

    public void insert(List<Contributor> contributors) {
        orma.transactionSync(() -> {
            orma.prepareInsertIntoContributor(OnConflict.REPLACE)
                    .executeAll(contributors);
        });
    }

    public Single<List<Contributor>> findAll() {
        return orma.selectFromContributor().executeAsObservable().toList();
    }

}
