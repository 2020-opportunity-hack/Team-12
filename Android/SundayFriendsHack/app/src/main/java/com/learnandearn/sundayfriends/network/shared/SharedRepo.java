package com.learnandearn.sundayfriends.network.shared;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.learnandearn.sundayfriends.network.RetrofitClient;
import com.learnandearn.sundayfriends.network.model.AuthHeader;
import com.learnandearn.sundayfriends.network.model.UserTransaction;
import com.learnandearn.sundayfriends.network.model.UserTransactionDto;
import com.learnandearn.sundayfriends.utils.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SharedRepo {

    private static final String TAG = "SharedRepo";

    private MutableLiveData<List<UserTransaction>> transactionsLiveData = new MutableLiveData<>();
    private SharedService                          sharedService;

    public SharedRepo(Application application) {
        RetrofitClient retrofit = RetrofitClient.getInstance();
        sharedService = retrofit.getRetrofit().create(SharedService.class);

        SharedPrefManager sharedPref = SharedPrefManager.getInstance(application);
        AuthHeader authHeader = new AuthHeader(
                sharedPref.getIdToken(),
                sharedPref.getIdClient(),
                sharedPref.getUserEmail()
        );
        retrofit.setAuthHeader(authHeader);
    }

    public MutableLiveData<List<UserTransaction>> getTransactionsLiveData() {
        return transactionsLiveData;
    }

    public void getUserTransactions(int userId) {
        sharedService.getUserTransactions(userId).enqueue(new Callback<UserTransactionDto>() {
            @Override
            public void onResponse(@NonNull Call<UserTransactionDto> call, @NonNull Response<UserTransactionDto> response) {
                if (response.body() != null) {
                    transactionsLiveData.postValue(response.body().getTransactions());
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserTransactionDto> call, @NonNull Throwable t) {
                transactionsLiveData.postValue(null);
            }
        });
    }
}
