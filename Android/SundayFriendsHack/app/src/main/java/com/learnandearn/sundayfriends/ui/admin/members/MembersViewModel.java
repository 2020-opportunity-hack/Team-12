package com.learnandearn.sundayfriends.ui.admin.members;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.learnandearn.sundayfriends.network.admin.AdminRepo;
import com.learnandearn.sundayfriends.network.model.UserInfo;

import java.util.List;

public class MembersViewModel extends AndroidViewModel {

    private AdminRepo                repo;
    private LiveData<List<UserInfo>> firstPageLiveData;
    private LiveData<List<UserInfo>> nextPageLiveData;
    private LiveData<List<UserInfo>> searchUserLiveData;

    public MembersViewModel(@NonNull Application application) {
        super(application);

        repo = new AdminRepo(application);
        firstPageLiveData = repo.getFirstPageLiveData();
        nextPageLiveData = repo.getNextPageLiveData();
        searchUserLiveData = repo.getSearchUserLiveData();
    }


    public void getFirstPageMembers(int offset, int limit) {
        repo.getFirstPageMembers(offset, limit);
    }

    public void getNextPageMembers(int offset, int limit) {
        repo.getNextPageMembers(offset, limit);
    }

    public void searchForUser(String email) {
        repo.searchUsers(email);
    }

    public LiveData<List<UserInfo>> getFirstPageLiveData() {
        return firstPageLiveData;
    }

    public LiveData<List<UserInfo>> getNextPageLiveData() {
        return nextPageLiveData;
    }

    public LiveData<List<UserInfo>> getSearchUserLiveData() {
        return searchUserLiveData;
    }
}
