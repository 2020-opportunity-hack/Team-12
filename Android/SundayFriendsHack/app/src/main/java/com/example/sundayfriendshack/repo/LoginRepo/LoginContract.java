package com.example.sundayfriendshack.repo.LoginRepo;

public class LoginContract {
    public interface Model{
        interface onRegisterUser{
            void onSuccessRegisterUser();
            void onFailedRegisterUser(Throwable t);
        }
    }
}
