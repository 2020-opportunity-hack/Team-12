package com.example.sundayfriendshack.repo.AdminRepo;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.sundayfriendshack.Constants;
import com.example.sundayfriendshack.model.UserInfo;
import com.example.sundayfriendshack.model.UserTransactionDto;
import com.example.sundayfriendshack.network.RetrofitClientInstance;
import com.example.sundayfriendshack.network.RetrofitInterfaces;
import com.example.sundayfriendshack.ui.admin.AdminActivity;
import com.example.sundayfriendshack.ui.admin.FragmentAdminUsers;
import com.example.sundayfriendshack.ui.admin.FragmentDeposit;
import com.example.sundayfriendshack.ui.admin.FragmentWithdraw;
import com.example.sundayfriendshack.ui.admin.FragmentDeactivatedUsers;
import com.example.sundayfriendshack.ui.user.FragmentUserTransactions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminRepo {

    private static final String TAG = "AdminRepo";

    private AdminActivity adminActivity;
    private FragmentAdminUsers fragmentAdminUsers;
    private FragmentUserTransactions fragmentUserTransactions;

    private FragmentDeactivatedUsers fragmentDeactivatedUsers;

    private FragmentWithdraw fragmentWithdraw;
    private FragmentDeposit fragmentDeposit;

    public AdminRepo(Context mContext) {
        adminActivity = (AdminActivity) mContext;
    }

    public void getListOfDeactivatedUsers(){
        fragmentDeactivatedUsers = (FragmentDeactivatedUsers) adminActivity.getSupportFragmentManager()
                .findFragmentByTag(Constants.FRAGMENT_DEACTIVATED_USERS);

        RetrofitInterfaces.GetDeactivatedUsers service = RetrofitClientInstance.getRetrofitInstance()
                .create(RetrofitInterfaces.GetDeactivatedUsers.class);
        Call<ArrayList<UserInfo>> call = service.listRepos();
        call.enqueue(new Callback<ArrayList<UserInfo>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<UserInfo>> call, @NonNull Response<ArrayList<UserInfo>> response) {
                Log.d(TAG, "onResponse: " + response.raw().request().url());
                fragmentDeactivatedUsers.onSuccesGetDeactivatedUsers(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<UserInfo>> call, @NonNull Throwable t) {
                fragmentDeactivatedUsers.onFailedGetDeactivatedUsers(t);
            }
        });
    }

    public void deactivateUser(int userId){
        fragmentAdminUsers = (FragmentAdminUsers) adminActivity.getSupportFragmentManager()
                .findFragmentByTag(Constants.FRAGMENT_ADMIN_LIST_OF_USERS);

        RetrofitInterfaces.AdminDeactivateUser service = RetrofitClientInstance.getRetrofitInstance()
                .create(RetrofitInterfaces.AdminDeactivateUser.class);
        Call<Void> call = service.listRepos(String.valueOf(userId));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                Log.d(TAG, "onResponse: " + response.raw().request().url());
                fragmentAdminUsers.onSuccessDeactivateUser(response);
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                fragmentAdminUsers.onFailedDeactivateUser(t);
            }
        });
    }

    public void depositTicket(int userId, int amountToDeposit, int actionType){
        fragmentDeposit = (FragmentDeposit) adminActivity.getSupportFragmentManager()
                .findFragmentByTag(Constants.FRAGMENT_ADMIN_USER_DEPOSIT);

        RetrofitInterfaces.AdminDepositTicket service = RetrofitClientInstance.getRetrofitInstance()
                .create(RetrofitInterfaces.AdminDepositTicket.class);
        Call<Void> call = service.listRepos(
                String.valueOf(userId),
                String.valueOf(amountToDeposit),
                String.valueOf(actionType)
        );
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                Log.d(TAG, "onResponse: " + response.raw().request().url());
                fragmentDeposit.onSuccessTicketDeposit();
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                fragmentDeposit.onFailedTicketDeposit(t);
            }
        });
    }

    public void withdrawTicket(int userId, int amountToWithdraw, int actionType){
        fragmentWithdraw = (FragmentWithdraw) adminActivity.getSupportFragmentManager()
                .findFragmentByTag(Constants.FRAGMENT_ADMIN_USER_WITHDRAW);

        RetrofitInterfaces.AdminWithdrawTicket service = RetrofitClientInstance.getRetrofitInstance()
                .create(RetrofitInterfaces.AdminWithdrawTicket.class);
        Call<Void> call = service.listRepos(
                String.valueOf(userId),
                String.valueOf(amountToWithdraw),
                String.valueOf(actionType)
        );
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                Log.d(TAG, "onResponse: " + response.raw().request().url());
                fragmentWithdraw.onSuccessTicketWithdraw();
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                fragmentWithdraw.onFailedTicketWithdraw(t);
            }
        });
    }



    public void getListOfUsers(){
        fragmentAdminUsers = (FragmentAdminUsers) adminActivity.getSupportFragmentManager()
                .findFragmentByTag(Constants.FRAGMENT_ADMIN_LIST_OF_USERS);

        RetrofitInterfaces.AdminFetchUsers service = RetrofitClientInstance.getRetrofitInstance()
                .create(RetrofitInterfaces.AdminFetchUsers.class);
        Call<ArrayList<UserInfo>> call = service.listRepos();
        call.enqueue(new Callback<ArrayList<UserInfo>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<UserInfo>> call, @NonNull Response<ArrayList<UserInfo>> response) {
                Log.d(TAG, "onResponse: " + response.raw().request().url());
                fragmentAdminUsers.onSuccessGetListUsers(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<UserInfo>> call, @NonNull Throwable t) {
                fragmentAdminUsers.onFailedGetListUsers(t);
            }
        });
    }

    public void getUserTransactions(int userId){
        fragmentUserTransactions = (FragmentUserTransactions) adminActivity.getSupportFragmentManager()
                .findFragmentByTag(Constants.FRAGMENT_USER_TRANSACTIONS);

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
