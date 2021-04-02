package com.learnandearn.sundayfriends.ui.user.profile;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.learnandearn.sundayfriends.Constants;
import com.learnandearn.sundayfriends.R;
import com.learnandearn.sundayfriends.ui.user.UserActivity;
import com.learnandearn.sundayfriends.ui.user.language.ChangeLanguageFragment;
import com.learnandearn.sundayfriends.utils.SharedPrefManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserProfileFragment extends Fragment {

    private static final String TAG = "UserProfileFragment";

    @BindView(R.id.iv_profile_pic)
    ImageView ivProfilePic;

    @BindView(R.id.tv_name)
    TextView tvTopLayoutName;

    @BindView(R.id.tv_email)
    TextView tvTopLayoutEmail;

    @BindView(R.id.tv_name_two)
    TextView tvSecondName;

    @BindView(R.id.tv_email_two)
    TextView tvSecondEmail;

    @BindView(R.id.tv_family_id)
    TextView tvFamilyId;

    @BindView(R.id.tv_static_name)
    TextView tvStaticName;

    @BindView(R.id.tv_static_email)
    TextView tvStaticEmail;

    @BindView(R.id.tv_static_phone_number)
    TextView tvStaticPhoneNumber;

    @BindView(R.id.tv_static_change_language)
    TextView tvStaticChangeLanguage;

    @BindView(R.id.tv_static_sign_out)
    TextView tvStaticSignOut;

    private Context context;
    private SharedPrefManager sharedPrefManager;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        sharedPrefManager = SharedPrefManager.getInstance(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        ButterKnife.bind(this, view);

        String imageUrl = sharedPrefManager.getProfilePic();

        if (imageUrl == null
                || imageUrl.equals("null")) {
            Glide.with(UserProfileFragment.this)
                    .load(R.drawable.blank_profile_pic)
                    .apply(RequestOptions.circleCropTransform())
                    .into(ivProfilePic);
        } else {
            Glide.with(UserProfileFragment.this)
                    .load(imageUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .into(ivProfilePic);
        }

        setLanguageStrings(sharedPrefManager.getLanguage());


        tvTopLayoutName.setText(sharedPrefManager.getName());
        tvTopLayoutEmail.setText(sharedPrefManager.getUserEmail());

        tvSecondEmail.setText(sharedPrefManager.getUserEmail());
        tvSecondName.setText(sharedPrefManager.getName());


        return view;
    }

    private void setLanguageStrings(String language){
        int familyId = sharedPrefManager.getFamilyId();
        switch (language){
            case Constants.VALUE_SHARED_PREF_LANGUAGE_ENGLISH:
                String familyIdEnglish = "Family Id: " + familyId;
                tvFamilyId.setText(familyIdEnglish);
                break;

            case Constants.VALUE_SHARED_PREF_LANGUAGE_SPANISH:
                String familyIdSpn = context.getString(R.string.family_id_spn) + familyId;
                tvFamilyId.setText(familyIdSpn);

                tvStaticName.setText(context.getString(R.string.name_spn));
                tvStaticEmail.setText(context.getString(R.string.email_spn));
                tvStaticPhoneNumber.setText(context.getString(R.string.phone_number_spn));
                tvStaticChangeLanguage.setText(context.getString(R.string.change_language_spn));
                tvStaticSignOut.setText(context.getString(R.string.sign_out_spn));

                break;

            case Constants.VALUE_SHARED_PREF_LANGUAGE_VIETNAMESE:
                String familyIdViet = context.getString(R.string.family_id_viet) + familyId;
                tvFamilyId.setText(familyIdViet);

                tvStaticName.setText(context.getString(R.string.name_viet));
                tvStaticEmail.setText(context.getString(R.string.email_viet));
                tvStaticPhoneNumber.setText(context.getString(R.string.phone_number_viet));
                tvStaticChangeLanguage.setText(context.getString(R.string.change_language_viet));
                tvStaticSignOut.setText(context.getString(R.string.sign_out_viet));

                break;
        }
    }

    @OnClick(R.id.btn_sign_out)
    void onUserSignOut() {
        UserActivity userActivity = (UserActivity) context;
        userActivity.signOutUser();
    }

    @OnClick(R.id.btn_change_language)
    void onUserChangeLanguage() {
        getParentFragmentManager().beginTransaction()
                .add(
                        R.id.user_fragment_container,
                        new ChangeLanguageFragment(),
                        Constants.FRAGMENT_USER_CHANGE_LANGUAGE
                )
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }
}
