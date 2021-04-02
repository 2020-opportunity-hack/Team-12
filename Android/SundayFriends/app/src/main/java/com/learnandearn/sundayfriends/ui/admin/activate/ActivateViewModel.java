package com.learnandearn.sundayfriends.ui.admin.activate;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.learnandearn.sundayfriends.network.admin.AdminRepo;
import com.learnandearn.sundayfriends.network.model.UserInfo;

import java.util.List;

public class ActivateViewModel extends AndroidViewModel {

    private AdminRepo                repo;
    private LiveData<List<UserInfo>> deactivatedUsersLiveData;

    public ActivateViewModel(@NonNull Application application) {
        super(application);

        repo = new AdminRepo(application);
        deactivatedUsersLiveData = repo.getDeactivatedUsersLiveData();
    }

    public void getDeactivatedUsers() {
        repo.getDeactivatedUsers();
    }

    public LiveData<List<UserInfo>> getDeactivatedUsersLiveData() {
        return deactivatedUsersLiveData;
    }
}
