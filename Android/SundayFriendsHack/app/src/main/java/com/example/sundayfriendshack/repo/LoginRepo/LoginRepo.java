package com.example.sundayfriendshack.repo.LoginRepo;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.sundayfriendshack.network.RetrofitClientInstance;
import com.example.sundayfriendshack.network.RetrofitInterfaces;
import com.example.sundayfriendshack.ui.login.LoginActivity;

import java.util.ArrayList;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepo {

    private Context context;
    private LoginActivity loginActivity;

    public LoginRepo(Context context) {
        this.context = context;
    }

    public void getUserData(RequestBody userInfo){
        RetrofitInterfaces.RegisterUser service = RetrofitClientInstance.getRetrofitInstance()
                .create(RetrofitInterfaces.RegisterUser.class);
        Call<ArrayList<String>> call = service.listRepos(userInfo);
        call.enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<String>> call, @NonNull Response<ArrayList<String>> response) {
                loginActivity.onSuccessRegisterUser();
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<String>> call, @NonNull Throwable t) {
                loginActivity.onFailedRegisterUser(t);
            }
        });
    }
}
