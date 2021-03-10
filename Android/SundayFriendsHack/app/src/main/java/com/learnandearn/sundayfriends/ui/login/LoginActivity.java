package com.learnandearn.sundayfriends.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.learnandearn.sundayfriends.BaseApplication;
import com.learnandearn.sundayfriends.Constants;
import com.learnandearn.sundayfriends.R;
import com.learnandearn.sundayfriends.network.model.OnBoard;
import com.learnandearn.sundayfriends.network.model.OnBoardDto;
import com.learnandearn.sundayfriends.utils.ActivityNavUtils;
import com.learnandearn.sundayfriends.utils.AuthStateManager;
import com.learnandearn.sundayfriends.utils.SharedPrefManager;
import com.learnandearn.sundayfriends.utils.SnackbarManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";


    @BindView(R.id.app_logo)
    ImageView appLogo;

    @BindView(R.id.parent_layout)
    ViewGroup parentLayout;

    private AlertDialog         progressBar;
    private SharedPrefManager   sharedPrefManager;
    private AuthStateManager    authStateManager;
    private LoginViewModel      viewModel;
    private GoogleSignInAccount account;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        authStateManager = ((BaseApplication) getApplicationContext()).getAuthStateManager();
        sharedPrefManager = ((BaseApplication) getApplicationContext()).getSharedPrefManager();

        setAppLogo();

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        setIsUserRegisteredListenr();
    }

    //Need to Sign user out when user not activated or unexpected error.
    //Otherwise the user is signed in and the onboard won't work
    private void setIsUserRegisteredListenr() {
        viewModel.getOnBoardLiveData().observe(this, new Observer<OnBoardDto>() {
            @Override
            public void onChanged(OnBoardDto onBoardDto) {
                dismissProgressBar();

                switch (onBoardDto.getResponseCode()) {
                    case SUCCESS:
                        Log.d(TAG, "onChanged: user is activated");
                        handleOnBoardResponse(onBoardDto.getOnBoard());
                        break;

                    case USER_NOT_ACTIVATED:
                        Log.d(TAG, "onChanged: User not activated.");
                        SnackbarManager.unactivatedUser(parentLayout).show();
                        authStateManager.getClient().signOut();
                        break;

                    default:
                        Log.d(TAG, "onChanged: Unexpected error");
                        SnackbarManager.unexpectedError(LoginActivity.this, parentLayout).show();
                        authStateManager.getClient().signOut();
                        break;
                }
            }
        });
    }

    private void handleOnBoardResponse(OnBoard onBoard) {
        try {
            sharedPrefManager.setFamilyId(onBoard.getFamilyId());
            sharedPrefManager.setUserId(onBoard.getUserId());
            sharedPrefManager.setBalance(onBoard.getBalance());

            if (onBoard.isAdmin()) {
                sharedPrefManager.setIsAdmin(Constants.VALUE_SHARED_PREF_ADMIN);
            } else {
                sharedPrefManager.setIsAdmin(Constants.VALUE_SHARED_PREF_USER);
            }

            if (!onBoard.isAdmin()) {
                ActivityNavUtils.initUserActivity(this);
            } else {
                ActivityNavUtils.initAdminActivity(this);
            }
        } catch (Exception e) {
            Log.e(TAG, "handleOnBoardResponse: ", e);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE_SIGN_IN) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            setProgressDialog();
            try {
                account = task.getResult(ApiException.class);

                viewModel.handleGoogleSignInResult(account);
                viewModel.isUserActivated(account);

            } catch (Exception e) {
                dismissProgressBar();
                Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onActivityResult: ", e);
            }


        }
    }


    private void setAppLogo() {
        Glide.with(this)
                .load(R.drawable.app_logo_login_page)
                .apply(RequestOptions.centerInsideTransform())
                .into(appLogo);
    }

    @OnClick(R.id.btn_login)
    public void onLoginBtnClick() {
        Intent signInIntent = authStateManager.getClient().getSignInIntent();
        startActivityForResult(signInIntent, Constants.REQUEST_CODE_SIGN_IN);
    }

    private void dismissProgressBar() {
        if (progressBar != null) {
            progressBar.dismiss();
            progressBar = null;
        }
    }

    private void setProgressDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.alertDialogTheme);
        builder.setView(R.layout.dialog_login_progress);
        progressBar = builder.create();
        progressBar.setCancelable(false);
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.show();

    }


}
