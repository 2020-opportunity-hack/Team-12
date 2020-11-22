package com.example.sundayfriendshack.repo.UserRepo;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.sundayfriendshack.model.FamilyMemberDto;
import com.example.sundayfriendshack.network.RetrofitClientInstance;
import com.example.sundayfriendshack.network.RetrofitInterfaces;
import com.example.sundayfriendshack.ui.user.MainActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepo {

    private static final String TAG = "UserRepo";

    private MainActivity mainActivity;

    public UserRepo(Context context) {
        mainActivity = (MainActivity) context;
    }

    public void getFamilyMembers(int familyId){
        RetrofitInterfaces.UserGetFamily service = RetrofitClientInstance.getRetrofitInstance()
                .create(RetrofitInterfaces.UserGetFamily.class);
        Call<ArrayList<FamilyMemberDto>> call = service.listRepos(familyId);
        call.enqueue(new Callback<ArrayList<FamilyMemberDto>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<FamilyMemberDto>> call, @NonNull Response<ArrayList<FamilyMemberDto>> response) {
                Log.d(TAG, "onResponse: " + response.raw().request().url());
                mainActivity.onSuccessGetFamilyMembers(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<FamilyMemberDto>> call, @NonNull Throwable t) {
                mainActivity.onFailedGetFamilyMembers(t);
            }
        });
    }
}
