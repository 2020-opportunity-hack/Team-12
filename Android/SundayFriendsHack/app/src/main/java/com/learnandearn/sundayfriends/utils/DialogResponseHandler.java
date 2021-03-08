package com.learnandearn.sundayfriends.utils;

import android.view.View;

import com.learnandearn.sundayfriends.network.model.ResponseCode;
import com.learnandearn.sundayfriends.ui.admin.dialog.DialogType;

public class DialogResponseHandler {
    public static void displaySnackbar(View view, ResponseCode responseCode, DialogType dialogType){
        switch (responseCode){
            case SUCCESS:
                SnackbarManager.successDialog(view, dialogType).show();
                break;

            case UNEXPECTED_ERROR:
                SnackbarManager.failedDialog(view, dialogType).show();
                break;

            case FAILED:
                SnackbarManager.networkCallFailed(view).show();
                break;
        }
    }
}
