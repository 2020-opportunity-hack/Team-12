package com.learnandearn.sundayfriends.ui.user.familymembers;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.learnandearn.sundayfriends.Constants;
import com.learnandearn.sundayfriends.R;
import com.learnandearn.sundayfriends.network.model.UserInfo;
import com.learnandearn.sundayfriends.utils.SharedPrefManager;
import com.learnandearn.sundayfriends.utils.SimpleDivider;
import com.learnandearn.sundayfriends.utils.SnackbarManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FamilyMembersFragment extends Fragment {

    private static final String TAG = "FamilyMembersFragment";

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.center_progress_bar)
    ProgressBar centerProgressBar;

    @BindView(R.id.parent_layout)
    ViewGroup parentLayout;

    @BindView(R.id.tv_my_family)
    TextView tvMyFamily;

    private Context                context;
    private FamilyMembersAdapter   adapter;
    private List<UserInfo>         listFamilyMembers = new ArrayList<>();
    private FamilyMembersViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        adapter = new FamilyMembersAdapter(context, listFamilyMembers);
        viewModel = new ViewModelProvider(this).get(FamilyMembersViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_family_members, container, false);
        ButterKnife.bind(this, view);

        initRecycler();
        setGetFamilyMembersListener();

        setLanguageStrings(SharedPrefManager.getInstance(context).getLanguage());

        return view;
    }

    private void setLanguageStrings(String language){
        switch (language){
            case Constants.VALUE_SHARED_PREF_LANGUAGE_ENGLISH:

                break;

            case Constants.VALUE_SHARED_PREF_LANGUAGE_SPANISH:
                tvMyFamily.setText(context.getString(R.string.my_family_spn));

                break;

            case Constants.VALUE_SHARED_PREF_LANGUAGE_VIETNAMESE:
                tvMyFamily.setText(context.getString(R.string.my_family_viet));

                break;
        }
    }

    private void setGetFamilyMembersListener() {
        viewModel.getFamilyMembersLiveData().observe(getViewLifecycleOwner(), new Observer<List<UserInfo>>() {
            @Override
            public void onChanged(List<UserInfo> list) {
                centerProgressBar.setVisibility(View.GONE);
                if (list != null) {
                    loadDataIntoRecycler(list);
                } else {
                    SnackbarManager.unexpectedError(context, parentLayout).show();
                }
            }
        });


        int familyId = SharedPrefManager.getInstance(context).getFamilyId();
        viewModel.getFamilyMembers(familyId);
    }

    private void loadDataIntoRecycler(List<UserInfo> list) {
        try {
            listFamilyMembers.addAll(list);
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            Log.e(TAG, "loadDataIntoRecycler: ", e);
        }
    }

    private void initRecycler() {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        SimpleDivider divider = new SimpleDivider(
                ContextCompat.getDrawable(context, R.drawable.divider_simple_line)
        );
        recyclerView.addItemDecoration(divider);
        recyclerView.setAdapter(adapter);
    }

}
