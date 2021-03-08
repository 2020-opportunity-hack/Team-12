package com.learnandearn.sundayfriends.ui.user.profile;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserProfileFragment extends Fragment {

    private static final String TAG = "UserProfileFragment";

    private Context context;

    @BindView(R.id.iv_profile_pic)
    ImageView ivProfilePic;

    @BindView(R.id.tv_name)
    TextView tvTopLayoutName;

    @BindView(R.id.tv_email)
    TextView tvTopLayoutEmail;

    @BindView(R.id.tv_name_two)
    TextView tvSecondName;

    @BindView(R.id.tv_email_two)
    TextView tvSecondEmail;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        ButterKnife.bind(this, view);

        String imageUrl = SharedPrefManager.getInstance(context).getProfilePic();

        if(imageUrl == null
                || imageUrl.equals("null")){
            Glide.with(UserProfileFragment.this)
                    .load(R.drawable.blank_profile_pic)
                    .apply(RequestOptions.circleCropTransform())
                    .into(ivProfilePic);
        }else{
            Glide.with(UserProfileFragment.this)
                    .load(imageUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .into(ivProfilePic);
        }


        tvTopLayoutName.setText(SharedPrefManager.getInstance(context).getName());
        tvTopLayoutEmail.setText(SharedPrefManager.getInstance(context).getUserEmail());

        tvSecondEmail.setText(SharedPrefManager.getInstance(context).getUserEmail());
        tvSecondName.setText(SharedPrefManager.getInstance(context).getName());

        return view;
    }

    @OnClick(R.id.btn_sign_out)
    void onUserSignOut(){
        UserActivity userActivity = (UserActivity) context;
        userActivity.signOutUser();
    }
}
