package com.learnandearn.sundayfriends.network;

import android.util.Log;

import com.learnandearn.sundayfriends.Constants;
import com.learnandearn.sundayfriends.network.model.AuthHeader;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RequestInterception implements Interceptor {

    private static final String TAG = "RequestInterception";

    private static AuthHeader authHeader;

    public RequestInterception() {
    }

    public void setAuthHeader(AuthHeader authHeader) {
        RequestInterception.authHeader = authHeader;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder requestBuilder = chain.request().newBuilder();

        requestBuilder.addHeader(Constants.RETROFIT_HEADER_KEY_ID_TOKEN, authHeader.getIdToken());
        requestBuilder.addHeader(Constants.RETROFIT_HEADER_KEY_ID_CLIENT, authHeader.getIdClient());
        requestBuilder.addHeader(Constants.RETROFIT_HEADER_KEY_USER_EMAIL, authHeader.getIdEmail());

        Log.d(TAG, "intercept: \n" + requestBuilder.build().headers().toString());

        return chain.proceed(requestBuilder.build());
    }
}
