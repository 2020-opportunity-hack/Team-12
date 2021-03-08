package com.learnandearn.sundayfriends.ui.user.home;

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
import com.learnandearn.sundayfriends.ui.user.UserActivity;
import com.learnandearn.sundayfriends.utils.SharedPrefManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserHomeFragment extends Fragment {

    private static final String TAG = "UserHomeFragment";

    private Context context;

    @BindView(R.id.iv_profile_pic)
    ImageView toolbarProfilePic;

    @BindView(R.id.tv_date)
    TextView tvDate;

    @BindView(R.id.tv_welcome_msg)
    TextView tvWelcomeMsg;

    @BindView(R.id.tv_num_tickets)
    TextView tvNumTickets;

    private UserActivity activity;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        activity = (UserActivity) context;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_home, container, false);
        ButterKnife.bind(this, view);

        setDate();
        setUserInfo();

        return view;
    }

    private void setUserInfo() {
        String name = SharedPrefManager.getInstance(context).getName();
        String profilePic = SharedPrefManager.getInstance(context).getProfilePic();
        String welcomeMsg = "Welcome, " + name;
        float balance = SharedPrefManager.getInstance(context).getBalance();

        tvWelcomeMsg.setText(welcomeMsg);
        tvNumTickets.setText(String.valueOf(balance));
        loadProfilePic(profilePic);

    }

    private void loadProfilePic(String profilePic) {
        if (!profilePic.equals("null")) {
            Glide.with(UserHomeFragment.this)
                    .load(profilePic)
                    .apply(RequestOptions.circleCropTransform())
                    .into(toolbarProfilePic);
        } else {
            Glide.with(UserHomeFragment.this)
                    .load(R.drawable.blank_profile_pic)
                    .apply(RequestOptions.circleCropTransform())
                    .into(toolbarProfilePic);
        }
    }

    private void setDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        tvDate.setText(currentDateandTime);
    }

    @OnClick(R.id.btn_recent_transactions)
    void onRecentTransactionsBtnClick(){
        activity.onRecentTransactionsBtnClick();
    }

    @OnClick(R.id.btn_family_members)
    void onFamilyMemebersBtnClick(){
        activity.onFamilyMembersBtnClick();
    }

    @OnClick(R.id.btn_my_profile)
    void onMyProfileBtnClick(){
        activity.onMyProfileBtnClick();
    }



}
