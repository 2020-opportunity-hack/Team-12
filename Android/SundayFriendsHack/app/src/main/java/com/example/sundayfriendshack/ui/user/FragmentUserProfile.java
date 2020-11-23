package com.example.sundayfriendshack.ui.user;

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
import com.example.sundayfriendshack.R;
import com.example.sundayfriendshack.manager.SharedPrefManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentUserProfile extends Fragment {

    private static final String TAG = "FragmentUserProfile";

    private Context mContext;

    @BindView(R.id.fraguserprofile_profile_picture)
    ImageView mProfilePic;

    @BindView(R.id.fraguserprofile_full_name)
    TextView mTopLayoutName;

    @BindView(R.id.fraguserprofile_email)
    TextView mTopLayoutEmail;

    @BindView(R.id.frag_userprofile_second_name)
    TextView mTvSecondName;

    @BindView(R.id.frag_userprofile_second_email)
    TextView mTvSecondEmail;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        ButterKnife.bind(this, view);

        String imageUrl = SharedPrefManager.getInstance(mContext).getProfilePic();

        if(imageUrl == null
                || imageUrl.equals("null")){
            Glide.with(mContext)
                    .load(R.drawable.blank_profile_pic)
                    .apply(RequestOptions.circleCropTransform())
                    .into(mProfilePic);
        }else{
            Glide.with(mContext)
                    .load(imageUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .into(mProfilePic);
        }


        Log.d(TAG, "onCreateView: name: " + SharedPrefManager.getInstance(mContext).getUserName());
        Log.d(TAG, "onCreateView: email: " + SharedPrefManager.getInstance(mContext).getUserEmail());
        mTopLayoutName.setText(SharedPrefManager.getInstance(mContext).getUserName());
        mTopLayoutEmail.setText(SharedPrefManager.getInstance(mContext).getUserEmail());

        mTvSecondEmail.setText(SharedPrefManager.getInstance(mContext).getUserEmail());
        mTvSecondName.setText(SharedPrefManager.getInstance(mContext).getUserName());




        return view;
    }
}
