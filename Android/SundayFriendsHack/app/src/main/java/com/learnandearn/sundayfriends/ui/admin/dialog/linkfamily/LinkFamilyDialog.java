package com.learnandearn.sundayfriends.ui.admin.dialog.linkfamily;

import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.learnandearn.sundayfriends.R;
import com.learnandearn.sundayfriends.network.model.UserInfo;
import com.learnandearn.sundayfriends.utils.StringCheckerUtil;
import com.learnandearn.sundayfriends.utils.ToastManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LinkFamilyDialog extends AlertDialog {

    @BindView(R.id.tv_link_msg)
    TextView tvDialogMsg;

    @BindView(R.id.et_family_id)
    EditText etFamilyId;

    private UserInfo           userInfo;
    private LinkFamilyListener btnCallback;

    public LinkFamilyDialog(@NonNull Context context, UserInfo userInfo) {
        super(context);
        this.userInfo = userInfo;

        try {
            btnCallback = (LinkFamilyListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement Callback. " + e);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_link_family);
        ButterKnife.bind(this);

        //Both flags to display softkeyboard in AlertDialog
        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        setDialogMsg();
    }

    private void setDialogMsg() {
        String msg = "Enter the family id you want to link " + userInfo.getName() + " to";
        tvDialogMsg.setText(msg);
    }


    @OnClick(R.id.btn_cancel)
    public void onBtnDismiss() {
        this.dismiss();
    }

    @OnClick(R.id.btn_confirm)
    void onBtnConfirm() {
        String input = etFamilyId.getText().toString().trim();
        if (StringCheckerUtil.isInputANumber(input) && !StringCheckerUtil.isInputEmpty(input)) {
            btnCallback.onConfirmLinkFamilyClick(userInfo, Integer.parseInt(input));
            this.dismiss();
        } else {
            ToastManager.invalidNumberString(getContext()).show();
        }
    }

}
