package com.learnandearn.sundayfriends.ui.admin.linkfamily;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.learnandearn.sundayfriends.network.admin.AdminRepo;
import com.learnandearn.sundayfriends.network.model.UserInfo;

import java.util.List;

public class LinkFamilyViewModel extends AndroidViewModel {

    private AdminRepo                repo;
    private LiveData<List<UserInfo>> usersToLinkLiveData;

    public LinkFamilyViewModel(@NonNull Application application) {
        super(application);

        repo = new AdminRepo(application);
        usersToLinkLiveData = repo.getLinkUsersLiveData();
    }

    public void getUsersToLink(){
        repo.getUsersToLink();
    }


    public LiveData<List<UserInfo>> getUsersToLinkLiveData() {
        return usersToLinkLiveData;
    }

}
