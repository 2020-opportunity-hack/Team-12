package com.example.sundayfriendshack.manager;

import android.content.Context;
import android.widget.Toast;

public class ToastManager {
    public static void displayNetworkError(Context context, Throwable t){
        Toast.makeText(context, "Network error. " + t, Toast.LENGTH_SHORT).show();
    }

    public static void somethingWentWrongError(Context context, Throwable t){
        Toast.makeText(context, "Sorry something went wrong. " + t, Toast.LENGTH_SHORT).show();
    }


}
