package com.example.sundayfriendshack.repo.LoginRepo;

import com.example.sundayfriendshack.model.UserInfo;

import retrofit2.Response;

public class LoginContract {
    public interface Model{
        interface onIsUserRegistered{
            void onSuccessGetUser(UserInfo userInfo);
            void onFailedGetUser(Throwable t);
        }

        interface onRegisterFamilyId{
            void onSuccessRegisterFamilyId(Response<Void> response);
            void onFailedRegisterFamilyId(Throwable t);
        }
    }
}
