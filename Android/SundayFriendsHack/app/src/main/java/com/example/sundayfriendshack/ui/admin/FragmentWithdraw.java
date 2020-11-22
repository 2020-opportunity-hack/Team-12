package com.example.sundayfriendshack.ui.admin;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.sundayfriendshack.Constants;
import com.example.sundayfriendshack.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentWithdraw extends DialogFragment {

    private Context mContext;

    @BindView(R.id.frag_adminwithdraw_toolbar_text)
    public TextView mTvToolbarText;

    private String mToolbarName;

    public static FragmentWithdraw newInstance(String name, int userId){
        Bundle args = new Bundle();
        args.putString(Constants.KEY_FRAGMENT_WITHDRAW_NAME, name);
        args.putInt(Constants.KEY_FRAGMENT_USER_ID, userId);
        FragmentWithdraw fragmentDeposit = new FragmentWithdraw();
        fragmentDeposit.setArguments(args);
        return fragmentDeposit;
    }

    @Override
    public void onActivityCreated(Bundle arg0) {
        super.onActivityCreated(arg0);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.FragmentDialogAnim;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme);

        if(getArguments() != null){
            this.mToolbarName = getArguments().getString(Constants.KEY_FRAGMENT_WITHDRAW_NAME);
        }

    }

    private void setToolbarTitle(String name){
        String title = "Withdraw from: " + name;
        mTvToolbarText.setText(title);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_withdraw, container, false);
        ButterKnife.bind(this, view);


        setToolbarTitle(mToolbarName);

        return view;
    }

    @OnClick(R.id.frag_adminwithdraw_back_btn)
    public void onBackBtnClick(){
        this.dismiss();
    }

    @OnClick(R.id.frag_adminwithdraw_btn_withdraw)
    public void onBtnWithdrawClick(){

    }
}
