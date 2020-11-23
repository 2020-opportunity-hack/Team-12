package com.example.sundayfriendshack.adapters;

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
import com.example.sundayfriendshack.R;
import com.example.sundayfriendshack.model.FamilyMemberDto;
import com.example.sundayfriendshack.ui.user.MainActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserFamilyMembersAdapter extends RecyclerView.Adapter<UserFamilyMembersAdapter.ViewHolder> {


    private Context context;
    private ArrayList<FamilyMemberDto> mList;

    private MainActivity mainActivity;

    public UserFamilyMembersAdapter(Context context, ArrayList<FamilyMemberDto> mList) {
        this.context = context;
        this.mList = mList;

        mainActivity = (MainActivity) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_single_user_family_member, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.familyMemberName.setText(mList.get(i).getName());
        Glide.with(context)
                .load(mList.get(i).getImageUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.familyMemberProfilePic);

        holder.familyMemberBalance.setText(String.valueOf(mList.get(i).getBalance()));
        holder.familyMemberEmail.setText(mList.get(i).getEmail());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.vh_singlefamilymember_name) TextView familyMemberName;

        @BindView(R.id.vh_singlefamilymember_profile_pic) ImageView familyMemberProfilePic;

        @BindView(R.id.vh_singlefamilymember_user_balance) TextView familyMemberBalance;

        @BindView(R.id.vh_singlefamilymember_email) TextView familyMemberEmail;

        int position;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            position = getAdapterPosition();

            mainActivity.initFragmentUserTransactions(mList.get(position).getName(), mList.get(position).getUserId());
        }
    }
}
