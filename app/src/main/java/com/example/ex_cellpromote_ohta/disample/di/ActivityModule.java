package com.example.ex_cellpromote_ohta.disample.di;

import android.support.v7.app.AppCompatActivity;

import dagger.Module;

/**
 * Created by ex-cellpromote-ohta on 2017/05/02.
 */

@Module
public class ActivityModule {

    final AppCompatActivity activity;

    public ActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }
}
