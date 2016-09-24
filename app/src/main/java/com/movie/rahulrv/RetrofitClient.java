package com.movie.rahulrv;

/**
 * Singleton class for Retrofit Adapter
 */

public class RetrofitClient {

    private static RetrofitClient retrofitClient;

    private RetrofitClient() {

    }

    public static RetrofitClient getInstance() {

        return retrofitClient;
    }
}
