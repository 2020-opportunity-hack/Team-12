package com.learnandearn.sundayfriends.ui.admin;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.learnandearn.sundayfriends.BaseApplication;
import com.learnandearn.sundayfriends.Constants;
import com.learnandearn.sundayfriends.R;
import com.learnandearn.sundayfriends.network.model.ResponseCode;
import com.learnandearn.sundayfriends.network.model.UserInfo;
import com.learnandearn.sundayfriends.ui.admin.dialog.DialogType;
import com.learnandearn.sundayfriends.ui.admin.dialog.activate.ActivateListener;
import com.learnandearn.sundayfriends.ui.admin.dialog.activate.ActivateUserDialog;
import com.learnandearn.sundayfriends.ui.admin.dialog.deactivate.DeactivateListener;
import com.learnandearn.sundayfriends.ui.admin.dialog.deactivate.DeactivateUserDialog;
import com.learnandearn.sundayfriends.ui.admin.dialog.deposit.AdminDepositListener;
import com.learnandearn.sundayfriends.ui.admin.dialog.deposit.DepositDialog;
import com.learnandearn.sundayfriends.ui.admin.dialog.linkfamily.LinkFamilyDialog;
import com.learnandearn.sundayfriends.ui.admin.dialog.linkfamily.LinkFamilyListener;
import com.learnandearn.sundayfriends.ui.admin.dialog.withdraw.AdminWithdrawListener;
import com.learnandearn.sundayfriends.ui.admin.dialog.withdraw.WithdrawDialog;
import com.learnandearn.sundayfriends.ui.admin.home.AdminDashBoardBtnClickListener;
import com.learnandearn.sundayfriends.ui.admin.home.AdminHomeFragment;
import com.learnandearn.sundayfriends.ui.admin.linkfamily.LinkFamilyFragment;
import com.learnandearn.sundayfriends.ui.admin.members.MembersAdapter;
import com.learnandearn.sundayfriends.ui.admin.members.MembersFragment;
import com.learnandearn.sundayfriends.ui.admin.more.AdminMoreFragment;
import com.learnandearn.sundayfriends.ui.shared.SimpleListUsersAdapter;
import com.learnandearn.sundayfriends.ui.shared.usertransactions.UserTransactionsFragment;
import com.learnandearn.sundayfriends.utils.ActivityNavUtils;
import com.learnandearn.sundayfriends.utils.AuthStateManager;
import com.learnandearn.sundayfriends.utils.DialogResponseHandler;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminActivity extends AppCompatActivity implements
        MembersAdapter.AdminListUsersAdapterCallback,
        SimpleListUsersAdapter.BtnClickAdapterCallback,
        AdminWithdrawListener,
        AdminDepositListener,
        DeactivateListener,
        ActivateListener,
        LinkFamilyListener,
        AdminDashBoardBtnClickListener {

    private static final String TAG = "AdminActivity";

    @BindView(R.id.admin_bottomnavigation)
    BottomNavigationView bottomNavigation;

    @BindView(R.id.parent_layout)
    ViewGroup parentLayout;

    private AdminActivityViewModel viewModel;
    private AuthStateManager       authStateManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        ButterKnife.bind(this);

        authStateManager = ((BaseApplication) getApplicationContext()).getAuthStateManager();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(
                            R.id.admin_fragment_container,
                            new AdminHomeFragment(),
                            Constants.FRAGMENT_ADMIN_HOME
                    )
                    .commit();
        }


        bottomNavigation.setOnNavigationItemSelectedListener(bottomNavListener);
        viewModel = new ViewModelProvider(this).get(AdminActivityViewModel.class);

        setActivateListener();
        setDeactivateListener();
        setWithdrawListener();
        setDepositListener();
        setLinkFamilyListener();

    }

    public void signOutUser() {

        authStateManager.getClient().signOut().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: Successfully Signed out");

                ActivityNavUtils.initLoginActivity(AdminActivity.this);
            }
        });
    }

    private void setActivateListener() {
        viewModel.getActivateLiveData().observe(this, new Observer<ResponseCode>() {
            @Override
            public void onChanged(ResponseCode responseCode) {
                DialogResponseHandler.displaySnackbar(
                        bottomNavigation,
                        responseCode,
                        DialogType.ACTIVATE_USER
                );
            }
        });
    }

    private void setDeactivateListener() {
        viewModel.getDeactivateLiveData().observe(this, new Observer<ResponseCode>() {
            @Override
            public void onChanged(ResponseCode responseCode) {
                DialogResponseHandler.displaySnackbar(
                        bottomNavigation,
                        responseCode,
                        DialogType.DEACTIVATE_USER
                );
            }
        });
    }

    private void setWithdrawListener() {
        viewModel.getWithdrawLiveData().observe(this, new Observer<ResponseCode>() {
            @Override
            public void onChanged(ResponseCode responseCode) {
                DialogResponseHandler.displaySnackbar(
                        bottomNavigation,
                        responseCode,
                        DialogType.WITHDRAW
                );
            }
        });
    }

    private void setDepositListener() {
        viewModel.getDepositLiveData().observe(this, new Observer<ResponseCode>() {
            @Override
            public void onChanged(ResponseCode responseCode) {
                DialogResponseHandler.displaySnackbar(
                        bottomNavigation,
                        responseCode,
                        DialogType.DEPOSIT
                );
            }
        });
    }

    private void setLinkFamilyListener() {
        viewModel.getLinkFamilyLiveData().observe(this, new Observer<ResponseCode>() {
            @Override
            public void onChanged(ResponseCode responseCode) {
                DialogResponseHandler.displaySnackbar(
                        bottomNavigation,
                        responseCode,
                        DialogType.LINK_FAMILY
                );
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavListener = item -> {
        switch (item.getItemId()) {
            case R.id.bottomnav_admin_home:
                AdminHomeFragment adminHomeFragment = (AdminHomeFragment) getSupportFragmentManager()
                        .findFragmentByTag(Constants.FRAGMENT_ADMIN_HOME);
                if (adminHomeFragment == null || !adminHomeFragment.isVisible()) {
                    replaceCurrentFragment(new AdminHomeFragment(), Constants.FRAGMENT_ADMIN_HOME);
                }
                return true;

            case R.id.bottomnav_admin_users:
                MembersFragment membersFragment = (MembersFragment) getSupportFragmentManager()
                        .findFragmentByTag(Constants.FRAGMENT_ADMIN_LIST_OF_USERS);
                if (membersFragment == null || !membersFragment.isVisible()) {
                    replaceCurrentFragment(new MembersFragment(), Constants.FRAGMENT_ADMIN_LIST_OF_USERS);
                }
                return true;

            case R.id.bottomnav_admin_more:
                AdminMoreFragment adminMoreFragment = (AdminMoreFragment) getSupportFragmentManager()
                        .findFragmentByTag(Constants.FRAGMENT_ADMIN_MORE);
                if (adminMoreFragment == null || !adminMoreFragment.isVisible()) {
                    replaceCurrentFragment(new AdminMoreFragment(), Constants.FRAGMENT_ADMIN_MORE);
                }
                return true;
        }
        return false;
    };

    @Override
    public void onWithdrawBtnClick(UserInfo userInfo) {
        AlertDialog dialog = new WithdrawDialog(this, userInfo);
        dialog.show();
    }

    @Override
    public void onDepositBtnClick(UserInfo userInfo) {
        AlertDialog dialog = new DepositDialog(this, userInfo);
        dialog.show();
    }

    @Override
    public void onGetTransactionsBtnClick(UserInfo userInfo) {
        DialogFragment transactionHistory = UserTransactionsFragment.newInstance(userInfo);
        transactionHistory.show(getSupportFragmentManager(), Constants.FRAGMENT_USER_TRANSACTIONS);
    }

    @Override
    public void onActivateFragBtnClick(UserInfo userInfo) {
        AlertDialog dialog = new ActivateUserDialog(this, userInfo);
        dialog.show();
    }

    @Override
    public void onDeactivateFragBtnClick(UserInfo userInfo) {
        AlertDialog dialog = new DeactivateUserDialog(this, userInfo);
        dialog.show();
    }

    @Override
    public void onLinkFamilyFragBtnClick(UserInfo userInfo) {
        AlertDialog dialog = new LinkFamilyDialog(this, userInfo);
        dialog.show();
    }

    @Override
    public void onConfirmDepositClick(int userId, int amountToDeposit, int actionType) {
        viewModel.depositTickets(userId, amountToDeposit, actionType);
    }

    @Override
    public void onConfirmWithdrawClick(int userId, int amountToDeposit, int actionType) {
        viewModel.withdrawTickets(userId, amountToDeposit, actionType);
    }

    @Override
    public void onConfirmActivateClick(UserInfo userInfo, boolean activate) {
        viewModel.activateUser(userInfo.getUserId(), activate);

    }

    @Override
    public void onConfirmDeactivateClick(UserInfo userInfo, boolean deactivate) {
        viewModel.deactivateUser(userInfo.getUserId(), deactivate);

    }

    @Override
    public void onConfirmLinkFamilyClick(UserInfo userInfo, int familyIdToLink) {
        viewModel.linkFamilyToUser(userInfo.getUserId(), familyIdToLink);
    }

    @Override
    public void onWithDrawDepositBtnClick() {
        bottomNavigation.setSelectedItemId(R.id.bottomnav_admin_users);
        replaceCurrentFragment(new MembersFragment(), Constants.FRAGMENT_ADMIN_LIST_OF_USERS);
    }

    @Override
    public void onLinkFamilyBtnClick() {
        addFragment(new LinkFamilyFragment(), Constants.FRAGMENT_ADMIN_LINK_FAMILY);
    }

    @Override
    public void onActivateDeactivateBtnClick() {
        bottomNavigation.setSelectedItemId(R.id.bottomnav_admin_more);
        replaceCurrentFragment(new AdminMoreFragment(), Constants.FRAGMENT_ADMIN_MORE);
    }

    private void addFragment(Fragment fragment, String fragmentTag) {
        getSupportFragmentManager().beginTransaction()
                .add(
                        R.id.admin_fragment_container,
                        fragment,
                        fragmentTag
                )
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    private void replaceCurrentFragment(Fragment fragment, String fragmentTag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.admin_fragment_container, fragment, fragmentTag)
                .commit();
    }

    //Go back to homepage on backpressed if it's not on homepage
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            AdminHomeFragment adminHomeFragment = (AdminHomeFragment) getSupportFragmentManager()
                    .findFragmentByTag(Constants.FRAGMENT_ADMIN_HOME);
            if (adminHomeFragment == null || !adminHomeFragment.isVisible()) {
                bottomNavigation.setSelectedItemId(R.id.bottomnav_admin_home);
                replaceCurrentFragment(new AdminHomeFragment(), Constants.FRAGMENT_ADMIN_HOME);
            } else {
                super.onBackPressed();
            }
        }

    }
}
