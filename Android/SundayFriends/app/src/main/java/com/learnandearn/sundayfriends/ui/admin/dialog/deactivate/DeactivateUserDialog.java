package com.learnandearn.sundayfriends.ui.admin.dialog.deactivate;

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


public class DeactivateUserDialog extends AlertDialog {

    @BindView(R.id.tv_deactivate_user)
    TextView tvDialogMsg;

    private UserInfo           userInfo;
    private DeactivateListener btnCallback;

    public DeactivateUserDialog(@NonNull Context context, UserInfo userInfo) {
        super(context);
        this.userInfo = userInfo;

        try {
            btnCallback = (DeactivateListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement Callback. " + e);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_deactivate);
        ButterKnife.bind(this);

        setDialogMsg();

    }

    private void setDialogMsg() {
        String msg = "Are you sure you want to deactivate " + userInfo.getName() + "?";
        tvDialogMsg.setText(msg);
    }


    @OnClick(R.id.btn_cancel)
    public void onBtnDismiss() {
        this.dismiss();
    }

    @OnClick(R.id.btn_confirm)
    public void onBtnConfirm() {
        btnCallback.onConfirmDeactivateClick(userInfo, Constants.DEACTIVATE_USER_FLAG);
        this.dismiss();
    }

}
