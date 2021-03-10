package com.learnandearn.sundayfriends.ui.user;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.learnandearn.sundayfriends.BaseApplication;
import com.learnandearn.sundayfriends.Constants;
import com.learnandearn.sundayfriends.R;
import com.learnandearn.sundayfriends.network.model.UserInfo;
import com.learnandearn.sundayfriends.ui.shared.usertransactions.UserTransactionsFragment;
import com.learnandearn.sundayfriends.ui.user.familymembers.FamilyMembersAdapter;
import com.learnandearn.sundayfriends.ui.user.familymembers.FamilyMembersFragment;
import com.learnandearn.sundayfriends.ui.user.home.UserDashBoardBtnClickListener;
import com.learnandearn.sundayfriends.ui.user.home.UserHomeFragment;
import com.learnandearn.sundayfriends.ui.user.profile.UserProfileFragment;
import com.learnandearn.sundayfriends.utils.ActivityNavUtils;
import com.learnandearn.sundayfriends.utils.AuthStateManager;
import com.learnandearn.sundayfriends.utils.SharedPrefManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserActivity extends AppCompatActivity implements
        FamilyMembersAdapter.FamilyMemberAdapterCallback,
        UserDashBoardBtnClickListener {

    private static final String TAG = "UserActivity";


    @BindView(R.id.activitymain_bottomnavigation)
    BottomNavigationView bottomNavigation;

    private AuthStateManager authStateManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);

        authStateManager = ((BaseApplication) this.getApplicationContext()).getAuthStateManager();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.user_fragment_container, new UserHomeFragment(), Constants.FRAGMENT_USER_HOME)
                    .commit();
        }

        initBottomNavigation();

    }

    public void signOutUser() {
        authStateManager.getClient().signOut().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                authStateManager.getClient().revokeAccess();
                ActivityNavUtils.initLoginActivity(UserActivity.this);
            }
        });
    }

    private void initBottomNavigation() {
        bottomNavigation.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.bottomnav_user_home:
                    UserHomeFragment userHomeFragment = (UserHomeFragment) getSupportFragmentManager()
                            .findFragmentByTag(Constants.FRAGMENT_USER_HOME);
                    if (userHomeFragment == null || !userHomeFragment.isVisible()) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(
                                        R.id.user_fragment_container,
                                        new UserHomeFragment(),
                                        Constants.FRAGMENT_USER_HOME
                                )
                                .commit();
                    }
                    return true;

                case R.id.bottomnav_user_family:
                    FamilyMembersFragment familyMembersFragment = (FamilyMembersFragment) getSupportFragmentManager()
                            .findFragmentByTag(Constants.FRAGMENT_USER_FAMILY_MEMBERS);
                    if (familyMembersFragment == null || !familyMembersFragment.isVisible()) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(
                                        R.id.user_fragment_container,
                                        new FamilyMembersFragment(),
                                        Constants.FRAGMENT_USER_FAMILY_MEMBERS
                                )
                                .commit();
                    }
                    return true;

                case R.id.bottomnav_user_profile:
                    UserProfileFragment userProfileFragment = (UserProfileFragment) getSupportFragmentManager()
                            .findFragmentByTag(Constants.FRAGMENT_USER_PROFILE);
                    if (userProfileFragment == null || !userProfileFragment.isVisible()) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(
                                        R.id.user_fragment_container,
                                        new UserProfileFragment(),
                                        Constants.FRAGMENT_USER_PROFILE
                                )
                                .commit();
                    }
                    return true;

            }
            return false;
        });
    }

    @Override
    public void onFamilyMemberClick(UserInfo userInfo) {
        UserTransactionsFragment userTransactionsFragment =
                UserTransactionsFragment.newInstance(userInfo);
        userTransactionsFragment.show(
                getSupportFragmentManager(),
                Constants.FRAGMENT_USER_TRANSACTIONS
        );
    }

    @Override
    public void onRecentTransactionsBtnClick() {
        UserInfo userInfo = new UserInfo(SharedPrefManager.getInstance(this).getUserId(), SharedPrefManager.getInstance(this).getName());
        DialogFragment transactionHistory = UserTransactionsFragment.newInstance(userInfo);
        transactionHistory.show(getSupportFragmentManager(), Constants.FRAGMENT_USER_TRANSACTIONS);
    }

    @Override
    public void onFamilyMembersBtnClick() {
        bottomNavigation.setSelectedItemId(R.id.bottomnav_user_family);
        getSupportFragmentManager().beginTransaction()
                .replace(
                        R.id.user_fragment_container,
                        new FamilyMembersFragment(),
                        Constants.FRAGMENT_USER_FAMILY_MEMBERS
                )
                .commit();
    }

    @Override
    public void onMyProfileBtnClick() {
        bottomNavigation.setSelectedItemId(R.id.bottomnav_user_profile);
        getSupportFragmentManager().beginTransaction()
                .replace(
                        R.id.user_fragment_container,
                        new UserProfileFragment(),
                        Constants.FRAGMENT_USER_PROFILE
                )
                .commit();
    }

    //Go back to homepage on backpressed if it's not on homepage
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            UserHomeFragment userHomeFragment = (UserHomeFragment) getSupportFragmentManager()
                    .findFragmentByTag(Constants.FRAGMENT_USER_HOME);
            if (userHomeFragment == null || !userHomeFragment.isVisible()) {
                bottomNavigation.setSelectedItemId(R.id.bottomnav_user_home);
                getSupportFragmentManager().beginTransaction()
                        .replace(
                                R.id.user_fragment_container,
                                new UserHomeFragment(),
                                Constants.FRAGMENT_USER_HOME
                        )
                        .commit();
            } else {
                super.onBackPressed();
            }
        }else{
            super.onBackPressed();
        }
    }
}
