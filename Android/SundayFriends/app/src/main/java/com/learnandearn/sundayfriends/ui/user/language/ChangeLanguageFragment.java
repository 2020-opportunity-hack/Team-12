package com.learnandearn.sundayfriends.ui.user.language;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.learnandearn.sundayfriends.Constants;
import com.learnandearn.sundayfriends.R;
import com.learnandearn.sundayfriends.utils.SharedPrefManager;

import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ChangeLanguageFragment extends Fragment {


    @BindViews({R.id.iv_checkmark_english, R.id.iv_checkmark_spanish, R.id.iv_checkmark_vietnamese})
    List<ImageView> ivCheckMarks;

    private SharedPrefManager sharedPrefManager;
    private Context context;
    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        sharedPrefManager = SharedPrefManager.getInstance(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_change_language, container, false);
        unbinder = ButterKnife.bind(this, view);

        String language = sharedPrefManager.getLanguage();
        switch (language){
            case Constants.VALUE_SHARED_PREF_LANGUAGE_ENGLISH:
                setChecked(R.id.iv_checkmark_english);
                break;

            case Constants.VALUE_SHARED_PREF_LANGUAGE_SPANISH:
                setChecked(R.id.iv_checkmark_spanish);
                break;

            case Constants.VALUE_SHARED_PREF_LANGUAGE_VIETNAMESE:
                setChecked(R.id.iv_checkmark_vietnamese);
                break;
        }


        return view;
    }

    @OnClick(R.id.btn_back)
    void onBackBtnClick(){
        getParentFragmentManager().popBackStack();
    }

    @OnClick({R.id.btn_english, R.id.btn_spanish, R.id.btn_vietnamese})
    void onCheckBoxClick(View view){
        switch (view.getId()){
            case R.id.btn_english:
                setChecked(R.id.iv_checkmark_english);
                sharedPrefManager.setLanguage(Constants.VALUE_SHARED_PREF_LANGUAGE_ENGLISH);
                restartActivity();
                break;

            case R.id.btn_spanish:
                setChecked(R.id.iv_checkmark_spanish);
                sharedPrefManager.setLanguage(Constants.VALUE_SHARED_PREF_LANGUAGE_SPANISH);
                restartActivity();
                break;

            case R.id.btn_vietnamese:
                setChecked(R.id.iv_checkmark_vietnamese);
                sharedPrefManager.setLanguage(Constants.VALUE_SHARED_PREF_LANGUAGE_VIETNAMESE);
                restartActivity();
                break;
        }
    }

    private void restartActivity(){
        Intent intent = getActivity().getIntent();
        getActivity().finish();
        startActivity(intent);
    }

    private void setChecked(int id){
        for(int i = 0; i < ivCheckMarks.size(); i++){
            if(ivCheckMarks.get(i).getId() != id){
                ivCheckMarks.get(i).setVisibility(View.GONE);
            }else{
                ivCheckMarks.get(i).setVisibility(View.VISIBLE);
            }
        }
    }

    //Need to unbind on fragments added to backstack or it leaks
    //e.g - go to profile, add fragment, click on second bottom navigation tab
    //Adapter must also be set to null because it's binding views inside as well
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
