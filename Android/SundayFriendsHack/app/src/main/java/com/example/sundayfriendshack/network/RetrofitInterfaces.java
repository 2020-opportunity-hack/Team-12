package com.example.sundayfriendshack.network;


import com.example.sundayfriendshack.model.FamilyMemberDto;
import com.example.sundayfriendshack.model.UserInfo;
import com.example.sundayfriendshack.model.UserTransactionDto;

import java.util.ArrayList;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class RetrofitInterfaces {


    public interface AdminFetchUsers {
        @GET("admin/fetchUsers")
        Call<ArrayList<UserInfo>> listRepos();
    }

    public interface LinkFamily {
        @POST
        Call<String> listRepos(@Query("userId") String userId, @Query("familyId") String familyId);
    }

    public interface AdminTransact {
        @POST
        Call<String> listRepos(@Body RequestBody info);
    }

    public interface UserGetFamily {
        @GET("user/get_family")
        Call<ArrayList<FamilyMemberDto>> listRepos (@Query("familyId") int familyId);
    }

    public interface RegisterUser{
        @POST("user/onboard")
        Call<ArrayList<String>> listRepos(@Body RequestBody userInfo);
    }

    public interface GetUserTransactions {
        @GET("user/transactions")
        Call<UserTransactionDto> listRepos(@Query("userId") String userId);
    }

}
