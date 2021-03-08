package com.learnandearn.sundayfriends.ui.admin.dialog.activate;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.learnandearn.sundayfriends.Constants;
import com.learnandearn.sundayfriends.R;
import com.learnandearn.sundayfriends.network.model.UserInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ActivateUserDialog extends AlertDialog {

    @BindView(R.id.tv_activate_user)
    TextView tvDialogMsg;

    private UserInfo         userInfo;
    private ActivateListener btnCallback;

    public ActivateUserDialog(@NonNull Context context, UserInfo userInfo) {
        super(context);
        this.userInfo = userInfo;

        try {
            btnCallback = (ActivateListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement Callback. " + e);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_activate);
        ButterKnife.bind(this);

        setDialogMsg();
    }

    private void setDialogMsg(){
        String msg = "Are you sure you want to activate " + userInfo.getName() + " ?";
        tvDialogMsg.setText(msg);
    }


    @OnClick(R.id.btn_cancel)
    public void onBtnDismiss() {
        this.dismiss();
    }

    @OnClick(R.id.btn_confirm)
    public void onBtnConfirm() {
        btnCallback.onConfirmActivateClick(userInfo, Constants.ACTIVATE_USER_FLAG);
        this.dismiss();
    }

}
