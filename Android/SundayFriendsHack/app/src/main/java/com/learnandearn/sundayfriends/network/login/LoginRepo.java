package com.learnandearn.sundayfriends.network.login;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.learnandearn.sundayfriends.R;
import com.learnandearn.sundayfriends.network.RetrofitClient;
import com.learnandearn.sundayfriends.network.model.AuthHeader;
import com.learnandearn.sundayfriends.network.model.OnBoard;
import com.learnandearn.sundayfriends.network.model.OnBoardDto;
import com.learnandearn.sundayfriends.network.model.ResponseCode;
import com.learnandearn.sundayfriends.utils.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepo {

    private static final String TAG = "LoginRepo";

    private LoginService                loginService;
    private MutableLiveData<OnBoardDto> onBoardLiveData = new MutableLiveData<>();
    private Application                 application;
    private RetrofitClient              retrofit;
    private SharedPrefManager           sharedPrefManager;


    public LoginRepo(Application application) {
        this.application = application;
        retrofit = RetrofitClient.getInstance();
        sharedPrefManager = SharedPrefManager.getInstance(application);
        loginService = retrofit.getRetrofit().create(LoginService.class);

    }

    public void isUserActivated(GoogleSignInAccount account) {
        AuthHeader authHeader = new AuthHeader(
                account.getIdToken(),
                application.getString(R.string.idclient),
                account.getEmail()
        );

        retrofit.setAuthHeader(authHeader);

        loginService.IsUserRegistered(account.getGivenName(), account.getEmail(), String.valueOf(account.getPhotoUrl()))
                .enqueue(new Callback<OnBoard>() {
                    @Override
                    public void onResponse(@NonNull Call<OnBoard> call, @NonNull Response<OnBoard> response) {
                        switch (response.code()) {
                            case 200:
                                OnBoardDto success = new OnBoardDto(response.body(), ResponseCode.SUCCESS);
                                onBoardLiveData.postValue(success);
                                break;

                            case 409:
                                OnBoardDto notActivated = new OnBoardDto(null, ResponseCode.USER_NOT_ACTIVATED);
                                onBoardLiveData.postValue(notActivated);
                                break;

                            default:
                                OnBoardDto onBoardDto = new OnBoardDto(null, ResponseCode.UNEXPECTED_ERROR);
                                onBoardLiveData.postValue(onBoardDto);
                                break;
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<OnBoard> call, @NonNull Throwable t) {
                        OnBoardDto onBoardDto = new OnBoardDto(null, ResponseCode.UNEXPECTED_ERROR);
                        onBoardLiveData.postValue(onBoardDto);
                    }
                });
    }

    public void handleSignInResult(GoogleSignInAccount account) {
        sharedPrefManager.setUserEmail(account.getEmail());
        sharedPrefManager.setName(account.getDisplayName());
        sharedPrefManager.setUserProfilePic(String.valueOf(account.getPhotoUrl()));

        sharedPrefManager.setIdClient(application.getString(R.string.idclient));
        sharedPrefManager.setIdToken(account.getIdToken());

    }

    public MutableLiveData<OnBoardDto> getOnBoardLiveData() {
        return onBoardLiveData;
    }
}
