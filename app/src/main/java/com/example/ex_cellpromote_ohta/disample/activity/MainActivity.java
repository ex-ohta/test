package com.example.ex_cellpromote_ohta.disample.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.ex_cellpromote_ohta.disample.DiSampleApplication;
import com.example.ex_cellpromote_ohta.disample.R;
import com.example.ex_cellpromote_ohta.disample.di.ActivityComponent;
import com.example.ex_cellpromote_ohta.disample.di.ActivityModule;
import com.example.ex_cellpromote_ohta.disample.repository.Repository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * MainActivity
 */
public class MainActivity extends AppCompatActivity {


    @Inject
    Repository repository;

    /**
     * GitHubからDroidkaigi2017アプリのコントリビュータのデータを取ってくる処理が書かれている。
     * APIからのデータ取得に失敗したらDBからデータを取ってくるような処理になっている。
     * DI(DependencyInjection)を用いた例と普通に使った例をこちらに書いている。
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         *
         * 普通に使うとこんな感じ。
         * まずhttpクライアントを作って、retrofitの準備をして、orma(DBライブラリ)の準備をして
         * リポジトリクラスを作ってリポジトリクラスからデータを取得している。
         *
         * このままだとActivityはどんどん肥大化していくし何より。クラスの中でnewを多数使っている。
         * つまり依存するオブジェクトが多いので、モックオブジェクトへの差し替えなどが非常に面倒。
         */
//        // http client の作成
//        final String CACHE_FILE_NAME = "okhttp.cache";
        final long MAX_CACHE_SIZE = 4 * 1024 * 1024; // 4MB
//        File cacheDir = new File(getApplicationContext().getCacheDir(), CACHE_FILE_NAME);
//        Cache cache = new Cache(cacheDir, MAX_CACHE_SIZE);
//        OkHttpClient httpClient = new OkHttpClient.Builder()
//                .cache(cache)
//                .addInterceptor(chain -> {
//                    Request.Builder builder1 = chain.request().newBuilder();
//                    return chain.proceed(builder1.build());
//                }).build();
//
//        GitHubService gitHubService = new Retrofit.Builder()
//                .baseUrl("https://api.github.com/")
//                .client(httpClient)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//                .create(GitHubService.class);
//
//        GitHubClient client = new GitHubClient(gitHubService);
//
//        // daoの作成
//        OrmaDatabase database = OrmaDatabase
//                .builder(getApplicationContext())
//                .writeOnMainThread(AccessThreadConstraint.FATAL)
//                .readOnMainThread(AccessThreadConstraint.FATAL)
//                .build();
//        Dao dao = new Dao(database);
//
//        // repositoryの作成
//        Repository repository = new Repository(client, dao);

        /*
         * DaggerなどのDIライブラリを用いて依存性の注入を行う場合。
         * 外部からインスタンスを注入するのでActivityが依存するオブジェクトを極力少なくすることができる。
         *
         * 中〜大規模なアプリケーションの開発で真価を発揮する。
         * 現在は具象クラスのインスタンスを注入しているが、AppModuleで注入しているインスタンスを全てinterfaceに置き換えた場合
         * 開発者は各々の機能を持つModuleの開発に注力することができる。
         * データベースのEngineの切り替えなども比較的容易に行うことができるだろう。
         * チーム間ではinterfaceの取り決めさえすれば良い。一つ一つのModuleを暫定的にモッククラスにし、Moduleの完成を待つ
         * と言ったような開発スタイルを取ることができる。
         *
         * 弱点はコードの記述量が増える事、DIライブラリ自体の学習コストが高めな事
         * AnnotationProcessingによりファクトリメソッドやクラスを自動生成するため、65535問題に遭遇する可能性が高くなる事
         */
        ActivityComponent activityComponent = ((DiSampleApplication) getApplication())
                .getAppComponent()
                .plus(new ActivityModule(this));
        activityComponent.inject(this);

        repository.getContributors()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    findViewById(R.id.progress).setVisibility(View.VISIBLE);
                })
                .subscribe(contributors -> {
                    // 面倒なのでココでRecyclerViewの初期化とAdapterの初期化をしまっている
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.contributorList);
                    recyclerView.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false));

                    ContributorsAdapter adapter = new ContributorsAdapter();
                    recyclerView.setAdapter(adapter);

                    adapter.addAll(contributors);

                }, throwable -> {
                    throwable.printStackTrace();
                    findViewById(R.id.progress).setVisibility(View.GONE);
                }, () -> {
                    findViewById(R.id.progress).setVisibility(View.GONE);
                });
    }

}
