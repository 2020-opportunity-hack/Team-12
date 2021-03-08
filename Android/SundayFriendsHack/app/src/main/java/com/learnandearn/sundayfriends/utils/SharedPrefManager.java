package com.learnandearn.sundayfriends.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.learnandearn.sundayfriends.Constants;

import static android.content.Context.MODE_PRIVATE;

public class SharedPrefManager {
    private static SharedPrefManager        sharedPrefManager;
    private static SharedPreferences        sharedPreferences;
    private static SharedPreferences.Editor editor;

    private SharedPrefManager() {
    }

    @SuppressLint("CommitPrefEdits")
    public static SharedPrefManager getInstance(Context context) {
        if (sharedPreferences == null) {
            sharedPrefManager = new SharedPrefManager();
            sharedPreferences = context.getSharedPreferences(
                    Constants.KEY_SHARED_PREF_APP_NAME,
                    MODE_PRIVATE
            );
            editor = sharedPreferences.edit();
        }
        return sharedPrefManager;
    }

    /**
     * userId - this id comes from our own server
     */
    public void setUserId(int userId) {
        editor.putInt(Constants.KEY_SHARED_PREF_USER_ID, userId);
        editor.commit();
    }

    public Integer getUserId() {
        return sharedPreferences.getInt(Constants.KEY_SHARED_PREF_USER_ID, -1);
    }


    /**
     * name
     */
    public void setName(String name) {
        editor.putString(Constants.KEY_SHARED_PREF_NAME, name);
        editor.commit();
    }

    public String getName() {
        return sharedPreferences.getString(Constants.KEY_SHARED_PREF_NAME, "null_name");
    }


    /**
     * email
     */
    public void setUserEmail(String email) {
        editor.putString(Constants.KEY_SHARED_PREF_EMAIL, email);
        editor.commit();
    }

    public String getUserEmail() {
        return sharedPreferences.getString(Constants.KEY_SHARED_PREF_EMAIL, "null_email");
    }

    /**
     * profilePic
     */
    public void setUserProfilePic(String profilePic) {
        editor.putString(Constants.KEY_SHARED_PREF_PROFILE_PIC, profilePic);
        editor.commit();
    }

    public String getProfilePic() {
        return sharedPreferences.getString(Constants.KEY_SHARED_PREF_PROFILE_PIC, "null");
    }

    /**
     * Family Id
     */

    public void setFamilyId(int familyId) {
        editor.putInt(Constants.KEY_SHARED_PREF_FAMILY_ID, familyId);
        editor.commit();
    }

    public int getFamilyId() {
        return sharedPreferences.getInt(Constants.KEY_SHARED_PREF_FAMILY_ID, 0);
    }

    /**
     * Balance
     */

    public void setBalance(float balance) {
        editor.putFloat(Constants.KEY_SHARED_PREF_BALANCE, balance);
        editor.commit();
    }

    public float getBalance() {
        return sharedPreferences.getFloat(Constants.KEY_SHARED_PREF_BALANCE, 0f);
    }

    /**
     * Is Admin
     * 0 has never signed in
     * 1 is admin
     * 2 is user
     */

    public void setIsAdmin(int isAdmin) {
        editor.putInt(Constants.KEY_SHARED_PREF_IS_ADMIN, isAdmin);
        editor.commit();
    }

    public int getIsAdmin() {
        return sharedPreferences.getInt(Constants.KEY_SHARED_PREF_IS_ADMIN, 0);
    }

    //Id token

    public void setIdToken(String idToken) {
        editor.putString(Constants.KEY_SHARED_PREF_HEADER_ID_TOKEN, idToken);
        editor.commit();
    }

    public String getIdToken() {
        return sharedPreferences.getString(Constants.KEY_SHARED_PREF_HEADER_ID_TOKEN, "null");
    }

    //Id Client

    public void setIdClient(String idClient) {
        editor.putString(Constants.KEY_SHARED_PREF_HEADER_ID_CLIENT, idClient);
        editor.commit();
    }

    public String getIdClient() {
        return sharedPreferences.getString(Constants.KEY_SHARED_PREF_HEADER_ID_CLIENT, "null");
    }


}
