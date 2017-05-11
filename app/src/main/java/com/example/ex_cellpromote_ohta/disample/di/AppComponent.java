package com.example.ex_cellpromote_ohta.disample.di;

import com.example.ex_cellpromote_ohta.disample.DiSampleApplication;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ex-cellpromote-ohta on 2017/05/02.
 * @Componentアノテーションに注目
 * modulesの名の通り、moduleは複数持つことができる
 * e.g. @Component(modules = {AppModule.class, xxxModule.class})
 * そこから考えるにModuleは機能毎に切るのが良いと考えられる。
 */


@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(DiSampleApplication app);
    ActivityComponent plus(ActivityModule module);

}
