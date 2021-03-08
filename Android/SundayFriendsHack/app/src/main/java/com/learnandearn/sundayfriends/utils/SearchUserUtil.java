package com.learnandearn.sundayfriends.utils;

import com.learnandearn.sundayfriends.network.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class SearchUserUtil {
    //Returns new list of searched users
    public static List<UserInfo> filteredList(List<UserInfo> unfilteredList, String input){
        List<UserInfo> newList = new ArrayList<>();
        for(int i = 0; i < unfilteredList.size(); i++){
            String email = unfilteredList.get(i).getEmail();
            if(email.contains(input)){
                newList.add(unfilteredList.get(i));
            }
        }
        return newList;
    }
}
