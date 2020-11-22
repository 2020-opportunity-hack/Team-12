package com.example.sundayfriendshack.repo.UserRepo;

import com.example.sundayfriendshack.model.FamilyMemberDto;

import java.util.ArrayList;

public class UserContract {
    public interface Model{
        interface onGetFamilyMembers{
            void onSuccessGetFamilyMembers(ArrayList<FamilyMemberDto> familyMemberDtos);
            void onFailedGetFamilyMembers(Throwable t);
        }
    }
}
