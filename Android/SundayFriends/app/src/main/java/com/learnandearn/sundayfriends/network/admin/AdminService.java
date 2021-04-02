package com.learnandearn.sundayfriends.network.admin;

import com.learnandearn.sundayfriends.network.model.UserInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface AdminService {


    @GET("admin/deactivatedUsers")
    Call<List<UserInfo>> getDeactivatedUsers();

    @GET("admin/fetchUsers")
    Call<List<UserInfo>> getActivatedUsers();

    @GET("admin/fetchUsers")
    Call<List<UserInfo>> getMembersToLink();


    //Searches for a specific user
    @GET("admin/fetchUsers")
    Call<List<UserInfo>> searchUsers(
            @Query("searchQuery") String gmail
    );

    @GET("admin/fetchUsers")
    Call<List<UserInfo>> getUsersPaginitation(
            @Query("offset") int offset,
            @Query("limit") int limit
    );

    //Same endpoint as activate
    //true boolean type on deactivate user means deactive that user
    @PUT("admin/deactivate_user")
    Call<Void> deactivateUser(
            @Query("userId") int userId,
            @Query("deactivate") boolean type
    );

    //Same endpoint as deactivate
    //false boolean type on deactivate user means activate that user
    @PUT("admin/deactivate_user")
    Call<Void> activateUser(
            @Query("userId") int userId,
            @Query("deactivate") boolean type
    );


    @PUT("admin/link_family")
    Call<Void> registerUserToFamily(
            @Query("userId") int userId,
            @Query("familyId") int familyId
    );


    /**
     * Action Type
     * 0 = withdraw
     * 1 = deposit
     */
    @POST("admin/transact")
    Call<Void> depositTicket(
            @Query("userId") int userId,
            @Query("amount") int amount,
            @Query("type") int actionType
    );


    /**
     * Action Type
     * 0 = withdraw
     * 1 = deposit
     */
    @POST("admin/transact")
    Call<Void> withdrawTicket(
            @Query("userId") int userId,
            @Query("amount") int amount,
            @Query("type") int actionType);


}
