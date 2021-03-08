package com.learnandearn.sundayfriends.ui.admin.members;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
import com.learnandearn.sundayfriends.utils.SimpleDivider;
import com.learnandearn.sundayfriends.utils.SnackbarManager;
import com.learnandearn.sundayfriends.utils.ToastManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MembersFragment extends Fragment {

    private static final String TAG = "MembersFragment";

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.center_progress_bar)
    ProgressBar centerProgressBar;

    @BindView(R.id.parent_layout)
    ViewGroup parentLayout;

    @BindView(R.id.searchview)
    SearchView searchView;

    @BindView(R.id.btn_search)
    Button btnSearch;

    @BindView(R.id.tv_no_results)
    TextView tvNoResults;


    private static final int STARTING_SEARCH_USERS_INDEX   = 0;
    private static final int INCREMENT_SEARCH_USERS_AMOUNT = 25;
    private              int currentSearchIndex;

    private Context          context;
    private MembersViewModel viewModel;
    private MembersAdapter   usersAdapter;
    private List<UserInfo>   listUsers         = new ArrayList<>();
    private boolean          isThereAfterValue = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        usersAdapter = new MembersAdapter(context, listUsers);
        viewModel = new ViewModelProvider(this).get(MembersViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_list_of_users, container, false);
        ButterKnife.bind(this, view);

        centerProgressBar.setVisibility(View.VISIBLE);

        initRecycler();
        initSearchViewClearBtn();

        setGetFirstPageListener();
        setGetNextPageListener();
        setSearchUserListener();

        viewModel.getFirstPageMembers(STARTING_SEARCH_USERS_INDEX, INCREMENT_SEARCH_USERS_AMOUNT);

        return view;
    }

    private void initSearchViewClearBtn() {
        View closeButton = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setQuery("", false);
                searchView.setIconified(false);
                refreshUserList();
            }
        });
    }

    /**
     * Checks if last index of users list is a progressbar and removes it
     */
    private void removeAdapterProgressBar() {
        //Checks if last item in index is progress bar
        int lastIndex = listUsers.size() - 1;
        if (listUsers.size() > 0 && listUsers.get(lastIndex).isProgressBar()) {
            listUsers.remove(lastIndex);
            usersAdapter.notifyItemRemoved(lastIndex);
        }
    }

    private void setGetFirstPageListener() {
        viewModel.getFirstPageLiveData().observe(getViewLifecycleOwner(), new Observer<List<UserInfo>>() {
            @Override
            public void onChanged(List<UserInfo> responseBody) {
                centerProgressBar.setVisibility(View.GONE);

                if (responseBody == null) {
                    isThereAfterValue = false;
                    SnackbarManager.unexpectedError(context, parentLayout).show();
                } else if (responseBody.size() == 0) {
                    Log.d(TAG, "onChanged: GetFirstPage end of results. ");
                    isThereAfterValue = false;
                    //End of all the users
                    tvNoResults.setVisibility(View.VISIBLE);
                } else {
                    isThereAfterValue = true;
                    Log.d(TAG, "onChanged: GetFirstPage: There is after value. ");
                    currentSearchIndex = currentSearchIndex + INCREMENT_SEARCH_USERS_AMOUNT;
                    handleFirstPageData(responseBody);
                }

            }
        });
    }


    private void handleFirstPageData(List<UserInfo> list) {
        listUsers.addAll(list);
        if (isThereAfterValue) {
            listUsers.add(UserInfo.addProgressBar());
        }
        usersAdapter.notifyDataSetChanged();
    }

    private void setGetNextPageListener() {
        viewModel.getNextPageLiveData().observe(getViewLifecycleOwner(), new Observer<List<UserInfo>>() {
            @Override
            public void onChanged(List<UserInfo> responseBody) {

                removeAdapterProgressBar();

                if (responseBody == null) {
                    isThereAfterValue = false;
                    SnackbarManager.unexpectedError(context, parentLayout).show();
                } else if (responseBody.size() == 0) {
                    Log.d(TAG, "onChanged: GetNextPage: end of results.");
                    isThereAfterValue = false;
                    //End of all the users
                    ToastManager.endOfUsers(context).show();
                } else {
                    isThereAfterValue = true;
                    Log.d(TAG, "onChanged: GetNextPage: there is after value.");
                    currentSearchIndex = currentSearchIndex + INCREMENT_SEARCH_USERS_AMOUNT;
                    Log.d(TAG, "onChanged: " + currentSearchIndex);
                    handleNextPageData(responseBody);
                }


            }
        });
    }

    private void handleNextPageData(List<UserInfo> list) {
        listUsers.addAll(list);
        if (isThereAfterValue) {
            listUsers.add(UserInfo.addProgressBar());
        }
        usersAdapter.notifyDataSetChanged();
    }

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            if (!MembersFragment.this.recyclerView.canScrollVertically(1)) {
                Log.d(TAG, "onScrollStateChanged: CALLED CAN'T SCROLL");

                if (isThereAfterValue) {
                    Log.d(TAG, "onScrolled: Performing query . . . ");
                    isThereAfterValue = false;
                    viewModel.getNextPageMembers(currentSearchIndex, INCREMENT_SEARCH_USERS_AMOUNT);
                }
            }
        }
    };

    private void setSearchUserListener() {
        viewModel.getSearchUserLiveData().observe(getViewLifecycleOwner(), new Observer<List<UserInfo>>() {
            @Override
            public void onChanged(List<UserInfo> responseBody) {
                centerProgressBar.setVisibility(View.GONE);
                btnSearch.setClickable(true);

                if (responseBody == null) {
                    SnackbarManager.unexpectedError(context, parentLayout).show();
                } else if (responseBody.size() == 0) {
                    tvNoResults.setVisibility(View.VISIBLE);
                } else {
                    handleSearchData(responseBody);
                }

            }
        });
    }

    private void handleSearchData(List<UserInfo> list) {
        listUsers.addAll(list);
        usersAdapter.notifyDataSetChanged();
    }


    private void initRecycler() {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        SimpleDivider simpleDivider = new SimpleDivider(
                ContextCompat.getDrawable(context, R.drawable.divider_simple_line));
        recyclerView.addItemDecoration(simpleDivider);
        recyclerView.addOnScrollListener(onScrollListener);
        recyclerView.setAdapter(usersAdapter);
    }


    @OnClick(R.id.btn_refresh)
    void onBtnRefreshClick() {
        refreshUserList();
    }

    private void refreshUserList() {
        centerProgressBar.setVisibility(View.VISIBLE);
        tvNoResults.setVisibility(View.GONE);
        listUsers.clear();
        usersAdapter.notifyDataSetChanged();
        centerProgressBar.setVisibility(View.VISIBLE);

        //Reset index to 0
        currentSearchIndex = STARTING_SEARCH_USERS_INDEX;

        viewModel.getFirstPageMembers(currentSearchIndex, INCREMENT_SEARCH_USERS_AMOUNT);
    }

    //Search has no pagination
    @OnClick(R.id.btn_search)
    void onBtnSearchClick() {
        String name = String.valueOf(searchView.getQuery()).trim();
        if (name.equals("")) {
            return;
        }

        btnSearch.setClickable(false);
        tvNoResults.setVisibility(View.GONE);

        centerProgressBar.setVisibility(View.VISIBLE);
        isThereAfterValue = false;

        listUsers.clear();
        usersAdapter.notifyDataSetChanged();

        currentSearchIndex = STARTING_SEARCH_USERS_INDEX;

        viewModel.searchForUser(name);
    }
}
