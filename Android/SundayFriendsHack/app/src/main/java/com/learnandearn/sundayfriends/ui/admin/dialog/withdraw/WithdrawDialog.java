package com.learnandearn.sundayfriends.ui.admin.dialog.withdraw;

import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.learnandearn.sundayfriends.Constants;
import com.learnandearn.sundayfriends.R;
import com.learnandearn.sundayfriends.network.model.UserInfo;
import com.learnandearn.sundayfriends.utils.StringCheckerUtil;
import com.learnandearn.sundayfriends.utils.ToastManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class WithdrawDialog extends AlertDialog {

    @BindView(R.id.title)
    TextView tvTitle;

    @BindView(R.id.edit_text_amount)
    EditText etAmount;

    private UserInfo              userInfo;
    private AdminWithdrawListener btnCallback;

    public WithdrawDialog(@NonNull Context context, UserInfo userInfo) {
        super(context);
        this.userInfo = userInfo;

        try {
            btnCallback = (AdminWithdrawListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement callback. " + e);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_withdraw);
        ButterKnife.bind(this);

        //Both flags to display softkeyboard in AlertDialog
        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        setTitle(userInfo.getName());

    }

    private void setTitle(String name) {
        String msg = "Withdraw tickets from: " + name;
        tvTitle.setText(msg);
    }

    @OnClick(R.id.btn_cancel)
    public void onBtnDismiss() {
        this.dismiss();
    }

    @OnClick(R.id.btn_withdraw)
    void onBtnWithdraw() {
        String input = etAmount.getText().toString().trim();
        if (StringCheckerUtil.isInputANumber(input) && !StringCheckerUtil.isInputEmpty(input)) {
            if (StringCheckerUtil.isNumberGreaterThanZero(input)) {
                btnCallback.onConfirmWithdrawClick(
                        userInfo.getUserId(),
                        Integer.parseInt(input),
                        Constants.TICKET_ACTION_TYPE_WITHDRAW
                );
                this.dismiss();
            } else {
                ToastManager.invalidNumber(getContext()).show();
            }
        } else {
            ToastManager.invalidNumberString(getContext()).show();
        }
    }

}
