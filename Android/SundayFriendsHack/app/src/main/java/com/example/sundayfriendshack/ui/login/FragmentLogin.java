package com.example.sundayfriendshack.ui.login;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sundayfriendshack.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentLogin extends Fragment {

    @BindView(R.id.activitylogin_app_logo) ImageView mAppLogo;

    private Context mContext;
    private LoginActivity loginActivity;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        loginActivity = (LoginActivity) mContext;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);


        setAppLogo();

        return view;
    }

    private void setAppLogo(){
        Glide.with(this)
                .load(R.drawable.ic_sunday_friends_logo)
                .apply(RequestOptions.centerInsideTransform())
                .into(mAppLogo);
    }

    @OnClick(R.id.activitylogin_test_btn)
    public void onLoginBtnClick(){
        loginActivity.getAuthManager().signIn(mContext);
    }

}
