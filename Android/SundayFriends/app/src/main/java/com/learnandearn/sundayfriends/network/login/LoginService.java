package com.learnandearn.sundayfriends.network.login;

import com.learnandearn.sundayfriends.network.model.OnBoard;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoginService {

    //After logging in send to endpoint to see if user exists
    @POST("user/onboard")
    Call<OnBoard> IsUserRegistered(
            @Query("name") String name,
            @Query("email") String email,
            @Query("imageUrl") String imageUrl
    );
}
