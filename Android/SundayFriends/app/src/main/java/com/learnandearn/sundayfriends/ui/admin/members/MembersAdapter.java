package com.learnandearn.sundayfriends.ui.admin.members;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.learnandearn.sundayfriends.R;
import com.learnandearn.sundayfriends.network.model.UserInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MembersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "MembersAdapter";


    private static final int USER_ITEM     = 0;
    private static final int PROGRESS_ITEM = 1;

    private Context                       context;
    private List<UserInfo>                list;
    private AdminListUsersAdapterCallback adapterCallback;

    public MembersAdapter(Context context, List<UserInfo> list) {
        this.context = context;
        this.list = list;

        try {
            adapterCallback = (AdminListUsersAdapterCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement AdapterCallback. " + e);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == USER_ITEM) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.viewholder_admin_single_member, parent, false);
            return new UserViewHolder(view);
        } else {
            //Progress Item
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.viewholder_list_users_progress_bar, parent, false);
            return new ProgressHolder(view);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).isProgressBar()) {
            return PROGRESS_ITEM;
        }

        return USER_ITEM;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {

        if (holder.getItemViewType() == USER_ITEM) {
            UserViewHolder userHolder = (UserViewHolder) holder;

            String imageUrl = list.get(i).getImageUrl();

            if (imageUrl == null || imageUrl.equals("null")) {
                loadBlankProfilePic(userHolder.profilePic);
            } else {
                loadProfilePic(userHolder.profilePic, imageUrl);
            }

            userHolder.userBalance.setText(String.valueOf(list.get(i).getBalance()));
            userHolder.email.setText(list.get(i).getEmail());
            userHolder.name.setText(list.get(i).getName());
        }

    }

    private void loadProfilePic(ImageView iv, String imageUrl){
        Glide.with(context)
                .load(imageUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(iv);
    }

    private void loadBlankProfilePic(ImageView iv){
        Glide.with(context)
                .load(R.drawable.blank_profile_pic)
                .apply(RequestOptions.circleCropTransform())
                .into(iv);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.btn_withdraw)
        TextView btnWithdraw;

        @BindView(R.id.btn_deposit)
        TextView btnDeposit;

        @BindView(R.id.balance)
        TextView userBalance;

        @BindView(R.id.name)
        TextView name;

        @BindView(R.id.email)
        TextView email;

        @BindView(R.id.profile_pic)
        ImageView profilePic;

        @BindView(R.id.btn_get_user_transactions)
        ImageView btnMore;

        UserViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            btnWithdraw.setOnClickListener(this);
            btnDeposit.setOnClickListener(this);
            btnMore.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            switch (v.getId()) {
                case R.id.btn_withdraw:
                    adapterCallback.onWithdrawBtnClick(list.get(position));
                    break;

                case R.id.btn_deposit:
                    adapterCallback.onDepositBtnClick(list.get(position));
                    break;

                case R.id.btn_get_user_transactions:
                    adapterCallback.onGetTransactionsBtnClick(list.get(position));
                    break;
            }

        }
    }

     private class ProgressHolder extends RecyclerView.ViewHolder {
        private ProgressHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public interface AdminListUsersAdapterCallback{
        void onWithdrawBtnClick(UserInfo userInfo);
        void onDepositBtnClick(UserInfo userInfo);
        void onGetTransactionsBtnClick(UserInfo userInfo);
    }
}
