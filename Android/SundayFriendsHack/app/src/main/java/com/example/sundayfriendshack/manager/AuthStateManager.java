package com.example.sundayfriendshack.manager;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.sundayfriendshack.Constants;
import com.example.sundayfriendshack.ui.admin.AdminActivity;
import com.example.sundayfriendshack.ui.login.LoginActivity;
import com.example.sundayfriendshack.ui.user.MainActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class AuthStateManager {

    private static final String TAG = "AuthStateManager";

    private Context mContext;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInAccount mAccount;

    public AuthStateManager(Context context) {
        this.mContext = context;

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(mContext, gso);
    }

    public void handleSignInResult() {
            mAccount = GoogleSignIn.getLastSignedInAccount(mContext);
            if(mAccount == null){
                Log.d(TAG, "handleSignInResult: Null Account starting login Activity");
                initLoginActivity(mContext);
            }else{
                //get UserId
                //initMainActivity(mContext);
                Log.d(TAG, "handleSignInResult: Not null starting family id");
                initFamilyIdFragment(mContext);
            }
    }

    public String getGoogleId(){
        if(mAccount != null){
            Log.d(TAG, "getGoogleId: " + mAccount.getId());
            return mAccount.getId();
        }
        return null;
    }

    public String getUserName(){
        if(mAccount != null){
            Log.d(TAG, "getUserName: " + mAccount.getDisplayName());
            return mAccount.getDisplayName();
        }
        return null;
    }

    public String getEmail(){
        if(mAccount != null){
            Log.d(TAG, "getUserName: " + mAccount.getEmail());
            return mAccount.getEmail();
        }
        return null;
    }

    public String getImageUrl(){
        if(mAccount != null){
            Log.d(TAG, "getUserName: " + mAccount.getPhotoUrl());
            return String.valueOf(mAccount.getPhotoUrl());
        }
        return null;
    }

    public void signOut(Context context){
        mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    initLoginActivity(context);
                }
            }
        });
    }

    public void signIn(Context context){
        if(context instanceof LoginActivity){
            LoginActivity loginActivity = (LoginActivity) context;
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            loginActivity.startActivityForResult(signInIntent, Constants.RC_SIGN_IN);
        }

    }

    private void initFamilyIdFragment(Context context){
        if(context instanceof LoginActivity){
            Log.d(TAG, "initFamilyIdFragment: Starting Family Id Fragment");
            LoginActivity loginActivity = (LoginActivity) context;
            loginActivity.initFamilyIdFragment();
        }


    }

    private void initLoginActivity(Context context){
        if(context instanceof MainActivity){
            Log.d(TAG, "initLoginActivity: Starting Login Activity");
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
            ((MainActivity) context).finish();
        }else if (context instanceof AdminActivity){
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
            ((AdminActivity) context).finish();
        }
    }


}
