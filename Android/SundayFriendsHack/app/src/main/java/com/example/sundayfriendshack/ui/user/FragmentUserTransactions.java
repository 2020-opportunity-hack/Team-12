package com.example.sundayfriendshack.ui.user;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sundayfriendshack.Constants;
import com.example.sundayfriendshack.R;
import com.example.sundayfriendshack.adapters.UsersTransactionsAdapter;
import com.example.sundayfriendshack.divider.SimpleDivider;
import com.example.sundayfriendshack.model.UserTransactionDto;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentUserTransactions extends DialogFragment {

    @BindView(R.id.fragusertransactions_recycler)
    RecyclerView mRecycler;

    @BindView(R.id.frag_usertransactions_toolbar_title)
    TextView mToolbarTitle;

    private Context mContext;

    private String mName;
    private int mUserId;

    private UsersTransactionsAdapter adapter;
    private ArrayList<UserTransactionDto.UserTransaction> mListUserTransactions = new ArrayList<>();

    public static FragmentUserTransactions newInstance(String name, int userId){
        Bundle args = new Bundle();
        args.putString(Constants.KEY_USER_TRANSACTIONS_USER_NAME, name);
        args.putInt(Constants.KEY_USER_TRANSACTIONS_USER_ID, userId);
        FragmentUserTransactions fragmentUserTransactions = new FragmentUserTransactions();
        fragmentUserTransactions.setArguments(args);
        return fragmentUserTransactions;
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
            mName = getArguments().getString(Constants.KEY_USER_TRANSACTIONS_USER_NAME);
            mUserId = getArguments().getInt(Constants.KEY_USER_TRANSACTIONS_USER_ID);
        }

        UserTransactionDto.UserTransaction  userTransaction = new UserTransactionDto().new UserTransaction();
        userTransaction.setTime(1606024858);
        userTransaction.setAmount(1111);
        userTransaction.setType(0);

        UserTransactionDto.UserTransaction  userTransaction2 = new UserTransactionDto().new UserTransaction();
        userTransaction2.setTime(1605765658);
        userTransaction2.setAmount(555);
        userTransaction2.setType(1);

        mListUserTransactions.add(userTransaction);
        mListUserTransactions.add(userTransaction2);

        adapter = new UsersTransactionsAdapter(mContext, mListUserTransactions);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_transactions, container, false);
        ButterKnife.bind(this, view);

        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        SimpleDivider simpleDivider = new SimpleDivider(ContextCompat.getDrawable(mContext, R.drawable.divider_simple_line));
        mRecycler.addItemDecoration(simpleDivider);
        mRecycler.setAdapter(adapter);

        setToolbarTitle(mName);


        return view;
    }

    private void setToolbarTitle(String name){
        String title = "Transaction History For: " + name;
        mToolbarTitle.setText(title);
    }

    @OnClick(R.id.fragusertransactions_back_btn)
    public void onBackBtnClick(){
        this.dismiss();
    }
}
