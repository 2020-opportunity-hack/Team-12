package com.example.sundayfriendshack.ui.menu;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.example.sundayfriendshack.R;
import com.example.sundayfriendshack.ui.admin.AdminActivity;
import com.example.sundayfriendshack.ui.user.MainActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeactivateMenu extends PopupWindow {

    private Context mContext;

    private AdminActivity adminActivity;

    private int mUserId;
    public DeactivateMenu(Context context, int userId) {
        super(context);
        this.mContext = context;
        this.mUserId = userId;
        adminActivity = (AdminActivity) context;



        setupView();
    }

    private void setupView(){
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.popupmenu_deactivate, null);
        ButterKnife.bind(this, view);

        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setOutsideTouchable(true);
        setFocusable(true);

        //Need set windowlayout for API 19 otherwise window won't appear
        //setWindowLayoutMode(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        view.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(view.getMeasuredHeight());

        setContentView(view);
    }

    @OnClick(R.id.popupmenu_deactivate_user_btn)
    public void onSignOutBtnClick(){
        dismiss();
        adminActivity.deactivateUser(mUserId);
    }


}
