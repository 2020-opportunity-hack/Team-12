package com.example.sundayfriendshack.ui.user;

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

public class FragmentUserProfile extends Fragment {

    private Context mContext;

    @BindView(R.id.fraguserprofile_profile_picture)
    ImageView mProfilePic;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        ButterKnife.bind(this, view);

        Glide.with(this)
                .load("https://i.imgur.com/c6BU51Q.jpg")
                .apply(RequestOptions.circleCropTransform())
                .into(mProfilePic);


        return view;
    }
}
