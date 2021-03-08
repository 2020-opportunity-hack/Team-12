package com.learnandearn.sundayfriends.ui.admin.more;

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
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.learnandearn.sundayfriends.Constants;
import com.learnandearn.sundayfriends.R;
import com.learnandearn.sundayfriends.ui.admin.AdminActivity;
import com.learnandearn.sundayfriends.ui.admin.activate.ActivateUserFragment;
import com.learnandearn.sundayfriends.ui.admin.deactivate.DeactivateUserFragment;
import com.learnandearn.sundayfriends.ui.admin.linkfamily.LinkFamilyFragment;
import com.learnandearn.sundayfriends.utils.SharedPrefManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdminMoreFragment extends Fragment {

    private static final String TAG = "AdminMoreFragment";


    @BindView(R.id.profile_pic)
    ImageView ivProfilePic;

    @BindView(R.id.name)
    TextView tvName;

    @BindView(R.id.email)
    TextView tvEmail;

    private Context       context;
    private AdminActivity adminActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        adminActivity = (AdminActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_more, container, false);
        ButterKnife.bind(this, view);

        String mProfilePic = SharedPrefManager.getInstance(context).getProfilePic();
        if (mProfilePic == null
                || mProfilePic.equals("null")) {
            Glide.with(context)
                    .load(R.drawable.blank_profile_pic)
                    .apply(RequestOptions.circleCropTransform())
                    .into(ivProfilePic);
        } else {
            Glide.with(context)
                    .load(mProfilePic)
                    .apply(RequestOptions.circleCropTransform())
                    .into(ivProfilePic);
        }

        tvName.setText(SharedPrefManager.getInstance(context).getName());
        tvEmail.setText(SharedPrefManager.getInstance(context).getUserEmail());


        return view;
    }



    @OnClick(R.id.btn_sign_out)
    void onSignOutBtnClick() {
        adminActivity.signOutUser();
    }

    @OnClick(R.id.btn_activate_user)
    void onActivateAUserBtnClick() {
        getParentFragmentManager().beginTransaction()
                .add(
                        R.id.admin_fragment_container,
                        new ActivateUserFragment(),
                        Constants.FRAGMENT_ADMIN_ACTIVATE_USER
                )
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    @OnClick(R.id.btn_link_family)
    void onLinkFamilyBtnClick() {
        getParentFragmentManager().beginTransaction()
                .add(
                        R.id.admin_fragment_container,
                        new LinkFamilyFragment(),
                        Constants.FRAGMENT_ADMIN_LINK_FAMILY
                )
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    @OnClick(R.id.btn_deactivate_user)
    void onDeactivateUserBtnClick() {
        getParentFragmentManager().beginTransaction()
                .add(
                        R.id.admin_fragment_container,
                        new DeactivateUserFragment(),
                        Constants.FRAGMENT_ADMIN_DEACTIVATE_USER
                )
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }
}
