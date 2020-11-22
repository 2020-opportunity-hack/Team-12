package com.example.sundayfriendshack.ui.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.sundayfriendshack.Constants;
import com.example.sundayfriendshack.R;
import com.example.sundayfriendshack.manager.ToastManager;
import com.example.sundayfriendshack.model.FamilyMemberDto;
import com.example.sundayfriendshack.repo.UserRepo.UserContract;
import com.example.sundayfriendshack.ui.admin.FragmentAdminHome;
import com.example.sundayfriendshack.ui.admin.FragmentAdminMore;
import com.example.sundayfriendshack.ui.admin.FragmentAdminUsers;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        UserContract.Model.onGetFamilyMembers {

    private static final String TAG = "MainActivity";


    @BindView(R.id.activitymain_bottomnavigation) BottomNavigationView bottomNavigation;

    private Fragment currentBottomNavFragment;
    private FragmentUserHome mFragmentUserHome;
    private FragmentUserFamilyMembers mFragmentUserFamilyMembers;
    private FragmentUserProfile mFragmentUserProfile;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            mFragmentUserHome = new FragmentUserHome();
            currentBottomNavFragment = mFragmentUserHome;
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_fragment_container, mFragmentUserHome, Constants.FRAGMENT_USER_HOME)
                    .commit();
        }

        initBottomNavigation();

    }

    public void signOutUser(){
        FragmentUserHome fragmentUserHome = (FragmentUserHome) getSupportFragmentManager().findFragmentByTag(Constants.FRAGMENT_USER_HOME);
        if (fragmentUserHome != null) {
            fragmentUserHome.signOutUser();
        }
    }

    public void initFragmentUserTransactions(String name, int userId){
        FragmentUserTransactions fragmentUserTransactions = FragmentUserTransactions.newInstance(name, userId);
        fragmentUserTransactions.show(getSupportFragmentManager(), Constants.FRAGMENT_USER_TRANSACTIONS);
    }

    private void initBottomNavigation() {
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.bottomnav_home:
                        Log.d(TAG, "onNavigationItemSelected: " + currentBottomNavFragment);
                        if (!(currentBottomNavFragment instanceof FragmentUserHome)) {
                            if (mFragmentUserHome != null) {
                                getSupportFragmentManager().beginTransaction()
                                        .show(mFragmentUserHome)
                                        .hide(currentBottomNavFragment)
                                        .commit();
                                currentBottomNavFragment = mFragmentUserHome;
                            } else {
                                mFragmentUserHome = new FragmentUserHome();
                                getSupportFragmentManager().beginTransaction()
                                        .add(R.id.main_fragment_container, mFragmentUserHome, Constants.FRAGMENT_USER_HOME)
                                        .hide(currentBottomNavFragment)
                                        .commit();
                                currentBottomNavFragment = mFragmentUserHome;
                            }
                        }

                        return true;

                    case R.id.bottomnav_search:
                        if (!(currentBottomNavFragment instanceof FragmentUserFamilyMembers)) {
                            if (mFragmentUserFamilyMembers != null) {
                                getSupportFragmentManager().beginTransaction()
                                        .show(mFragmentUserFamilyMembers)
                                        .hide(currentBottomNavFragment)
                                        .commit();
                                currentBottomNavFragment = mFragmentUserFamilyMembers;
                            } else {
                                mFragmentUserFamilyMembers = new FragmentUserFamilyMembers();
                                getSupportFragmentManager().beginTransaction()
                                        .add(R.id.main_fragment_container, mFragmentUserFamilyMembers, Constants.FRAGMENT_USER_FAMILY_MEMBERS)
                                        .hide(currentBottomNavFragment)
                                        .commit();
                                currentBottomNavFragment = mFragmentUserFamilyMembers;
                            }
                        }
                        return true;


                    case R.id.bottomnav_profile:
                        if (!(currentBottomNavFragment instanceof FragmentUserProfile)) {
                            if (mFragmentUserProfile != null) {
                                getSupportFragmentManager().beginTransaction()
                                        .show(mFragmentUserProfile)
                                        .hide(currentBottomNavFragment)
                                        .commit();
                                currentBottomNavFragment = mFragmentUserProfile;
                            } else {
                                mFragmentUserProfile = new FragmentUserProfile();
                                getSupportFragmentManager().beginTransaction()
                                        .add(R.id.main_fragment_container, mFragmentUserProfile, Constants.FRAGMENT_USER_PROFILE)
                                        .hide(currentBottomNavFragment)
                                        .commit();
                                currentBottomNavFragment = mFragmentUserProfile;
                            }
                        }
                        return true;

                }
                return false;
            }
        });
    }

    @Override
    public void onSuccessGetFamilyMembers(ArrayList<FamilyMemberDto> familyMemberDtos) {

    }

    @Override
    public void onFailedGetFamilyMembers(Throwable t) {
        ToastManager.displayNetworkError(this, t);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.d(TAG, "onSaveInstanceState: Saving state");
        getSupportFragmentManager().putFragment(outState, Constants.KEY_INSTANCE_STATE_USER_CURRENT_FRAGMENT, currentBottomNavFragment);

        if (mFragmentUserHome != null) {
            getSupportFragmentManager().putFragment(outState, Constants.KEY_INSTANCE_STATE_USER_HOME, mFragmentUserHome);
        }

        if (mFragmentUserFamilyMembers != null) {
            getSupportFragmentManager().putFragment(outState, Constants.KEY_INSTANCE_STATE_USER_FAMILY_MEMBERS, mFragmentUserFamilyMembers);
        }

        if (mFragmentUserProfile != null) {
            getSupportFragmentManager().putFragment(outState, Constants.KEY_INSTANCE_STATE_USER_PROFILE, mFragmentUserProfile);
        }


    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Log.d(TAG, "onRestoreInstanceState: Restoring state");
        currentBottomNavFragment = getSupportFragmentManager()
                .getFragment(savedInstanceState, Constants.KEY_INSTANCE_STATE_USER_CURRENT_FRAGMENT);

        mFragmentUserHome = (FragmentUserHome) getSupportFragmentManager()
                .getFragment(savedInstanceState, Constants.KEY_INSTANCE_STATE_USER_HOME);

        mFragmentUserFamilyMembers = (FragmentUserFamilyMembers) getSupportFragmentManager()
                .getFragment(savedInstanceState, Constants.KEY_INSTANCE_STATE_USER_FAMILY_MEMBERS);

        mFragmentUserProfile = (FragmentUserProfile) getSupportFragmentManager()
                .getFragment(savedInstanceState, Constants.KEY_INSTANCE_STATE_USER_PROFILE);
    }
}
