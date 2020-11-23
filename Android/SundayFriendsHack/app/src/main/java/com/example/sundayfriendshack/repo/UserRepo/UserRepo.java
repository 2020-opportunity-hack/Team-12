package com.example.sundayfriendshack.repo.UserRepo;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.sundayfriendshack.Constants;
import com.example.sundayfriendshack.model.FamilyMemberDto;
import com.example.sundayfriendshack.model.UserInfo;
import com.example.sundayfriendshack.model.UserTransactionDto;
import com.example.sundayfriendshack.network.RetrofitClientInstance;
import com.example.sundayfriendshack.network.RetrofitInterfaces;
import com.example.sundayfriendshack.ui.user.FragmentUserFamilyMembers;
import com.example.sundayfriendshack.ui.user.FragmentUserHome;
import com.example.sundayfriendshack.ui.user.FragmentUserTransactions;
import com.example.sundayfriendshack.ui.user.MainActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepo {

    private static final String TAG = "UserRepo";

    private MainActivity mainActivity;
    private FragmentUserTransactions fragmentUserTransactions;
    private FragmentUserFamilyMembers fragmentUserFamilyMembers;

    private FragmentUserHome fragmentUserHome;

    public UserRepo(Context context) {
        mainActivity = (MainActivity) context;
    }

    public void getUserInfo(int userId){
        fragmentUserHome = (FragmentUserHome) mainActivity.getSupportFragmentManager()
                .findFragmentByTag(Constants.FRAGMENT_USER_HOME);

        RetrofitInterfaces.GetUserInfo service = RetrofitClientInstance.getRetrofitInstance()
                .create(RetrofitInterfaces.GetUserInfo.class);
        Call<UserInfo> call = service.listRepos(String.valueOf(userId));
        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(@NonNull Call<UserInfo> call, @NonNull Response<UserInfo> response) {
                fragmentUserHome.onSuccessGetUserInfo(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<UserInfo> call, @NonNull Throwable t) {
                fragmentUserHome.onFailedGetUserInfo(t);
            }
        });
    }

    public void getFamilyMembers(int familyId){
        fragmentUserFamilyMembers = (FragmentUserFamilyMembers) mainActivity.getSupportFragmentManager()
                .findFragmentByTag(Constants.FRAGMENT_USER_FAMILY_MEMBERS);

        RetrofitInterfaces.UserGetFamily service = RetrofitClientInstance.getRetrofitInstance()
                .create(RetrofitInterfaces.UserGetFamily.class);
        Call<ArrayList<FamilyMemberDto>> call = service.listRepos(familyId);
        call.enqueue(new Callback<ArrayList<FamilyMemberDto>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<FamilyMemberDto>> call, @NonNull Response<ArrayList<FamilyMemberDto>> response) {
                Log.d(TAG, "onResponse: " + response.raw().request().url());
                fragmentUserFamilyMembers.onSuccessGetFamilyMembers(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<FamilyMemberDto>> call, @NonNull Throwable t) {
                fragmentUserFamilyMembers.onFailedGetFamilyMembers(t);
            }
        });
    }

    public void getUserTransactions(int userId){
        fragmentUserTransactions = (FragmentUserTransactions) mainActivity.getSupportFragmentManager()
                .findFragmentByTag(Constants.FRAGMENT_USER_TRANSACTIONS);

        if(fragmentUserTransactions != null){
            Log.d(TAG, "getUserTransactions: WE FOUND USER TRANSACTION");
        }

        RetrofitInterfaces.GetUserTransactions service = RetrofitClientInstance.getRetrofitInstance()
                .create(RetrofitInterfaces.GetUserTransactions.class);
        Call<UserTransactionDto> call = service.listRepos(String.valueOf(userId));
        call.enqueue(new Callback<UserTransactionDto>() {
            @Override
            public void onResponse(@NonNull Call<UserTransactionDto> call, @NonNull Response<UserTransactionDto> response) {
                Log.d(TAG, "onResponse: " + response.raw().request().url());
                fragmentUserTransactions.onSuccessGetUserTransactions(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<UserTransactionDto> call, @NonNull Throwable t) {
                fragmentUserTransactions.onFailedGetUserTransactions(t);
            }
        });
    }
}
