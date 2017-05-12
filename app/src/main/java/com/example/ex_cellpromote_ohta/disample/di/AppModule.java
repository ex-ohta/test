package com.example.ex_cellpromote_ohta.disample.di;

import android.app.Application;
import android.content.Context;

import com.example.ex_cellpromote_ohta.disample.api.GitHubService;
import com.example.ex_cellpromote_ohta.disample.database.OrmaDatabase;
import com.github.gfx.android.orma.AccessThreadConstraint;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ex-cellpromote-ohta on 2017/05/02.
 * インスタンスを生成する部分
 * こちらで生成されたインスタンスはComponentInterfaceを手がかりに、依存性の充足を行う
 */

@Module
public class AppModule {

    private Context context;

    public AppModule(Application context) {
        this.context = context;
    }

    @Provides
    public Context provideContext() {
        return context;
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        // 本当だったらHttpClientModuleに切り出してProvideさせると良い
        final String CACHEFILENAME = "okhttp.cache";
        final long maxCacheSize = 4 * 1024 * 1024; // 4MB

        File cacheDir = new File(context.getCacheDir(), CACHEFILENAME);
        Cache cache = new Cache(cacheDir, maxCacheSize);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(chain -> {
                    Request.Builder builder1 = chain.request().newBuilder();
                    return chain.proceed(builder1.build());
                });
        return builder.build();
    }

    @Provides
    public GitHubService provideGitHubService(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GitHubService.class);
    }

    @Provides
    public OrmaDatabase provideOrma(Context context) {
        return OrmaDatabase
                .builder(context)
                .writeOnMainThread(AccessThreadConstraint.FATAL)
                .readOnMainThread(AccessThreadConstraint.FATAL)
                .build();
    }

    /*
     * ココのコメントアウトを外しても動くが、該当するクラスを見てほしい。
     * GitHubClientとRepositoryクラスだ
     * コンストラクタに@Injectアノテーションがついている。(依存性の要求)
     * そして、そのコンストラクタはこのクラスに存在するprovideXXXメソッドによって提供されるインスタンスを必要としている。
     * つまり、必要なインスタンスが最低限Moduleで定義されていれば、provideXXXメソッドを書く必要は無い。
     * Daggerライブラリは勝手にインスタンスを注入してくれる。
     */
//    @Provides
//    public GitHubClient provideGitHubClient(GitHubService service) {
//        return new GitHubClient(service);
//    }

//    @Provides
//    public Repository provideRepository(GitHubClient client, Dao dao) {
//        return new Repository(client, dao);
//    }

}
