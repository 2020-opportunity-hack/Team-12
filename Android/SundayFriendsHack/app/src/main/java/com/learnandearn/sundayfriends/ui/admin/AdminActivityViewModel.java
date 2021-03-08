package com.learnandearn.sundayfriends.ui.admin;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.learnandearn.sundayfriends.network.admin.AdminRepo;
import com.learnandearn.sundayfriends.network.model.ResponseCode;

public class AdminActivityViewModel extends AndroidViewModel {

    private AdminRepo repo;

    private MutableLiveData<ResponseCode> activateLiveData;
    private MutableLiveData<ResponseCode> deactivateLiveData;
    private MutableLiveData<ResponseCode> withdrawLiveData;
    private MutableLiveData<ResponseCode> depositLiveData;
    private MutableLiveData<ResponseCode> linkFamilyLiveData;


    public AdminActivityViewModel(@NonNull Application application) {
        super(application);

        repo = new AdminRepo(application);

        activateLiveData = repo.getActivateLiveData();
        deactivateLiveData = repo.getDeactivateLiveData();
        withdrawLiveData = repo.getWithdrawLiveData();
        depositLiveData = repo.getDepositLiveData();
        linkFamilyLiveData = repo.getLinkLiveData();
    }



    public void activateUser(int userId, boolean activate) {
        repo.activateUser(userId, activate);
    }

    public void deactivateUser(int userId, boolean deactivate) {
        repo.deactivateUser(userId, deactivate);
    }

    public void withdrawTickets(int userId, int amountToWithdraw, int actionType) {
        repo.withdrawTicket(userId, amountToWithdraw, actionType);
    }

    public void depositTickets(int userId, int amountToDeposit, int actionType) {
        repo.depositTicket(userId, amountToDeposit, actionType);
    }

    public void linkFamilyToUser(int userId, int familyId) {
        repo.linkFamilyToUser(userId, familyId);
    }


    //Getters

    public MutableLiveData<ResponseCode> getActivateLiveData() {
        return activateLiveData;
    }

    public MutableLiveData<ResponseCode> getDeactivateLiveData() {
        return deactivateLiveData;
    }

    public MutableLiveData<ResponseCode> getWithdrawLiveData() {
        return withdrawLiveData;
    }

    public MutableLiveData<ResponseCode> getDepositLiveData() {
        return depositLiveData;
    }

    public MutableLiveData<ResponseCode> getLinkFamilyLiveData() {
        return linkFamilyLiveData;
    }
}
