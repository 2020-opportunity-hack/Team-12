package com.learnandearn.sundayfriends.ui.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.learnandearn.sundayfriends.network.login.LoginRepo;
import com.learnandearn.sundayfriends.network.model.OnBoard;
import com.learnandearn.sundayfriends.network.model.OnBoardDto;

public class LoginViewModel extends AndroidViewModel {

    private LoginRepo            repo;
    private LiveData<OnBoardDto> onBoardLiveData;

    public LoginViewModel(@NonNull Application application) {
        super(application);

        repo = new LoginRepo(application);
        onBoardLiveData = repo.getOnBoardLiveData();
    }

    public void isUserActivated(GoogleSignInAccount account) {
        repo.isUserActivated(account);
    }

    public void handleGoogleSignInResult(GoogleSignInAccount signInAccount){
        repo.handleSignInResult(signInAccount);
    }


    public LiveData<OnBoardDto> getOnBoardLiveData() {
        return onBoardLiveData;
    }
}
