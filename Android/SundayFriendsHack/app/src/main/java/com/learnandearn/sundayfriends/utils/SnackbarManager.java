package com.learnandearn.sundayfriends.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import com.learnandearn.sundayfriends.R;
import com.learnandearn.sundayfriends.ui.admin.dialog.DialogType;
import com.google.android.material.snackbar.Snackbar;

public class SnackbarManager {
    public static Snackbar unexpectedError(Context context, ViewGroup parentLayout){
        String msg = context.getString(R.string.app_name) + " is currently unavailable.";
        return Snackbar
                .make(parentLayout, msg, Snackbar.LENGTH_INDEFINITE)
                .setAction("Dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .setActionTextColor(context.getResources().getColor(R.color.colorWhite));
    }

    public static Snackbar successDialog(View anchorView, DialogType dialogType){
        String msg = "Success ";
        switch (dialogType){
            case ACTIVATE_USER:
                msg += "activating user.";
                break;

            case DEACTIVATE_USER:
                msg += "deactivating user.";
                break;

            case LINK_FAMILY:
                msg += "success linking family.";
                break;

            case WITHDRAW:
                msg += "withdrawing tickets.";
                break;

            case DEPOSIT:
                msg += "depositing tickets.";
                break;

        }

        Snackbar snackbar = Snackbar.make(anchorView, msg, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        snackbar.setAnchorView(anchorView);
        snackbar.setActionTextColor(Color.WHITE);
        return snackbar;
    }

    public static Snackbar failedDialog(View anchorView, DialogType dialogType){
        String msg = "Failed ";
        switch (dialogType){
            case ACTIVATE_USER:
                msg += "activating user.";
                break;

            case DEACTIVATE_USER:
                msg += "deactivating user.";
                break;

            case LINK_FAMILY:
                msg += "success linking family.";
                break;

            case WITHDRAW:
                msg += "withdrawing tickets.";
                break;

            case DEPOSIT:
                msg += "depositing tickets.";
                break;

        }

        Snackbar snackbar = Snackbar.make(anchorView, msg, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        snackbar.setAnchorView(anchorView);
        snackbar.setActionTextColor(Color.WHITE);
        return snackbar;
    }

    //Network call failed
    //No internet connection or server is down
    /**
     * Displays above bottomNavigation which is the anchor
     * @param anchorView
     * @return
     */
    public static Snackbar networkCallFailed(View anchorView){
        String msg = "Whoops something went wrong!";
        Snackbar snackbar = Snackbar.make(anchorView, msg, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        snackbar.setAnchorView(anchorView);
        snackbar.setActionTextColor(Color.WHITE);
        return snackbar;
    }

    public static Snackbar unactivatedUser(ViewGroup parentLayout){
        String msg = "User is not activated.";
        return Snackbar
                .make(parentLayout, msg, Snackbar.LENGTH_INDEFINITE)
                .setAction("Dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .setActionTextColor(Color.WHITE);
    }


}
