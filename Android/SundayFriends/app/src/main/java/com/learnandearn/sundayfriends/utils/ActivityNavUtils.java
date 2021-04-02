package com.learnandearn.sundayfriends.utils;

import android.app.Activity;
import android.content.Intent;

import com.learnandearn.sundayfriends.ui.admin.AdminActivity;
import com.learnandearn.sundayfriends.ui.login.LoginActivity;
import com.learnandearn.sundayfriends.ui.user.UserActivity;

public class ActivityNavUtils {
    public static void initLoginActivity(Activity activity) {
        Intent i = new Intent(activity, LoginActivity.class);
        activity.startActivity(i);
        activity.finish();
    }

    public static void initUserActivity(Activity activity) {
        Intent i = new Intent(activity, UserActivity.class);
        activity.startActivity(i);
        activity.finish();
    }

    public static void initAdminActivity(Activity activity) {
        Intent i = new Intent(activity, AdminActivity.class);
        activity.startActivity(i);
        activity.finish();
    }
}
