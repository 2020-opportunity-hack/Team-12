package com.learnandearn.sundayfriends.network;

import com.learnandearn.sundayfriends.Constants;
import com.learnandearn.sundayfriends.network.model.AuthHeader;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String TAG = "RetrofitClient";

    private static RetrofitClient      retrofitClient;
    private static Retrofit            retrofit;
    private static RequestInterception requestInterception;

    public static RetrofitClient getInstance() {
        if (retrofitClient == null) {
            retrofitClient = new RetrofitClient();
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.SUNDAY_FRIENDS_ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(buildClient())
                    .build();
        }
        return retrofitClient;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public void setAuthHeader(AuthHeader authHeader) {
        requestInterception.setAuthHeader(authHeader);
    }

    private static OkHttpClient buildClient() {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);

        requestInterception = new RequestInterception();

        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(requestInterception)
                .build();
    }


}

