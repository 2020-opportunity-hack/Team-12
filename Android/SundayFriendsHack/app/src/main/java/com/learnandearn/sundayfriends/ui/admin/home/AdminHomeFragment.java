package com.learnandearn.sundayfriends.ui.admin.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.learnandearn.sundayfriends.R;
import com.learnandearn.sundayfriends.ui.admin.AdminActivity;
import com.learnandearn.sundayfriends.utils.SharedPrefManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdminHomeFragment extends Fragment {


    @BindView(R.id.profile_pic)
    ImageView ivProfilePic;

    @BindView(R.id.tv_welcome_msg)
    TextView tvName;

    @BindView(R.id.tv_date)
    TextView tvDate;

    private Context       context;
    private AdminActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        activity = (AdminActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_home, container, false);
        ButterKnife.bind(this, view);

        setWelcomeMessage();
        setProfilePic();
        setCurrentTime();

        return view;
    }

    private void setCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        tvDate.setText(currentDateandTime);
    }

    private void setWelcomeMessage() {
        String welcomeUser = "Welcome, " + SharedPrefManager.getInstance(context).getName();
        tvName.setText(welcomeUser);
    }

    private void setProfilePic() {
        String mProfilePic = SharedPrefManager.getInstance(context).getProfilePic();
        if (mProfilePic == null
                || mProfilePic.equals("null")) {
            Glide.with(context)
                    .load(R.drawable.blank_profile_pic)
                    .apply(RequestOptions.circleCropTransform())
                    .into(this.ivProfilePic);
        } else {
            Glide.with(context)
                    .load(mProfilePic)
                    .apply(RequestOptions.circleCropTransform())
                    .into(this.ivProfilePic);
        }
    }

    //Buttons
    @OnClick(R.id.btn_activate_deactivate)
    void onActivateDeactivateBtnClick(){
        activity.onActivateDeactivateBtnClick();
    }

    @OnClick(R.id.btn_withdraw_deposit)
    void onWithdrawDepositBtnClick(){
        activity.onWithDrawDepositBtnClick();
    }

    @OnClick(R.id.btn_link_family)
    void onLinkFamilyBtnClick(){
        activity.onLinkFamilyBtnClick();
    }


}
