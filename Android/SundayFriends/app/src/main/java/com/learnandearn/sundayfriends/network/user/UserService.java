package com.learnandearn.sundayfriends.network.user;

import com.learnandearn.sundayfriends.network.model.UserInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserService {


    //Checks if the user is signed up with backend after signing in with Google Auth

    //Gets list of user family members
    @GET("user/get_family")
    Call<List<UserInfo>> getUserFamilyMembers(@Query("familyId") int familyId);


    //Gets the user's info
    @GET("user/get_user")
    Call<UserInfo> getUser(@Query("userId") int userId);


}
