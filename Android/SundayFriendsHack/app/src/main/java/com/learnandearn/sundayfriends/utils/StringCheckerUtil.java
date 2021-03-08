package com.learnandearn.sundayfriends.utils;

import android.text.TextUtils;

public class StringCheckerUtil {
    public static boolean isInputANumber(String s){
        return TextUtils.isDigitsOnly(s);
    }

    public static boolean isInputEmpty(String s){
        return s.equals("");
    }

    public static boolean isNumberGreaterThanZero(String s){
        return Integer.valueOf(s) > 0;
    }
}
