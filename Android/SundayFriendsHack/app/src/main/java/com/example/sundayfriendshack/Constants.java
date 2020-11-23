package com.example.sundayfriendshack;

public class Constants {

    /**
     * Fragments for LoginActivity
     */

    public static String FRAGMENT_LOGIN = "FRAGMENT_LOGIN";
    public static String FAMILY_ID_FRAGMENT = "FAMILY_ID_FRAGMENT";

    public static String KEY_START_HOMEPAGE_USER_ID = "user_id";
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
    public static String FRAGMENT_DEACTIVATED_USERS = "FRAGMENT_DEACTIVATED_USERS";

    public static String KEY_FRAGMENT_WITHDRAW_NAME = "user_full_name";
    public static String KEY_FRAGMENT_DEPOSIT_NAME = "user_full_name";
    public static String KEY_FRAGMENT_USER_ID = "user_id";

    public static String KEY_INSTANCE_STATE_ADMIN_CURRENT_FRAGMENT = "fragment_admin_current_fragment";
    public static String KEY_INSTANCE_STATE_ADMIN_HOME = "fragment_admin_home";
    public static String KEY_INSTANCE_STATE_ADMIN_LIST_USERS = "fragment_admin_list_users";
    public static String KEY_INSTANCE_STATE_ADMIN_MORE = "fragment_admin_more";

    public static int TICKET_ACTION_TYPE_WITHDRAW = 0;
    public static int TICKET_ACTION_TYPE_DEPOSIT = 1;

    public static boolean TICKET_ACTION_TYPE_BOOLEAN_DEPOSIT = true;
    public static boolean TICKET_ACTION_TYPE_BOOLEAN_WITHDRAW = false;

    public static boolean ACTIVE_USER = false;
    public static boolean DISABLED_USER = true;


    /**
     * Fragments for Both Main and Admin Activity
     */
    public static String FRAGMENT_USER_TRANSACTIONS = "FRAGMENT_USER_TRANSACTIONS";
    public static String KEY_USER_TRANSACTIONS_USER_ID = "user_id";
    public static String KEY_USER_TRANSACTIONS_USER_NAME = "family_member_name";

    /**
     * Shared pref values
     */

    public static String KEY_SHARED_PREF_NAME = "user_name";
    public static String KEY_SHARED_PREF_EMAIL = "user_email";
    public static String KEY_SHARED_PREF_PROFILE_PIC = "user_profile_pic";
    public static String KEY_SHARED_PREF_USER_ID = "user_id";
    public static String KEY_SHARED_PREF_FAMILY_ID = "family_id";



    /**
     * Request Codes
     */
    public final static int RC_SIGN_IN = 1111;

    public final static int SUCCESS_CODE = 200;

    public static String APP_NAME = "Sunday Friends";

    public static String SUNDAY_FRIENDS_ENDPOINT = "http://34.240.219.132:8080/";


}
