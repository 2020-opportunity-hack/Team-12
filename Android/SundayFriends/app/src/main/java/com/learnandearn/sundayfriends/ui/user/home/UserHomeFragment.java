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
import com.learnandearn.sundayfriends.Constants;
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

    @BindView(R.id.tv_my_dashboard)
    TextView tvMyDashboard;

    @BindView(R.id.tv_have_nice_day)
    TextView tvHaveANiceDay;

    @BindView(R.id.tv_my_sunday_dollars)
    TextView tvMySundayDollars;

    @BindView(R.id.tv_recent_transactions)
    TextView tvRecentTransactions;

    @BindView(R.id.tv_my_family_members)
    TextView tvMyFamilyMembers;

    @BindView(R.id.tv_my_profile)
    TextView tvMyProfile;


    private UserActivity activity;
    private SharedPrefManager sharedPrefManager;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        activity = (UserActivity) context;
        sharedPrefManager = SharedPrefManager.getInstance(context);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_home, container, false);
        ButterKnife.bind(this, view);

        setDate();


        String profilePic = sharedPrefManager.getProfilePic();
        loadProfilePic(profilePic);
        float balance = sharedPrefManager.getBalance();
        tvNumTickets.setText(String.valueOf(balance));


        setLanguageStrings(sharedPrefManager.getLanguage());



        return view;
    }

    private void setLanguageStrings(String language){
        String name = SharedPrefManager.getInstance(context).getName();
        switch (language){
            case Constants.VALUE_SHARED_PREF_LANGUAGE_ENGLISH:
                String welcomeMsg = "Welcome, " + name;
                tvWelcomeMsg.setText(welcomeMsg);
                break;

            case Constants.VALUE_SHARED_PREF_LANGUAGE_SPANISH:
                tvMyDashboard.setText(context.getString(R.string.my_dashboard_spn));

                String welcomeMsgSpn = context.getString(R.string.welcome_spn) + name;
                tvWelcomeMsg.setText(welcomeMsgSpn);

                tvHaveANiceDay.setText(context.getString(R.string.have_a_nice_day_spn));
                tvMySundayDollars.setText(context.getString(R.string.my_sunday_dollars_spn));
                tvRecentTransactions.setText(context.getString(R.string.recent_transactions_spn));
                tvMyFamilyMembers.setText(context.getString(R.string.my_family_members_spn));
                tvMyProfile.setText(context.getString(R.string.my_profile_spn));

                break;

            case Constants.VALUE_SHARED_PREF_LANGUAGE_VIETNAMESE:
                tvMyDashboard.setText(context.getString(R.string.my_dashboard_viet));

                String welcomeMsgViet = context.getString(R.string.welcome_viet) + name;
                tvWelcomeMsg.setText(welcomeMsgViet);

                tvHaveANiceDay.setText(context.getString(R.string.have_a_nice_day_viet));
                tvMySundayDollars.setText(context.getString(R.string.my_sunday_dollars_viet));
                tvRecentTransactions.setText(context.getString(R.string.recent_transactions_viet));
                tvMyFamilyMembers.setText(context.getString(R.string.my_family_members_viet));
                tvMyProfile.setText(context.getString(R.string.my_profile_viet));

                break;
        }
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
