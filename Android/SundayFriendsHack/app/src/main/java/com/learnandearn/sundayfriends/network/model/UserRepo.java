package com.learnandearn.sundayfriends.network.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.learnandearn.sundayfriends.network.RetrofitClient;
import com.learnandearn.sundayfriends.network.user.UserService;
import com.learnandearn.sundayfriends.utils.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepo {

    private static final String TAG = "UserRepo";

    private MutableLiveData<List<UserInfo>> mFamilyMembersLiveData = new MutableLiveData<>();
    private UserService                     mUserService;

    public UserRepo(Application application) {
        RetrofitClient client = RetrofitClient.getInstance();
        mUserService = client.getRetrofit().create(UserService.class);

        SharedPrefManager sharedPref = SharedPrefManager.getInstance(application);
        AuthHeader authHeader = new AuthHeader(
                sharedPref.getIdToken(),
                sharedPref.getIdClient(),
                sharedPref.getUserEmail()
        );
        client.setAuthHeader(authHeader);
    }

    public MutableLiveData<List<UserInfo>> getFamilyMembersLiveData() {
        return mFamilyMembersLiveData;
    }

    public void getFamilyMembers(int familyId) {
        mUserService.getUserFamilyMembers(familyId).enqueue(new Callback<List<UserInfo>>() {
            @Override
            public void onResponse(@NonNull Call<List<UserInfo>> call, @NonNull Response<List<UserInfo>> response) {
                if (response.body() != null) {
                    mFamilyMembersLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<UserInfo>> call, @NonNull Throwable t) {
                mFamilyMembersLiveData.postValue(null);
            }
        });
    }
}
