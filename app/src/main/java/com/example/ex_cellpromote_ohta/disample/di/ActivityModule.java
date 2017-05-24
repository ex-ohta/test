package com.example.ex_cellpromote_ohta.disample.di;

import android.support.v7.app.AppCompatActivity;

import dagger.Module;

/**
 * Created by ex-cellpromote-ohta on 2017/05/02.
 */

@Module
public class ActivityModule {

    final int noUsedVariable = 1;
    final int noUsedVariable2 = 2;
    final AppCompatActivity activity;

    public ActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    final int noUsedVariable3 = 1;
    final int noUsedVariable4 = 2;

    final int noUsedVariabl5 = 1;
    final int noUsedVariable6 = 2;

}
