package com.example.sundayfriendshack.ui.admin;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sundayfriendshack.R;
import com.example.sundayfriendshack.adapters.AdminListUsersAdapter;
import com.example.sundayfriendshack.divider.PostDivider;
import com.example.sundayfriendshack.model.UserInfo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentAdminUsers extends Fragment {

    private Context mContext;

    @BindView(R.id.fragadminlistusers_recycler) RecyclerView mRecycler;

    private AdminListUsersAdapter mListUsersAdapter;
    private ArrayList<UserInfo> mListData = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        mListUsersAdapter = new AdminListUsersAdapter(mContext, mListData);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_list_of_users, container, false);
        ButterKnife.bind(this, view);

        for(int i = 0; i < 10; i++){
            UserInfo userInfo = new UserInfo();
            userInfo.setName(String.valueOf(i));
            userInfo.setEmail("Test Email");
            userInfo.setImageUrl("https://i.imgur.com/njXF4IH.jpg");
            userInfo.setBalance(1111);
            mListData.add(userInfo);
        }

        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycler.setAdapter(mListUsersAdapter);

        return view;
    }
}
