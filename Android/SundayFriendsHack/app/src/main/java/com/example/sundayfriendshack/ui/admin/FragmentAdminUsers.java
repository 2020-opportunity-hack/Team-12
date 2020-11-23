package com.example.sundayfriendshack.ui.admin;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.sundayfriendshack.Constants;
import com.example.sundayfriendshack.R;
import com.example.sundayfriendshack.adapters.AdminListUsersAdapter;
import com.example.sundayfriendshack.divider.PostDivider;
import com.example.sundayfriendshack.manager.ToastManager;
import com.example.sundayfriendshack.model.UserInfo;
import com.example.sundayfriendshack.repo.AdminRepo.AdminContract;
import com.example.sundayfriendshack.repo.AdminRepo.AdminRepo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

public class FragmentAdminUsers extends Fragment implements
        AdminContract.Model.onGetListUsers,
        AdminContract.Model.onDeactivateUser{

    private static final String TAG = "FragmentAdminUsers";

    private Context mContext;

    @BindView(R.id.fragadminlistusers_recycler) RecyclerView mRecycler;

    @BindView(R.id.fragadminlistusers_swiperefreshlayout) SwipeRefreshLayout mSwipeRefreshLayout;

    private AdminListUsersAdapter mListUsersAdapter;
    private ArrayList<UserInfo> mListData = new ArrayList<>();
    private AdminRepo mRepo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        mListUsersAdapter = new AdminListUsersAdapter(mContext, mListData);
        mRepo = new AdminRepo(mContext);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_list_of_users, container, false);
        ButterKnife.bind(this, view);


        mRepo.getListOfUsers();
        /**
         * for(int i = 0; i < 10; i++){
         *             UserInfo userInfo = new UserInfo();
         *             userInfo.setName(String.valueOf(i));
         *             userInfo.setEmail("Test Email");
         *             userInfo.setImageUrl("https://i.imgur.com/njXF4IH.jpg");
         *             userInfo.setBalance(1111);
         *             mListData.add(userInfo);
         *         }
         */


        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycler.setAdapter(mListUsersAdapter);

        initSwipeRefreshLayout();

        return view;
    }

    public void deactivateUser(int userId){
        Log.d(TAG, "deactivateUser: Calling..." + userId);
        mRepo.deactivateUser(userId);
    }

    private void initSwipeRefreshLayout(){
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorWhite));
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.standardBlue);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRepo.getListOfUsers();
            }
        });
    }

    @Override
    public void onSuccessGetListUsers(ArrayList<UserInfo> listUsers) {
        Log.d(TAG, "onSuccessGetListUsers: Success Get Users");
        mSwipeRefreshLayout.setRefreshing(false);
        try {
            mListData.clear();
            for(int i = 0; i < listUsers.size(); i++){
                if(listUsers.get(i).isActive() == Constants.ACTIVE_USER){
                    mListData.add(listUsers.get(i));
                }
            }
            mListUsersAdapter.notifyDataSetChanged();
        }catch (Exception e){
            ToastManager.somethingWentWrongError(mContext, e);
        }

    }

    @Override
    public void onFailedGetListUsers(Throwable t) {
        Log.d(TAG, "onFailedGetListUsers: Failed get users");
        mSwipeRefreshLayout.setRefreshing(false);
        ToastManager.displayNetworkError(mContext, t);
    }

    @Override
    public void onSuccessDeactivateUser(Response<Void> response) {
        if(response.code() == Constants.SUCCESS_CODE){
            Toast.makeText(mContext, "Successfully deactivated user. ", Toast.LENGTH_SHORT).show();
            //Remove user from list, notify Item removed
        }else{
            Toast.makeText(mContext, "Something went wrong with deactivating user. " + response.code(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailedDeactivateUser(Throwable t) {
        ToastManager.displayNetworkError(mContext, t);
    }
}
