package com.movie.rahulrv.dependencyinjection.module;

import com.movie.rahulrv.BuildConfig;
import com.movie.rahulrv.MovieAPI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Singleton class for Retrofit Adapter
 */
@Module
public class RetrofitClient {
    private static final String API_KEY = "api_key";

    @Provides @Singleton
    public MovieAPI provideMovieAPI() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient().newBuilder();
        //okHttpClient.addInterceptor(interceptor);
        okHttpClient.addInterceptor(chain -> {
            Request request = chain.request();
            HttpUrl url = request.url().newBuilder()
                    .addQueryParameter(API_KEY, BuildConfig.API_KEY).build();
            request = request.newBuilder().url(url).build();
            return chain.proceed(request);
        });
        Retrofit retrofitClient = new Retrofit.Builder().baseUrl(BuildConfig.END_POINT).addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient.build()).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();

        return retrofitClient.create(MovieAPI.class);
    }
}
