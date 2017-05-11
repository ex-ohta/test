package com.example.ex_cellpromote_ohta.disample.di;

import com.example.ex_cellpromote_ohta.disample.activity.MainActivity;
import com.example.ex_cellpromote_ohta.disample.di.scope.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by ex-cellpromote-ohta on 2017/05/02.
 */

@ActivityScope
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity activity);

}

