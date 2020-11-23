package com.example.sundayfriendshack.ui.admin;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


import com.example.sundayfriendshack.Constants;
import com.example.sundayfriendshack.R;
import com.example.sundayfriendshack.manager.ToastManager;
import com.example.sundayfriendshack.repo.AdminRepo.AdminContract;
import com.example.sundayfriendshack.repo.AdminRepo.AdminRepo;
import com.example.sundayfriendshack.utils.KeyboardUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentDeposit extends DialogFragment implements AdminContract.Model.onTicketDeposit {


    @BindView(R.id.frag_admindeposit_toolbar_text)
    public TextView mTvToolbarText;

    @BindView(R.id.frag_admindeposit_et_amount)
    EditText mEtDeposit;

    @BindView(R.id.frag_admindeposit_btn_deposit)
    Button mBtnDeposit;

    private String mToolbarName;
    private Context mContext;

    private int mUserId;
    private AdminRepo mRepo;

    private int mAmountTicketsToDeposit = -1;

    public static FragmentDeposit newInstance(String name, int userId){
        Bundle args = new Bundle();
        args.putString(Constants.KEY_FRAGMENT_DEPOSIT_NAME, name);
        args.putInt(Constants.KEY_FRAGMENT_USER_ID, userId);
        FragmentDeposit fragmentDeposit = new FragmentDeposit();
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
            this.mToolbarName = getArguments().getString(Constants.KEY_FRAGMENT_DEPOSIT_NAME);
            this.mUserId = getArguments().getInt(Constants.KEY_FRAGMENT_USER_ID);
        }

        mRepo = new AdminRepo(mContext);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_deposit, container, false);
        ButterKnife.bind(this, view);

        setToolbarTitle(mToolbarName);


        return view;
    }

    private void setToolbarTitle(String name){
        String title = "Deposit to: " + name;
        mTvToolbarText.setText(title);
    }

    @OnClick(R.id.frag_admindeposit_back_btn)
    public void onBackBtnClick(){
        this.dismiss();
    }

    @OnClick(R.id.frag_admindeposit_btn_deposit)
    public void onBtnDepositClick(){
        KeyboardUtils.hideKeyboard(mEtDeposit);
        String stringAmountToDeposit = mEtDeposit.getText().toString().trim();
        mAmountTicketsToDeposit = Integer.parseInt(stringAmountToDeposit);
        mRepo.depositTicket(mUserId, mAmountTicketsToDeposit, Constants.TICKET_ACTION_TYPE_DEPOSIT);
    }

    @Override
    public void onSuccessTicketDeposit() {
        mBtnDeposit.setVisibility(View.INVISIBLE);
        Toast.makeText(mContext, "Successfully deposited: " + mAmountTicketsToDeposit + " Tickets", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailedTicketDeposit(Throwable t) {
        ToastManager.displayNetworkError(mContext, t);
    }
}
