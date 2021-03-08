package com.learnandearn.sundayfriends.ui.user.familymembers;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.learnandearn.sundayfriends.network.model.UserRepo;
import com.learnandearn.sundayfriends.network.model.UserInfo;

import java.util.List;

public class FamilyMembersViewModel extends AndroidViewModel {

    private LiveData<List<UserInfo>> familyMembersLiveData;
    private UserRepo                 repo;

    public FamilyMembersViewModel(@NonNull Application application) {
        super(application);

        repo = new UserRepo(application);
        familyMembersLiveData = repo.getFamilyMembersLiveData();
    }

    public void getFamilyMembers(int familyId) {
        repo.getFamilyMembers(familyId);
    }

    public LiveData<List<UserInfo>> getFamilyMembersLiveData() {
        return familyMembersLiveData;
    }

}
