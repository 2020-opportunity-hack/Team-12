package com.example.sundayfriendshack;

public class Constants {

    /**
     * Fragments for LoginActivity
     */

    public static String FRAGMENT_LOGIN = "FRAGMENT_LOGIN";
    public static String FAMILY_ID_FRAGMENT = "FAMILY_ID_FRAGMENT";

    /**
     * Fragments for MainActivity
     */
    public static String FRAGMENT_USER_HOME = "FRAGMENT_USER_HOME";
    public static String FRAGMENT_USER_FAMILY_MEMBERS = "FRAGMENT_USER_FAMILY_MEMBERS";
    public static String FRAGMENT_USER_PROFILE = "FRAGMENT_USER_PROFILE";

    public static String KEY_INSTANCE_STATE_USER_CURRENT_FRAGMENT = "fragment_user_current_fragment";
    public static String KEY_INSTANCE_STATE_USER_HOME = "fragment_user_home";
    public static String KEY_INSTANCE_STATE_USER_FAMILY_MEMBERS = "fragment_user_list_family_members";
    public static String KEY_INSTANCE_STATE_USER_PROFILE = "fragment_user_profile";

    /*
     * Fragments for AdminActivity
     */
    public static String FRAGMENT_ADMIN_HOME = "FRAGMENT_ADMIN_HOME";
    public static String FRAGMENT_ADMIN_LIST_OF_USERS = "FRAGMENT_ADMIN_LIST_OF_USERS";
    public static String FRAGMENT_ADMIN_MORE = "FRAGMENT_ADMIN_MORE";

    public static String FRAGMENT_ADMIN_USER_WITHDRAW = "FRAGMENT_ADMIN_USER_WITHDRAW";
    public static String FRAGMENT_ADMIN_USER_DEPOSIT = "FRAGMENT_ADMIN_USER_DEPOSIT";

    public static String KEY_FRAGMENT_WITHDRAW_NAME = "user_full_name";
    public static String KEY_FRAGMENT_DEPOSIT_NAME = "user_full_name";
    public static String KEY_FRAGMENT_USER_ID = "user_id";

    public static String KEY_INSTANCE_STATE_ADMIN_CURRENT_FRAGMENT = "fragment_admin_current_fragment";
    public static String KEY_INSTANCE_STATE_ADMIN_HOME = "fragment_admin_home";
    public static String KEY_INSTANCE_STATE_ADMIN_LIST_USERS = "fragment_admin_list_users";
    public static String KEY_INSTANCE_STATE_ADMIN_MORE = "fragment_admin_more";



    /**
     * Fragments for Both Main and Admin Activity
     */
    public static String FRAGMENT_USER_TRANSACTIONS = "FRAGMENT_USER_TRANSACTIONS";
    public static String KEY_USER_TRANSACTIONS_USER_ID = "user_id";
    public static String KEY_USER_TRANSACTIONS_USER_NAME = "family_member_name";



    /**
     * Request Codes
     */
    public final static int RC_SIGN_IN = 1111;

    public static String APP_NAME = "Sunday Friends";

    public static String SUNDAY_FRIENDS_ENDPOINT = "reddit.com";
}
