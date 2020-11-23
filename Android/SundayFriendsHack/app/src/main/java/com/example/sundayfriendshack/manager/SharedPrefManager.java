package com.example.sundayfriendshack.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.sundayfriendshack.Constants;

import static android.content.Context.MODE_PRIVATE;

public class SharedPrefManager {
    private static SharedPrefManager sharedPrefManager = new SharedPrefManager();
    private static SharedPreferences mSharedPreferences;
    private static SharedPreferences.Editor mEditor;
    private static final String SHARED_PREFS = "SUNDAY_FRIENDS_APP";

    private SharedPrefManager() {}

    public static SharedPrefManager getInstance(Context context){
        if(mSharedPreferences == null){
            mSharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            mEditor = mSharedPreferences.edit();
        }
        return sharedPrefManager;
    }

    /**
     * userId
     */
    public void setUserId(int userId){
        mEditor.putInt(Constants.KEY_FRAGMENT_USER_ID, userId);
        mEditor.commit();
    }

    public Integer getUserId(){
        return mSharedPreferences.getInt(Constants.KEY_SHARED_PREF_USER_ID, -1);
    }


    /**
     * name
     */
    public void setUserName(String userName){
        mEditor.putString(Constants.KEY_SHARED_PREF_NAME, userName);
        mEditor.commit();
    }

    public String getUserName(){
        return mSharedPreferences.getString(Constants.KEY_SHARED_PREF_NAME, "null_name");
    }


    /**
     * email
     */
    public void setUserEmail(String email){
        mEditor.putString(Constants.KEY_SHARED_PREF_EMAIL, email);
        mEditor.commit();
    }

    public String getUserEmail(){
        return mSharedPreferences.getString(Constants.KEY_SHARED_PREF_EMAIL, "null_email");
    }

    /**
     * profilePic
     */
    public void setUserProfilePic(String profilePic){
        mEditor.putString(Constants.KEY_SHARED_PREF_PROFILE_PIC, profilePic);
        mEditor.commit();
    }

    public String getProfilePic(){
        return mSharedPreferences.getString(Constants.KEY_SHARED_PREF_PROFILE_PIC, null);
    }

    /**
     * Family Id
     */

    public void setFamilyId(int familyId){
        mEditor.putInt(Constants.KEY_SHARED_PREF_FAMILY_ID, familyId);
        mEditor.commit();
    }

    public int getFamilyId(){
        return mSharedPreferences.getInt(Constants.KEY_SHARED_PREF_FAMILY_ID, 0);
    }








}
