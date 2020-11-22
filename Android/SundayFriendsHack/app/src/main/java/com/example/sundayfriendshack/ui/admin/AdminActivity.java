package com.example.sundayfriendshack.ui.admin;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.sundayfriendshack.Constants;
import com.example.sundayfriendshack.R;
import com.example.sundayfriendshack.ui.user.FragmentUserTransactions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminActivity extends AppCompatActivity {

    private static final String TAG = "AdminActivity";

    @BindView(R.id.activityadmin_bottomnavigation) BottomNavigationView bottomNavigation;

    private Fragment currentBottomNavFragment;
    private FragmentAdminHome mFragmentAdminHome;
    private FragmentAdminUsers mFragmentAdminListUsers;
    private FragmentAdminMore mFragmentAdminMore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            mFragmentAdminHome = new FragmentAdminHome();
            currentBottomNavFragment = mFragmentAdminHome;
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activityadmin_main_fragment_container, mFragmentAdminHome, Constants.FRAGMENT_ADMIN_HOME)
                    .commit();
        }

        initBottomNavigation();


    }

    public void signOutUser(){
        FragmentAdminHome fragmentAdminHome = (FragmentAdminHome) getSupportFragmentManager()
                .findFragmentByTag(Constants.FRAGMENT_ADMIN_HOME);
        if (fragmentAdminHome != null) {
            Log.d(TAG, "signOutUser: FOUND ADMIN HOME SIGNING OUT USER");
            fragmentAdminHome.signOutUser();
        }else{
            Log.d(TAG, "signOutUser: CANNOT FIND ADMIN HOME");
        }
    }

    public void initUserTransactionHistory(String name, int userId){
        FragmentUserTransactions transactionHistory = FragmentUserTransactions.newInstance(name, userId);
        transactionHistory.show(getSupportFragmentManager(), Constants.FRAGMENT_USER_TRANSACTIONS);
    }

    public void initWithdrawFragment(String name, int userId){
        FragmentWithdraw fragmentWithdraw = FragmentWithdraw.newInstance(name, userId);
        fragmentWithdraw.show(getSupportFragmentManager(), Constants.FRAGMENT_ADMIN_USER_WITHDRAW);
    }

    public void initDepositFragment(String name, int userId){
        FragmentDeposit fragmentDeposit = FragmentDeposit.newInstance(name, userId);
        fragmentDeposit.show(getSupportFragmentManager(), Constants.FRAGMENT_ADMIN_USER_DEPOSIT);
    }


    private void initBottomNavigation() {
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.bottomnav_home:
                        Log.d(TAG, "onNavigationItemSelected: " + currentBottomNavFragment);
                        if (!(currentBottomNavFragment instanceof FragmentAdminHome)) {
                            if (mFragmentAdminHome != null) {
                                getSupportFragmentManager().beginTransaction()
                                        .show(mFragmentAdminHome)
                                        .hide(currentBottomNavFragment)
                                        .commit();
                                currentBottomNavFragment = mFragmentAdminHome;
                            } else {
                                mFragmentAdminHome = new FragmentAdminHome();
                                getSupportFragmentManager().beginTransaction()
                                        .add(R.id.activityadmin_main_fragment_container, mFragmentAdminHome, Constants.FRAGMENT_ADMIN_HOME)
                                        .hide(currentBottomNavFragment)
                                        .commit();
                                currentBottomNavFragment = mFragmentAdminHome;
                            }
                        }

                        return true;

                    case R.id.bottomnav_search:
                        if (!(currentBottomNavFragment instanceof FragmentAdminUsers)) {
                            if (mFragmentAdminListUsers != null) {
                                getSupportFragmentManager().beginTransaction()
                                        .show(mFragmentAdminListUsers)
                                        .hide(currentBottomNavFragment)
                                        .commit();
                                currentBottomNavFragment = mFragmentAdminListUsers;
                            } else {
                                mFragmentAdminListUsers = new FragmentAdminUsers();
                                getSupportFragmentManager().beginTransaction()
                                        .add(R.id.activityadmin_main_fragment_container, mFragmentAdminListUsers, Constants.FRAGMENT_ADMIN_LIST_OF_USERS)
                                        .hide(currentBottomNavFragment)
                                        .commit();
                                currentBottomNavFragment = mFragmentAdminListUsers;
                            }
                        }
                        return true;


                    case R.id.bottomnav_profile:
                        if (!(currentBottomNavFragment instanceof FragmentAdminMore)) {
                            if (mFragmentAdminMore != null) {
                                getSupportFragmentManager().beginTransaction()
                                        .show(mFragmentAdminMore)
                                        .hide(currentBottomNavFragment)
                                        .commit();
                                currentBottomNavFragment = mFragmentAdminMore;
                            } else {
                                mFragmentAdminMore = new FragmentAdminMore();
                                getSupportFragmentManager().beginTransaction()
                                        .add(R.id.activityadmin_main_fragment_container, mFragmentAdminMore, Constants.FRAGMENT_ADMIN_MORE)
                                        .hide(currentBottomNavFragment)
                                        .commit();
                                currentBottomNavFragment = mFragmentAdminMore;
                            }
                        }
                        return true;

                }
                return false;
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.d(TAG, "onSaveInstanceState: Saving state");
        getSupportFragmentManager().putFragment(outState, Constants.KEY_INSTANCE_STATE_ADMIN_CURRENT_FRAGMENT, currentBottomNavFragment);

        if (mFragmentAdminHome != null) {
            getSupportFragmentManager().putFragment(outState, Constants.KEY_INSTANCE_STATE_ADMIN_HOME, mFragmentAdminHome);
        }

        if (mFragmentAdminListUsers != null) {
            getSupportFragmentManager().putFragment(outState, Constants.KEY_INSTANCE_STATE_ADMIN_LIST_USERS, mFragmentAdminListUsers);
        }

        if (mFragmentAdminMore != null) {
            getSupportFragmentManager().putFragment(outState, Constants.KEY_INSTANCE_STATE_ADMIN_MORE, mFragmentAdminMore);
        }


    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Log.d(TAG, "onRestoreInstanceState: Restoring state");
        currentBottomNavFragment = getSupportFragmentManager()
                .getFragment(savedInstanceState, Constants.KEY_INSTANCE_STATE_ADMIN_CURRENT_FRAGMENT);

        mFragmentAdminHome = (FragmentAdminHome) getSupportFragmentManager()
                .getFragment(savedInstanceState, Constants.KEY_INSTANCE_STATE_ADMIN_HOME);

        mFragmentAdminListUsers = (FragmentAdminUsers) getSupportFragmentManager()
                .getFragment(savedInstanceState, Constants.KEY_INSTANCE_STATE_ADMIN_LIST_USERS);

        mFragmentAdminMore = (FragmentAdminMore) getSupportFragmentManager()
                .getFragment(savedInstanceState, Constants.KEY_INSTANCE_STATE_ADMIN_MORE);
    }

}
