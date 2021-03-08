package com.learnandearn.sundayfriends.network.shared;

import com.learnandearn.sundayfriends.network.model.UserTransactionDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SharedService {
    @GET("user/transactions")
    Call<UserTransactionDto> getUserTransactions(@Query("userId") int userId);
}
