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
import retrofit2.http.PUT;
import retrofit2.http.Query;

public class RetrofitInterfaces {

    public interface GetDeactivatedUsers{
        @GET("admin/deactivatedUsers")
        Call<ArrayList<UserInfo>> listRepos();
    }

    public interface GetUserInfo{
        @GET("user/get_user")
        Call<UserInfo> listRepos(@Query("userId") String userId);
    }

    //Same endpoint as deactivate
    public interface AdminActivateUser{
        @PUT("admin/deactivate_user")
        Call<Void> listRepos(@Query("userId") String userId);
    }

    public interface AdminDeactivateUser{
        @PUT("admin/deactivate_user")
        Call<Void> listRepos(@Query("userId") String userId);
    }

    public interface AdminFetchUsers {
        @GET("admin/fetchUsers")
        Call<ArrayList<UserInfo>> listRepos();
    }

    public interface RegisterFamilyId {
        @PUT("admin/link_family")
        Call<Void> listRepos(@Query("userId") String userId, @Query("familyId") String familyId);
    }

    /**
     * Action Type
     * 0 = withdraw
     * 1 = deposit
     */
    public interface AdminDepositTicket {
        @POST("admin/transact")
        Call<Void> listRepos(
                @Query("userId") String userId,
                @Query("amount") String amount,
                @Query("type") String actionType
        );
    }

    public interface AdminWithdrawTicket {
        @POST("admin/transact")
        Call<Void> listRepos(
                @Query("userId") String userId,
                @Query("amount") String amount,
                @Query("type") String actionType);
    }

    public interface UserGetFamily {
        @GET("user/get_family")
        Call<ArrayList<FamilyMemberDto>> listRepos (@Query("familyId") int familyId);
    }

    public interface IsUserRegistered{
        @POST("user/onboard")
        Call<UserInfo> listRepos(
                @Query("name") String name,
                @Query("email") String email,
                @Query("imageUrl") String imageUrl
        );
    }

    public interface GetUserTransactions {
        @GET("user/transactions")
        Call<UserTransactionDto> listRepos(@Query("userId") String userId);
    }

}
