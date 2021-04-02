package com.learnandearn.sundayfriends;

public class Constants {


    /**
     * Fragments for UserActivity
     */
    public static final String FRAGMENT_USER_HOME            = "FRAGMENT_USER_HOME";
    public static final String FRAGMENT_USER_FAMILY_MEMBERS  = "FRAGMENT_USER_FAMILY_MEMBERS";
    public static final String FRAGMENT_USER_PROFILE         = "FRAGMENT_USER_PROFILE";
    public static final String FRAGMENT_USER_CHANGE_LANGUAGE = "FRAGMENT_USER_CHANGE_LANGUAGE";


    public static final String KEY_BUNDLE_SERIALIZABLE_USER_INFO = "KEY_BUNDLE_SERIALIZABLE_USER_INFO";

    /*
     * Fragments for AdminActivity
     */
    public static final String FRAGMENT_ADMIN_HOME          = "FRAGMENT_ADMIN_HOME";
    public static final String FRAGMENT_ADMIN_LIST_OF_USERS = "FRAGMENT_ADMIN_LIST_OF_USERS";
    public static final String FRAGMENT_ADMIN_MORE          = "FRAGMENT_ADMIN_MORE";
    public static final String FRAGMENT_ADMIN_LINK_FAMILY   = "FRAGMENT_ADMIN_LINK_FAMILY";

    public static final String FRAGMENT_ADMIN_ACTIVATE_USER   = "FRAGMENT_ADMIN_ACTIVATE_USER";
    public static final String FRAGMENT_ADMIN_DEACTIVATE_USER = "FRAGMENT_ADMIN_DEACTIVATE_USER";

    public static final int TICKET_ACTION_TYPE_WITHDRAW         = 0;
    public static final int TICKET_ACTION_TYPE_DEPOSIT          = 1;
    public static final int TICKET_ACTION_TYPE_INTEREST_DEPOSIT = 2;

    public static final boolean ACTIVATE_USER_FLAG   = false;
    public static final boolean DEACTIVATE_USER_FLAG = true;

    //Fragments for Both Main and Admin Activity
    public static final String FRAGMENT_USER_TRANSACTIONS = "FRAGMENT_USER_TRANSACTIONS";


    //Shared Pref Manager
    public static final String KEY_SHARED_PREF_APP_NAME = "SUNDAY_FRIENDS_APP";

    public static final String KEY_SHARED_PREF_NAME        = "KEY_SHARED_PREF_NAME";
    public static final String KEY_SHARED_PREF_EMAIL       = "KEY_SHARED_PREF_EMAIL";
    public static final String KEY_SHARED_PREF_PROFILE_PIC = "KEY_SHARED_PREF_PROFILE_PIC";
    public static final String KEY_SHARED_PREF_USER_ID     = "KEY_SHARED_PREF_USER_ID";
    public static final String KEY_SHARED_PREF_FAMILY_ID   = "KEY_SHARED_PREF_FAMILY_ID";
    public static final String KEY_SHARED_PREF_BALANCE     = "KEY_SHARED_PREF_BALANCE";
    public static final String KEY_SHARED_PREF_IS_ADMIN    = "KEY_SHARED_PREF_IS_ADMIN";

    public static final String KEY_SHARED_PREF_HEADER_ID_TOKEN  = "KEY_SHARED_PREF_ID_TOKEN";
    public static final String KEY_SHARED_PREF_HEADER_ID_CLIENT = "KEY_SHARED_PREF_ID_CLIENT";

    public static final int VALUE_SHARED_PREF_ADMIN = 1;
    public static final int VALUE_SHARED_PREF_USER  = 2;

    public static final String KEY_SHARED_PREF_LANGUAGE              = "KEY_SHARED_PREF_LANGUAGE";
    public static final String VALUE_SHARED_PREF_LANGUAGE_ENGLISH    = "ENGLISH";
    public static final String VALUE_SHARED_PREF_LANGUAGE_SPANISH    = "SPANISH";
    public static final String VALUE_SHARED_PREF_LANGUAGE_VIETNAMESE = "VIETNAMESE";


    /**
     * Request Codes
     */
    public static final int    REQUEST_CODE_SIGN_IN            = 1111;
    public static final String RETROFIT_HEADER_KEY_ID_TOKEN    = "idToken";
    public static final String RETROFIT_HEADER_KEY_ID_CLIENT   = "idClient";
    public static final String RETROFIT_HEADER_KEY_USER_EMAIL  = "idEmail";
    public static final String USER_TRANSACTION_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String SUNDAY_FRIENDS_ENDPOINT         = "http://184.169.189.74:8080/";
}
