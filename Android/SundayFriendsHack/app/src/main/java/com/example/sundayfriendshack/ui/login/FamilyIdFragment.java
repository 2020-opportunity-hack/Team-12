package com.example.sundayfriendshack.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sundayfriendshack.Constants;
import com.example.sundayfriendshack.R;
import com.example.sundayfriendshack.manager.SharedPrefManager;
import com.example.sundayfriendshack.manager.ToastManager;
import com.example.sundayfriendshack.model.UserInfo;
import com.example.sundayfriendshack.repo.LoginRepo.LoginContract;
import com.example.sundayfriendshack.repo.LoginRepo.LoginRepo;
import com.example.sundayfriendshack.ui.admin.AdminActivity;
import com.example.sundayfriendshack.ui.user.MainActivity;
import com.example.sundayfriendshack.utils.KeyboardUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class FamilyIdFragment extends Fragment implements
        LoginContract.Model.onIsUserRegistered,
        LoginContract.Model.onRegisterFamilyId {

    private static final String TAG = "FamilyIdFragment";


    @BindView(R.id.fragfamilyid_image) ImageView mFamilyIdImage;

    @BindView(R.id.fragfamilyid_et_family_id) EditText mEtFamilyId;

    @BindView(R.id.fragmentfamilyid_parent_layout) ConstraintLayout mParentLayout;

    private String mName, mEmail, mImageUrl;

    private Context mContext;

    private LoginRepo mRepo;

    private LoginActivity activity;

    private int mUserId, mFamilyId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        activity = (LoginActivity) mContext;
        mRepo = new LoginRepo(mContext);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_family_id, container, false);
        ButterKnife.bind(this, view);

        mParentLayout.setVisibility(View.INVISIBLE);

        mName = activity.getAuthManager().getUserName();
        mEmail = activity.getAuthManager().getEmail();
        mImageUrl = activity.getAuthManager().getImageUrl();

        if(mImageUrl == null
                || mImageUrl.equals("null")){
            Glide.with(mContext)
                    .load(R.drawable.blank_profile_pic)
                    .apply(RequestOptions.circleCropTransform())
                    .into(mFamilyIdImage);
        }else{
            Glide.with(mContext)
                    .load(mImageUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .into(mFamilyIdImage);
        }

        mRepo.isUserRegistered(mName, mEmail, mImageUrl);

        return view;
    }


    @OnClick(R.id.fragfamily_btn_proceed)
    public void onProceedBtnClick(){
        String familyId = mEtFamilyId.getText().toString();
        if(!familyId.equals("")){
            KeyboardUtils.hideKeyboard(mEtFamilyId);
            mFamilyId = Integer.parseInt(mEtFamilyId.getText().toString());
            mRepo.registerFamilyId(mUserId, mFamilyId);
        }else{
            Toast.makeText(mContext, "Please enter your family id number. ", Toast.LENGTH_SHORT).show();
        }

    }

    @OnClick(R.id.fragfamily_btn_skip)
    public void onSkipBtnClick(){
        initAdminActivity();
    }


    private void initAdminActivity(){
        Intent intent = new Intent(mContext, AdminActivity.class);
        startActivity(intent);
        ((LoginActivity) mContext).finish();
    }


    @Override
    public void onSuccessGetUser(UserInfo userInfo) {
        try {
            mUserId = userInfo.getUserId();
            Log.d(TAG, "onSuccessGetUser: Name: " + userInfo.getName());
            Log.d(TAG, "onSuccessGetUser: Email: " + userInfo.getEmail());
            SharedPrefManager.getInstance(mContext).setUserId(mUserId);
            SharedPrefManager.getInstance(mContext).setUserName(userInfo.getName());
            SharedPrefManager.getInstance(mContext).setUserEmail(userInfo.getEmail());
            SharedPrefManager.getInstance(mContext).setUserProfilePic(userInfo.getImageUrl());
            SharedPrefManager.getInstance(mContext).setFamilyId(userInfo.getFamilyId());
            //Integer familyId = userInfo.getFamilyId();
            //if(familyId == null){
                //Does not have a parent id, display the parent id layout.
            //    Log.d(TAG, "onSuccessGetUser: Name: " + userInfo.getName());
            //    Log.d(TAG, "onSuccessGetUser: Email: " + userInfo.getEmail());
            //    Log.d(TAG, "onSuccessGetUser: ImageUrl: " + userInfo.getImageUrl());
            //    Log.d(TAG, "onSuccessGetUser: FamilyId: " + userInfo.getFamilyId());
            //    Log.d(TAG, "onSuccessGetUser: FamilyId is null, Launching FamilyIdFragment. ");
                mParentLayout.setVisibility(View.VISIBLE);
            //}else{
                //Is admin? else launch admin
            //    Log.d(TAG, "onSuccessGetUser: Name: " + userInfo.getName());
             //   Log.d(TAG, "onSuccessGetUser: Email: " + userInfo.getEmail());
             //   Log.d(TAG, "onSuccessGetUser: ImageUrl: " + userInfo.getImageUrl());
             //   Log.d(TAG, "onSuccessGetUser: IsAdmin: " + userInfo.isAdmin());
             //   Log.d(TAG, "onSuccessGetUser: FamilyId: " + userInfo.getFamilyId());
             //   Log.d(TAG, "onSuccessGetUser: FamilyId is not null, Going to MainActivity. ");
             //   initMainActivity(mContext, mUserId);
            //}
            //check if family id is null or not
            //start mainactivity
        }catch (Exception e){
            ToastManager.displayNetworkError(mContext, e);
        }
    }

    @Override
    public void onFailedGetUser(Throwable t) {
        Log.d(TAG, "onFailedGetUser: Failed to get user...." + t);

        Toast.makeText(mContext, "User account is deactivated. ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessRegisterFamilyId(Response<Void> response) {
        if(response.code() == Constants.SUCCESS_CODE){
            Toast.makeText(mContext, "Success Linking User to Family. ", Toast.LENGTH_SHORT).show();
            initMainActivity(mContext, mUserId);
        }else{
            //Family id does not exist
            //Toast.makeText(mContext, "Sorry something went wrong linking family to user. " + response.code(), Toast.LENGTH_LONG).show();
            //For demo display main activity
            initMainActivity(mContext, mUserId);
        }
    }

    private void initMainActivity(Context context, int userId){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(Constants.KEY_START_HOMEPAGE_USER_ID, userId);
        startActivity(intent);
    }

    @Override
    public void onFailedRegisterFamilyId(Throwable t) {
        ToastManager.displayNetworkError(mContext, t);
    }
}
