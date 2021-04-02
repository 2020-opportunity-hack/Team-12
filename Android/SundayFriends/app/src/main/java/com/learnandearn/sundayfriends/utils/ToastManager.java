package com.learnandearn.sundayfriends.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastManager {

    //Number entered by withdraw and deposit are less than 0
    public static Toast invalidNumber(Context context){
        return Toast.makeText(context, "Please enter a value greater than 0", Toast.LENGTH_SHORT);
    }

    //User did not enter a valid number
    public static Toast invalidNumberString(Context context){
        return Toast.makeText(context, "Please enter a number", Toast.LENGTH_SHORT);
    }

    //End of pagination
    public static Toast endOfUsers(Context context){
        return Toast.makeText(context, "End of users", Toast.LENGTH_SHORT);
    }
}
