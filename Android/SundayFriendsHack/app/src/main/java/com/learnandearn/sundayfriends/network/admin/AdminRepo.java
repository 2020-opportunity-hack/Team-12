package com.learnandearn.sundayfriends.network.admin;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.learnandearn.sundayfriends.network.RetrofitClient;
import com.learnandearn.sundayfriends.network.model.AuthHeader;
import com.learnandearn.sundayfriends.network.model.ResponseCode;
import com.learnandearn.sundayfriends.network.model.UserInfo;
import com.learnandearn.sundayfriends.utils.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminRepo {

    private static final String TAG = "AdminRepo";

    //List Members Fragment
    private AdminService                    adminService;
    private MutableLiveData<List<UserInfo>> firstPageLiveData  = new MutableLiveData<>();
    private MutableLiveData<List<UserInfo>> nextPageLiveData   = new MutableLiveData<>();
    private MutableLiveData<List<UserInfo>> searchUserLiveData = new MutableLiveData<>();

    //Withdraw/Deposit tickets
    private MutableLiveData<ResponseCode> withdrawLiveData = new MutableLiveData<>();
    private MutableLiveData<ResponseCode> depositLiveData  = new MutableLiveData<>();

    //Activate/Deactivate Users
    private MutableLiveData<ResponseCode> deactivateLiveData = new MutableLiveData<>();
    private MutableLiveData<ResponseCode> activateLiveData   = new MutableLiveData<>();
    private MutableLiveData<ResponseCode> linkLiveData       = new MutableLiveData<>();

    //List of Users
    private MutableLiveData<List<UserInfo>> listDeactivatedUsersLiveData = new MutableLiveData<>();
    private MutableLiveData<List<UserInfo>> listActivatedUsersLiveData   = new MutableLiveData<>();
    private MutableLiveData<List<UserInfo>> listUsersToLinkLiveData      = new MutableLiveData<>();


    public AdminRepo(Application application) {
        RetrofitClient client = RetrofitClient.getInstance();
        adminService = client.getRetrofit().create(AdminService.class);


        SharedPrefManager sharedPref = SharedPrefManager.getInstance(application);
        AuthHeader authHeader = new AuthHeader(
                sharedPref.getIdToken(),
                sharedPref.getIdClient(),
                sharedPref.getUserEmail()
        );
        client.setAuthHeader(authHeader);
    }

    public void getFirstPageMembers(int offset, int limit) {
        adminService.getUsersPaginitation(offset, limit).enqueue(new Callback<List<UserInfo>>() {
            @Override
            public void onResponse(@NonNull Call<List<UserInfo>> call, @NonNull Response<List<UserInfo>> response) {
                //Log.d(TAG, "onResponse: " + response.raw().request().url());
                if (response.body() != null) {
                    firstPageLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<UserInfo>> call, @NonNull Throwable t) {
                //Log.d(TAG, "onFailure: Error getting first page users: " + t);
                firstPageLiveData.postValue(null);
            }
        });
    }

    public void getNextPageMembers(int offset, int limit) {
        adminService.getUsersPaginitation(offset, limit).enqueue(new Callback<List<UserInfo>>() {
            @Override
            public void onResponse(Call<List<UserInfo>> call, Response<List<UserInfo>> response) {
                if (response.body() != null) {
                    nextPageLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<UserInfo>> call, Throwable t) {
                nextPageLiveData.postValue(null);
            }
        });
    }


    public void searchUsers(String gmail) {
        adminService.searchUsers(gmail).enqueue(new Callback<List<UserInfo>>() {
            @Override
            public void onResponse(Call<List<UserInfo>> call, Response<List<UserInfo>> response) {
                //Log.d(TAG, "onResponse: " + response.raw().request().url());
                if (response.body() != null) {
                    searchUserLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<UserInfo>> call, Throwable t) {
                //Log.d(TAG, "onFailure: Error search users: " + t);
                searchUserLiveData.postValue(null);
            }
        });
    }

    public void depositTicket(int userId, int amountToDeposit, int actionType) {
        adminService.depositTicket(
                userId,
                amountToDeposit,
                actionType
        ).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.code() == 200) {
                    depositLiveData.postValue(ResponseCode.SUCCESS);
                }else{
                    depositLiveData.postValue(ResponseCode.UNEXPECTED_ERROR);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: Deposit: ", t);
                depositLiveData.postValue(ResponseCode.FAILED);
            }
        });
    }

    public void withdrawTicket(int userId, int amountToWithdraw, int actionType) {
        adminService.withdrawTicket(
                userId,
                amountToWithdraw,
                actionType
        ).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.code() == 200) {
                    withdrawLiveData.postValue(ResponseCode.SUCCESS);
                }else{
                    withdrawLiveData.postValue(ResponseCode.UNEXPECTED_ERROR);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: Withdraw: ", t);
                withdrawLiveData.postValue(ResponseCode.FAILED);
            }
        });
    }

    public void getDeactivatedUsers() {
        adminService.getDeactivatedUsers().enqueue(new Callback<List<UserInfo>>() {
            @Override
            public void onResponse(@NonNull Call<List<UserInfo>> call, @NonNull Response<List<UserInfo>> response) {
                if (response.body() != null) {
                    Log.d(TAG, "onResponse: Not null");
                    listDeactivatedUsersLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<UserInfo>> call, @NonNull Throwable t) {
                listDeactivatedUsersLiveData.postValue(null);
            }
        });
    }

    public void getActivatedUsers() {
        adminService.getActivatedUsers().enqueue(new Callback<List<UserInfo>>() {
            @Override
            public void onResponse(@NonNull Call<List<UserInfo>> call, @NonNull Response<List<UserInfo>> response) {
                if (response.body() != null) {
                    Log.d(TAG, "onResponse: Not null");
                    listActivatedUsersLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<UserInfo>> call, @NonNull Throwable t) {
                listActivatedUsersLiveData.postValue(null);
            }
        });
    }

    public void getUsersToLink() {
        adminService.getMembersToLink().enqueue(new Callback<List<UserInfo>>() {
            @Override
            public void onResponse(@NonNull Call<List<UserInfo>> call, @NonNull Response<List<UserInfo>> response) {
                if (response.body() != null) {
                    Log.d(TAG, "onResponse: Not null");
                    getLinkUsersLiveData().postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<UserInfo>> call, @NonNull Throwable t) {
                getLinkUsersLiveData().postValue(null);
            }
        });
    }

    public void deactivateUser(int userId, boolean deactivate) {
        adminService.deactivateUser(userId, deactivate).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.code() == 200) {
                    deactivateLiveData.postValue(ResponseCode.SUCCESS);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                deactivateLiveData.postValue(ResponseCode.FAILED);
            }
        });
    }

    public void activateUser(int userId, boolean activate) {
        adminService.activateUser(userId, activate).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                Log.d(TAG, "onResponse: " + response.code());
                if (response.code() == 200) {
                    activateLiveData.postValue(ResponseCode.SUCCESS);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                activateLiveData.postValue(ResponseCode.FAILED);
            }
        });
    }

    public void linkFamilyToUser(int userId, int familyId) {
        adminService.registerUserToFamily(userId, familyId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    linkLiveData.postValue(ResponseCode.SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                linkLiveData.postValue(ResponseCode.FAILED);
            }
        });
    }

    public MutableLiveData<List<UserInfo>> getFirstPageLiveData() {
        return firstPageLiveData;
    }

    public MutableLiveData<List<UserInfo>> getNextPageLiveData() {
        return nextPageLiveData;
    }

    public MutableLiveData<List<UserInfo>> getSearchUserLiveData() {
        return searchUserLiveData;
    }

    public MutableLiveData<ResponseCode> getWithdrawLiveData() {
        return withdrawLiveData;
    }

    public MutableLiveData<ResponseCode> getDepositLiveData() {
        return depositLiveData;
    }

    //Get users live data
    public MutableLiveData<List<UserInfo>> getDeactivatedUsersLiveData() {
        return listDeactivatedUsersLiveData;
    }

    public MutableLiveData<List<UserInfo>> getActivatedUsersLiveData() {
        return listActivatedUsersLiveData;
    }

    public MutableLiveData<List<UserInfo>> getLinkUsersLiveData() {
        return listUsersToLinkLiveData;
    }

    //Action Listener Live data
    public MutableLiveData<ResponseCode> getDeactivateLiveData() {
        return deactivateLiveData;
    }

    public MutableLiveData<ResponseCode> getActivateLiveData() {
        return activateLiveData;
    }

    public MutableLiveData<ResponseCode> getLinkLiveData() {
        return linkLiveData;
    }

}
