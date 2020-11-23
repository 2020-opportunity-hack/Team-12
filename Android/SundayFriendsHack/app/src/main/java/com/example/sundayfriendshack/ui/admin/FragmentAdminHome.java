package com.example.sundayfriendshack.ui.admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sundayfriendshack.manager.AuthStateManager;
import com.example.sundayfriendshack.Constants;
import com.example.sundayfriendshack.R;
import com.example.sundayfriendshack.manager.SharedPrefManager;
import com.example.sundayfriendshack.ui.menu.SettingsMenu;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentAdminHome extends Fragment {

    private Context mContext;

    @BindView(R.id.fragadminhome_toolbar_profile_pic) ImageView mToolbarProfilePic;

    @BindView(R.id.fragadminhome_tv_user_name) TextView mTvName;

    @BindView(R.id.frag_adminhome_date) TextView mTextViewDate;

    private AuthStateManager mAuthStateManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        mAuthStateManager = new AuthStateManager(mContext);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_home, container, false);
        ButterKnife.bind(this, view);


        String mProfilePic = SharedPrefManager.getInstance(mContext).getProfilePic();

        String welcomeUser = "Welcome, " + SharedPrefManager.getInstance(mContext).getUserName();
        mTvName.setText(welcomeUser);

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

        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        mTextViewDate.setText(currentDateandTime);



        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.RC_SIGN_IN) {
            mAuthStateManager.handleSignInResult();
        }
    }

    @OnClick(R.id.fragadminhome_toolbar_profile_pic)
    public void onSignOutBtnClick(){
        PopupWindow popupWindow = new SettingsMenu(mContext);
        popupWindow.showAsDropDown(mToolbarProfilePic);
    }

    public void signOutUser(){
        mAuthStateManager.signOut(getActivity());
    }
}
