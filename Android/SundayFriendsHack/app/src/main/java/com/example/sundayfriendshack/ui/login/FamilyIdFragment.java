package com.example.sundayfriendshack.ui.login;

import android.content.Context;
import android.content.Intent;
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
import com.example.sundayfriendshack.ui.admin.AdminActivity;
import com.example.sundayfriendshack.ui.user.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FamilyIdFragment extends Fragment {


    @BindView(R.id.fragfamilyid_image) ImageView mFamilyIdImage;

    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_family_id, container, false);
        ButterKnife.bind(this, view);

        Glide.with(mContext)
                .load("https://i.imgur.com/TLz8RGI.jpg")
                .apply(RequestOptions.centerInsideTransform())
                .into(mFamilyIdImage);

        return view;
    }

    @OnClick(R.id.fragfamily_btn_proceed)
    public void onProceedBtnClick(){
        initMainActivity();
    }

    @OnClick(R.id.fragfamily_btn_skip)
    public void onSkipBtnClick(){
        initAdminActivity();
    }

    private void initMainActivity(){
        Intent intent = new Intent(mContext, MainActivity.class);
        startActivity(intent);
        ((LoginActivity) mContext).finish();
    }

    private void initAdminActivity(){
        Intent intent = new Intent(mContext, AdminActivity.class);
        startActivity(intent);
        ((LoginActivity) mContext).finish();
    }

}
