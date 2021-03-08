package com.learnandearn.sundayfriends.utils;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.learnandearn.sundayfriends.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class AuthStateManager {

    private static final String TAG = "AuthStateManager";

    private GoogleSignInClient client;
    private GoogleSignInAccount account;

    private AuthStateManager() { }

    public AuthStateManager(Application application){
            GoogleSignInOptions gso = new GoogleSignInOptions
                    .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(application.getString(R.string.idclient))
                    .requestEmail()
                    .build();

            this.client = GoogleSignIn.getClient(application, gso);
            this.account = GoogleSignIn.getLastSignedInAccount(application);
    }

    public String getIdToken(){
        if (account != null) {
            return account.getIdToken();
        }
        return null;
    }

    public boolean isUserSignedIn() {
        if (account != null) {
            return true;
        }
        return false;
    }


    //Display name is full name
    public String getName() {
        if (account != null) {
            return account.getGivenName();
        }
        return null;
    }

    public String getEmail() {
        if (account != null) {
            return account.getEmail();
        }
        return null;
    }

    public String getUserProfilePic() {
        if (account != null) {
            Log.d(TAG, "getUserProfilePic: Image: " + account.getPhotoUrl());
            return String.valueOf(account.getPhotoUrl());
        }
        return null;
    }

    public GoogleSignInClient getClient() {
        return client;
    }

    public GoogleSignInAccount getAccount() {
        return account;
    }
}
