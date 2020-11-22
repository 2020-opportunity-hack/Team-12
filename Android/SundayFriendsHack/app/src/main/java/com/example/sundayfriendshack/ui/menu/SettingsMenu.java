package com.example.sundayfriendshack.ui.menu;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.example.sundayfriendshack.R;
import com.example.sundayfriendshack.ui.admin.AdminActivity;
import com.example.sundayfriendshack.ui.user.MainActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SettingsMenu extends PopupWindow {

    private Context mContext;

    private AdminActivity adminActivity;
    private MainActivity userActivity;

    public SettingsMenu(Context context) {
        super(context);
        this.mContext = context;

        if(mContext instanceof MainActivity){
            userActivity = (MainActivity) context;
        }else if(context instanceof AdminActivity){
            adminActivity = (AdminActivity) context;
        }


        setupView();
    }

    private void setupView(){
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.popupmenu_settings, null);
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

    @OnClick(R.id.popupmenu_settings_signout)
    public void onSignOutBtnClick(){
        dismiss();
        if(mContext instanceof MainActivity){
            userActivity.signOutUser();
        }else if(mContext instanceof AdminActivity){
            adminActivity.signOutUser();
        }
    }


}
