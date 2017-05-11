package com.example.ex_cellpromote_ohta.disample;

import android.app.Application;

import com.example.ex_cellpromote_ohta.disample.di.AppComponent;
import com.example.ex_cellpromote_ohta.disample.di.AppModule;
import com.example.ex_cellpromote_ohta.disample.di.DaggerAppComponent;

/**
 * Created by ex-cellpromote-ohta on 2017/05/02.
 */

public class DiSampleApplication extends Application {

    private AppComponent appComponent;

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // ココでApplication単位で使用するオブジェクトの依存性を注入する
        appComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .build();
        appComponent.inject(this);
    }
}
