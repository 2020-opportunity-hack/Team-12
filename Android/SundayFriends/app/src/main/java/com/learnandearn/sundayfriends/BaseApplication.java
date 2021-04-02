package com.learnandearn.sundayfriends;

import android.app.Application;

import com.learnandearn.sundayfriends.utils.AuthStateManager;
import com.learnandearn.sundayfriends.utils.SharedPrefManager;

public class BaseApplication extends Application {

    private SharedPrefManager sharedPrefManager;
    private AuthStateManager authStateManager;

    @Override
    public void onCreate() {
        super.onCreate();

        sharedPrefManager = SharedPrefManager.getInstance(this);
        authStateManager = new AuthStateManager(this);
    }

    public SharedPrefManager getSharedPrefManager() {
        return sharedPrefManager;
    }

    public AuthStateManager getAuthStateManager() {
        return authStateManager;
    }
}
