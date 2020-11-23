package com.example.sundayfriendshack.ui.user;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sundayfriendshack.Constants;
import com.example.sundayfriendshack.R;
import com.example.sundayfriendshack.adapters.UserFamilyMembersAdapter;
import com.example.sundayfriendshack.divider.SimpleDivider;
import com.example.sundayfriendshack.manager.SharedPrefManager;
import com.example.sundayfriendshack.manager.ToastManager;
import com.example.sundayfriendshack.model.FamilyMemberDto;
import com.example.sundayfriendshack.repo.UserRepo.UserContract;
import com.example.sundayfriendshack.repo.UserRepo.UserRepo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentUserFamilyMembers extends Fragment implements UserContract.Model.onGetFamilyMembers {

    @BindView(R.id.fragfamilymembers_recycler) RecyclerView mRecycler;

    private Context mContext;


    private UserFamilyMembersAdapter familyMembersAdapter;
    private ArrayList<FamilyMemberDto> mListFamilyMembers = new ArrayList<>();
    private UserRepo mRepo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();

        mRepo = new UserRepo(mContext);
        familyMembersAdapter = new UserFamilyMembersAdapter(mContext, mListFamilyMembers);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_family_members, container, false);
        ButterKnife.bind(this, view);

        /**
         * for(int i = 0; i < 15; i++){
         *             FamilyMemberDto familyMemberDto = new FamilyMemberDto();
         *             familyMemberDto.setImageUrl("https://i.imgur.com/vjgnwL0.jpg");
         *             familyMemberDto.setName("Alice Smith");
         *             familyMemberDto.setBalance(50);
         *             mListFamilyMembers.add(familyMemberDto);
         *         }
         */


         mRepo = new UserRepo(mContext);
         mRepo.getFamilyMembers(SharedPrefManager.getInstance(mContext).getFamilyId());


        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        SimpleDivider divider = new SimpleDivider(
                ContextCompat.getDrawable(mContext, R.drawable.divider_simple_line)
        );
        mRecycler.addItemDecoration(divider);
        mRecycler.setAdapter(familyMembersAdapter);

        return view;
    }

    @Override
    public void onSuccessGetFamilyMembers(ArrayList<FamilyMemberDto> familyMemberDtos) {
        try {
            mListFamilyMembers.addAll(familyMemberDtos);
            familyMembersAdapter.notifyDataSetChanged();
        }catch (Exception e){
            ToastManager.somethingWentWrongError(mContext, e);
        }

    }

    @Override
    public void onFailedGetFamilyMembers(Throwable t) {
        ToastManager.displayNetworkError(mContext, t);
    }
}
