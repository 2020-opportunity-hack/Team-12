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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sundayfriendshack.R;
import com.example.sundayfriendshack.adapters.ActivateUserAdapter;
import com.example.sundayfriendshack.divider.SimpleDivider;
import com.example.sundayfriendshack.manager.ToastManager;
import com.example.sundayfriendshack.model.UserInfo;
import com.example.sundayfriendshack.repo.AdminRepo.AdminContract;
import com.example.sundayfriendshack.repo.AdminRepo.AdminRepo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentDeactivatedUsers extends DialogFragment implements AdminContract.Model.onGetDeactivatedUsers{

    @BindView(R.id.frag_deactivatedusers_recycler) RecyclerView mRecycler;

    private Context mContext;

    private ActivateUserAdapter adapter;
    private ArrayList<UserInfo> mList = new ArrayList<>();

    private static final String TAG = "FragmentDeactivatedUser";



    private AdminRepo mRepo;

    public static FragmentDeactivatedUsers newInstance(){
        return new FragmentDeactivatedUsers();
    }

    @Override
    public void onActivityCreated(Bundle arg0) {
        super.onActivityCreated(arg0);
        //getDialog().getWindow().getAttributes().windowAnimations = R.style.FragmentDialogAnim;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();

        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme);

        mRepo = new AdminRepo(mContext);
        adapter = new ActivateUserAdapter(mContext, mList);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_deactivated_users, container, false);
        ButterKnife.bind(this, view);

        mRepo.getListOfDeactivatedUsers();

        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        SimpleDivider simpleDivider = new SimpleDivider(ContextCompat.getDrawable(mContext, R.drawable.divider_simple_line));
        mRecycler.addItemDecoration(simpleDivider);
        mRecycler.setAdapter(adapter);

        return view;
    }

    public void activateUser(int userId){
        //Activate user
        Log.d(TAG, "activateUser: Activating User: " + userId);
    }



    @OnClick(R.id.frag_deactivatedusers_back_btn)
    public void onBackBtnClick(){
        this.dismiss();
    }


    @Override
    public void onSuccesGetDeactivatedUsers(ArrayList<UserInfo> list) {
        try {
            mList.addAll(list);
            adapter.notifyDataSetChanged();
        }catch (Exception e){
            Toast.makeText(mContext, "Sorry something went wrong getting deactivated users. " + e, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailedGetDeactivatedUsers(Throwable t) {
        ToastManager.displayNetworkError(mContext, t);
    }
}
