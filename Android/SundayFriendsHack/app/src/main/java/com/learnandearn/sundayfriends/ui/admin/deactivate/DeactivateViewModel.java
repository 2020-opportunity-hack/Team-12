package com.learnandearn.sundayfriends.ui.admin.deactivate;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.learnandearn.sundayfriends.network.admin.AdminRepo;
import com.learnandearn.sundayfriends.network.model.UserInfo;

import java.util.List;

public class DeactivateViewModel extends AndroidViewModel {

    private AdminRepo                repo;
    private LiveData<List<UserInfo>> activatedUsersLiveData;

    public DeactivateViewModel(@NonNull Application application) {
        super(application);

        repo = new AdminRepo(application);
        activatedUsersLiveData = repo.getActivatedUsersLiveData();
    }

    public void getActivatedUsers() {
        repo.getActivatedUsers();
    }

    public LiveData<List<UserInfo>> getActivatedUsersLiveData() {
        return activatedUsersLiveData;
    }
}
