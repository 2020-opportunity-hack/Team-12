package com.example.sundayfriendshack.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sundayfriendshack.manager.AuthStateManager;
import com.example.sundayfriendshack.Constants;
import com.example.sundayfriendshack.R;
import com.example.sundayfriendshack.manager.ToastManager;
import com.example.sundayfriendshack.repo.LoginRepo.LoginContract;
import com.example.sundayfriendshack.ui.user.MainActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements LoginContract.Model.onRegisterUser {

    private static final String TAG = "LoginActivity";

    private AuthStateManager mAuthStateManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mAuthStateManager = new AuthStateManager(this);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null){
            initMainActivity();
        }else{
            initFragmentLogin();
        }

    }

    private void initMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
        finish();
    }

    private void initFragmentLogin(){
        FragmentLogin fragmentLogin = new FragmentLogin();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.activitylogin_container, fragmentLogin, Constants.FRAGMENT_LOGIN)
                .commit();
    }

    public void initFamilyIdFragment(){
        FamilyIdFragment familyIdFragment = new FamilyIdFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.activitylogin_container, familyIdFragment, Constants.FAMILY_ID_FRAGMENT)
                .commit();
    }

    public AuthStateManager getAuthManager(){
        return mAuthStateManager;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.RC_SIGN_IN) {
            mAuthStateManager.handleSignInResult();
        }
    }


    @Override
    public void onSuccessRegisterUser() {
        Toast.makeText(this, "Success Registering User. ", Toast.LENGTH_SHORT).show();
        //Do someother action
    }

    @Override
    public void onFailedRegisterUser(Throwable t) {
        ToastManager.displayNetworkError(this, t);
    }
}
