package com.learnandearn.sundayfriends.ui.admin.activate;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.learnandearn.sundayfriends.R;
import com.learnandearn.sundayfriends.network.model.UserInfo;
import com.learnandearn.sundayfriends.ui.admin.dialog.DialogType;
import com.learnandearn.sundayfriends.ui.shared.SimpleListUsersAdapter;
import com.learnandearn.sundayfriends.utils.SearchUserUtil;
import com.learnandearn.sundayfriends.utils.SimpleDivider;
import com.learnandearn.sundayfriends.utils.SnackbarManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ActivateUserFragment extends Fragment {

    private static final String TAG = "ActivateUserFragment";

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.parent_layout)
    ViewGroup parentLayout;

    @BindView(R.id.searchview)
    SearchView searchView;

    @BindView(R.id.btn_search)
    Button btnSearch;

    @BindView(R.id.tv_no_results)
    TextView tvNoResults;

    private Context                context;
    private SimpleListUsersAdapter adapter;
    private List<UserInfo>         listUsers = new ArrayList<>();
    private ActivateViewModel      viewModel;
    private Unbinder               unbinder;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        adapter = new SimpleListUsersAdapter(context, DialogType.ACTIVATE_USER, listUsers);
        viewModel = new ViewModelProvider(this).get(ActivateViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_activate_user, container, false);
        unbinder = ButterKnife.bind(this, view);

        initRecycler();
        setDeactivatedUsersListener();

        return view;
    }

    private void initRecycler() {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        SimpleDivider simpleDivider = new SimpleDivider(ContextCompat.getDrawable(context, R.drawable.divider_simple_line));
        recyclerView.addItemDecoration(simpleDivider);
        recyclerView.setAdapter(adapter);
    }

    private void setDeactivatedUsersListener() {
        viewModel.getDeactivatedUsersLiveData().observe(getViewLifecycleOwner(), new Observer<List<UserInfo>>() {
            @Override
            public void onChanged(List<UserInfo> list) {
                progressBar.setVisibility(View.GONE);
                if (list == null) {
                    SnackbarManager.unexpectedError(context, parentLayout).show();
                    return;
                }
                if (list.size() == 0) {
                    tvNoResults.setVisibility(View.VISIBLE);
                } else {
                    handleData(list);
                }
            }
        });
        viewModel.getDeactivatedUsers();
    }

    private void handleData(List<UserInfo> list) {
        listUsers.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.btn_back)
    void onBackBtnClick() {
        getParentFragmentManager().popBackStack();
    }

    @OnClick(R.id.btn_search)
    void onSearchBtnClick() {
        tvNoResults.setVisibility(View.GONE);
        String input = String.valueOf(searchView.getQuery()).trim();
        List<UserInfo> newList = SearchUserUtil.filteredList(listUsers, input);
        if (newList.size() == 0) {
            tvNoResults.setVisibility(View.VISIBLE);
        }
        listUsers.clear();
        listUsers.addAll(newList);
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.btn_refresh)
    void onBtnRefreshClick() {
        progressBar.setVisibility(View.VISIBLE);
        tvNoResults.setVisibility(View.GONE);
        listUsers.clear();
        adapter.notifyDataSetChanged();
        progressBar.setVisibility(View.VISIBLE);
        viewModel.getDeactivatedUsers();
    }

    //Need to unbind on fragments added to backstack or it leaks
    //e.g - go to profile, add fragment, click on second bottom navigation tab
    //Adapter must also be set to null because it's binding views inside as well
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        adapter = null;
        unbinder.unbind();
    }

}
