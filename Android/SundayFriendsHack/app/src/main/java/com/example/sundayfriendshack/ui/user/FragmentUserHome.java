package com.example.sundayfriendshack.ui.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sundayfriendshack.manager.AuthStateManager;
import com.example.sundayfriendshack.Constants;
import com.example.sundayfriendshack.R;
import com.example.sundayfriendshack.manager.SharedPrefManager;
import com.example.sundayfriendshack.manager.ToastManager;
import com.example.sundayfriendshack.model.UserInfo;
import com.example.sundayfriendshack.repo.UserRepo.UserContract;
import com.example.sundayfriendshack.repo.UserRepo.UserRepo;
import com.example.sundayfriendshack.ui.login.LoginActivity;
import com.example.sundayfriendshack.ui.menu.SettingsMenu;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentUserHome extends Fragment implements UserContract.Model.onGetUserInfo {

    private static final String TAG = "FragmentUserHome";

    private Context mContext;

    @BindView(R.id.fraguserhome_toolbar_profile_pic) ImageView mToolbarProfilePic;

    @BindView(R.id.frag_userhome_date) TextView mTextViewDate;

    @BindView(R.id.fraguserhome_tv_user_name) TextView mTextViewName;

    @BindView(R.id.frag_userhome_num_tickets) TextView mTextViewNumTickets;

    private AuthStateManager mAuthStateManager;

    @BindView(R.id.frag_userhome_swiperefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private String mName, mProfilePic, mEmail;

    private MainActivity mainActivity;

    private UserRepo mRepo;

    public static FragmentUserHome newInstance(int userId){
        Bundle args = new Bundle();
        args.putInt(Constants.KEY_FRAGMENT_USER_ID, userId);
        FragmentUserHome fragmentUserHome = new FragmentUserHome();
        fragmentUserHome.setArguments(args);
        return fragmentUserHome;
    }
    
    @Override
    public void onStart() {
        super.onStart();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(mContext);
        if(account == null){
            Intent intent = new Intent(mContext, LoginActivity.class);
            mContext.startActivity(intent);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        mainActivity = (MainActivity) mContext;
        mAuthStateManager = new AuthStateManager(mContext);
        mRepo = new UserRepo(mContext);


        //if(getArguments() != null){
        //    mUserId = getArguments().getInt(Constants.KEY_FRAGMENT_USER_ID);
        //}

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_home, container, false);
        ButterKnife.bind(this, view);


        mRepo.getUserInfo(SharedPrefManager.getInstance(mContext).getUserId());

        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        mTextViewDate.setText(currentDateandTime);

        initSwipeRefreshLayout();


        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.RC_SIGN_IN) {
            mAuthStateManager.handleSignInResult();
        }
    }

    @OnClick(R.id.fraguserhome_toolbar_profile_pic)
    public void onSignOutBtnClick(){
        PopupWindow popupWindow = new SettingsMenu(mContext);
        popupWindow.showAsDropDown(mToolbarProfilePic);
    }

    public void signOutUser(){
        mAuthStateManager.signOut(getActivity());
    }


    @Override
    public void onSuccessGetUserInfo(UserInfo userInfo) {
        try {
            mSwipeRefreshLayout.setRefreshing(false);
            mName = "Welcome, " + userInfo.getName();
            mProfilePic = userInfo.getImageUrl();
            mEmail = userInfo.getEmail();
            int numTickets = userInfo.getBalance();

            mTextViewNumTickets.setText(String.valueOf(numTickets));

            mTextViewName.setText(mName);

            if(mProfilePic == null
                    || mProfilePic.equals("null")){
                Glide.with(mContext)
                        .load(R.drawable.blank_profile_pic)
                        .apply(RequestOptions.circleCropTransform())
                        .into(mToolbarProfilePic);
            }else{
                Glide.with(mContext)
                        .load(mProfilePic)
                        .apply(RequestOptions.circleCropTransform())
                        .into(mToolbarProfilePic);
            }

        }catch (Exception e){
            Log.d(TAG, "onSuccessGetUserInfo: Error: " + e);
            Toast.makeText(mContext, "Something went wrong getting user info. " + e, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailedGetUserInfo(Throwable t) {
        mSwipeRefreshLayout.setRefreshing(false);
        Log.d(TAG, "onFailedGetUserInfo: Failed to get user info");
        ToastManager.displayNetworkError(mContext, t);
    }

    private void initSwipeRefreshLayout(){
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorWhite));
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.standardBlue);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRepo.getUserInfo(SharedPrefManager.getInstance(mContext).getUserId());
            }
        });
    }

    @OnClick(R.id.square2)
    public void onClickRecentTransactions(){
        mainActivity.initFragmentUserTransactions(
                SharedPrefManager.getInstance(mContext).getUserName(),
                SharedPrefManager.getInstance(mContext).getUserId()
        );
    }
}
