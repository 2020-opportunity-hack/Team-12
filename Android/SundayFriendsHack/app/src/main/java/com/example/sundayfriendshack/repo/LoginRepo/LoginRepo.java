package com.example.sundayfriendshack.repo.LoginRepo;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.sundayfriendshack.Constants;
import com.example.sundayfriendshack.model.UserInfo;
import com.example.sundayfriendshack.network.RetrofitClientInstance;
import com.example.sundayfriendshack.network.RetrofitInterfaces;
import com.example.sundayfriendshack.ui.login.FamilyIdFragment;
import com.example.sundayfriendshack.ui.login.LoginActivity;

import java.util.ArrayList;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepo {

    private Context context;

    private FamilyIdFragment familyIdFragment;

    public LoginRepo(Context context) {
        this.context = context;

        LoginActivity loginActivity = (LoginActivity) context;
        familyIdFragment = (FamilyIdFragment) loginActivity.getSupportFragmentManager()
                .findFragmentByTag(Constants.FAMILY_ID_FRAGMENT);
    }

    public void isUserRegistered(String name, String email, String imageUrl){

        RetrofitInterfaces.IsUserRegistered service = RetrofitClientInstance.getRetrofitInstance()
                .create(RetrofitInterfaces.IsUserRegistered.class);
        Call<UserInfo> call = service.listRepos(name, email, imageUrl);
        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(@NonNull Call<UserInfo> call, @NonNull Response<UserInfo> response) {
                familyIdFragment.onSuccessGetUser(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<UserInfo> call, @NonNull Throwable t) {
                familyIdFragment.onFailedGetUser(t);
            }
        });
    }

    public void registerFamilyId(int userId, int familyId){
        RetrofitInterfaces.RegisterFamilyId service = RetrofitClientInstance.getRetrofitInstance()
                .create(RetrofitInterfaces.RegisterFamilyId.class);
        Call<Void> call = service.listRepos(String.valueOf(userId), String.valueOf(familyId));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                familyIdFragment.onSuccessRegisterFamilyId(response);
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                familyIdFragment.onFailedRegisterFamilyId(t);
            }
        });
    }
}
