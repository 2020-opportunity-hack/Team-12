package com.example.sundayfriendshack.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sundayfriendshack.R;
import com.example.sundayfriendshack.model.UserInfo;
import com.example.sundayfriendshack.ui.admin.AdminActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminListUsersAdapter extends RecyclerView.Adapter<AdminListUsersAdapter.ViewHolder> {


    private Context mContext;
    private ArrayList<UserInfo> mList;
    private AdminActivity adminActivity;

    public AdminListUsersAdapter(Context context, ArrayList<UserInfo> mList) {
        this.mContext = context;
        this.mList = mList;
        adminActivity = (AdminActivity) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_admin_list_of_users, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        Glide.with(mContext)
                .load(mList.get(i).getImageUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.profilePic);

        holder.userBalance.setText(String.valueOf(mList.get(i).getBalance()));
        holder.email.setText(mList.get(i).getEmail());
        holder.name.setText(mList.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.vhadminusers_btn_withdraw) TextView btnWithdraw;

        @BindView(R.id.vhadminusers_btn_deposit) TextView btnDeposit;

        @BindView(R.id.vh_adminusers_balance) TextView userBalance;

        @BindView(R.id.vh_adminusers_name) TextView name;

        @BindView(R.id.vh_adminusers_email) TextView email;

        @BindView(R.id.vh_adminusers_user_profile_pic) ImageView profilePic;

        @BindView(R.id.vh_adminusers_parent_layout) ConstraintLayout parentLayout;

        int position;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            btnWithdraw.setOnClickListener(this);
            btnDeposit.setOnClickListener(this);
            parentLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            position = getAdapterPosition();

            //Onclick display list of that user's transactions
            switch (v.getId()){
                case R.id.vhadminusers_btn_withdraw:
                    adminActivity.initWithdrawFragment(
                            mList.get(position).getName(),
                            mList.get(position).getUserId()
                    );
                    break;

                case R.id.vhadminusers_btn_deposit:
                    adminActivity.initDepositFragment(
                            mList.get(position).getName(),
                            mList.get(position).getUserId()
                    );
                    break;

                case R.id.vh_adminusers_parent_layout:
                    adminActivity.initUserTransactionHistory(
                            mList.get(position).getName(),
                            mList.get(position).getUserId()
                    );
                    break;
            }

        }
    }
}
