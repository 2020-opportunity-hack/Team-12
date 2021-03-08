package com.learnandearn.sundayfriends.ui.shared.usertransactions;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.learnandearn.sundayfriends.Constants;
import com.learnandearn.sundayfriends.R;
import com.learnandearn.sundayfriends.network.model.UserInfo;
import com.learnandearn.sundayfriends.network.model.UserTransaction;
import com.learnandearn.sundayfriends.utils.SimpleDivider;
import com.learnandearn.sundayfriends.utils.SnackbarManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserTransactionsFragment extends DialogFragment {

    private static final String TAG = "FragmentUserTransaction";

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.btn_title)
    TextView tvToolbarTitle;

    @BindView(R.id.parent_layout)
    ViewGroup parentLayout;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private Context                   context;
    private UserInfo                  userInfo;
    private UsersTransactionsAdapter  adapter;
    private List<UserTransaction>     listTransactions = new ArrayList<>();
    private UserTransactionsViewModel viewModel;

    public static UserTransactionsFragment newInstance(UserInfo userInfo) {
        Bundle args = new Bundle();
        args.putSerializable(Constants.KEY_BUNDLE_SERIALIZABLE_USER_INFO, userInfo);
        UserTransactionsFragment fragment = new UserTransactionsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle arg0) {
        super.onActivityCreated(arg0);
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().getAttributes().windowAnimations = R.style.FragmentDialogAnim;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme);

        if (getArguments() != null) {
            userInfo = (UserInfo) getArguments()
                    .getSerializable(Constants.KEY_BUNDLE_SERIALIZABLE_USER_INFO);
        }

        adapter = new UsersTransactionsAdapter(context, listTransactions);
        viewModel = new ViewModelProvider(this).get(UserTransactionsViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transactions, container, false);
        ButterKnife.bind(this, view);

        setToolbarTitle(userInfo.getName());
        initRecycler();
        setTransactionsListener();

        return view;
    }

    private void setTransactionsListener() {
        viewModel.getMediatorLiveData().observe(getViewLifecycleOwner(), new Observer<List<UserTransaction>>() {
            @Override
            public void onChanged(List<UserTransaction> list) {
                progressBar.setVisibility(View.GONE);
                if (list != null) {
                    loadDataIntoRecycler(list);
                } else {
                    SnackbarManager.unexpectedError(context, parentLayout).show();
                }
            }
        });
        viewModel.getUserTransactions(userInfo.getUserId());
    }

    private void loadDataIntoRecycler(List<UserTransaction> list) {
        listTransactions.addAll(list);
        adapter.notifyDataSetChanged();
    }

    private void initRecycler() {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        SimpleDivider simpleDivider = new SimpleDivider(
                ContextCompat.getDrawable(context, R.drawable.divider_simple_line)
        );
        recyclerView.addItemDecoration(simpleDivider);
        recyclerView.setAdapter(adapter);
    }

    private void setToolbarTitle(String name) {
        String title = "Transaction History For: " + name;
        tvToolbarTitle.setText(title);
    }

    @OnClick(R.id.btn_back)
    void onBackBtnClick() {
        this.dismiss();
    }

}
