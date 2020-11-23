package com.example.sundayfriendshack.ui.admin;

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
import com.example.sundayfriendshack.R;
import com.example.sundayfriendshack.manager.SharedPrefManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentAdminMore extends Fragment {

    private Context mContext;

    @BindView(R.id.fragadminprofile_profile_picture)
    ImageView mProfilePicture;

    @BindView(R.id.frag_adminprofile_name) TextView mTvName;

    @BindView(R.id.frag_adminprofile_email) TextView mTvEmail;

    private AdminActivity adminActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        adminActivity = (AdminActivity) mContext;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_more, container, false);
        ButterKnife.bind(this, view);

        String mProfilePic = SharedPrefManager.getInstance(mContext).getProfilePic();
        if(mProfilePic == null
                || mProfilePic.equals("null")){
            Glide.with(mContext)
                    .load(R.drawable.blank_profile_pic)
                    .apply(RequestOptions.circleCropTransform())
                    .into(mProfilePicture);
        }else{
            Glide.with(mContext)
                    .load(mProfilePic)
                    .apply(RequestOptions.circleCropTransform())
                    .into(mProfilePicture);
        }

        mTvName.setText(SharedPrefManager.getInstance(mContext).getUserName());
        mTvEmail.setText(SharedPrefManager.getInstance(mContext).getUserEmail());


        return view;
    }

    @OnClick(R.id.frag_adminprofile_activate_user)
    public void onActivateAUserBtnClick(){
        adminActivity.initDeactivatedUsersFragment();
    }
}
