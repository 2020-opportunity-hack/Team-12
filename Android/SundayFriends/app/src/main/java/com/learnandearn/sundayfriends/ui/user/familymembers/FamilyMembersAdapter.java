package com.learnandearn.sundayfriends.ui.user.familymembers;

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

public class FamilyMembersAdapter extends
        RecyclerView.Adapter<FamilyMembersAdapter.ViewHolder> {

    private static final String TAG = "FamilyMembersAdapter";

    private Context                     context;
    private List<UserInfo>              list;
    private FamilyMemberAdapterCallback adapterCallback;

    public FamilyMembersAdapter(Context context, List<UserInfo> list) {
        this.context = context;
        this.list = list;

        try {
            adapterCallback = (FamilyMemberAdapterCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement AdapterCallback. " + e);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_user_single_family_member, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

        if (list.get(i).getImageUrl() == null) {
            loadEmptyProfilePic(holder.familyMemberProfilePic);
        } else {
            if(list.get(i).getImageUrl().equals("null")){
                loadEmptyProfilePic(holder.familyMemberProfilePic);
            }else{
                Glide.with(context)
                        .load(list.get(i).getImageUrl())
                        .apply(RequestOptions.circleCropTransform())
                        .into(holder.familyMemberProfilePic);
            }
        }


        holder.familyMemberName.setText(list.get(i).getName());
        holder.familyMemberBalance.setText(String.valueOf(list.get(i).getBalance()));
        holder.familyMemberEmail.setText(list.get(i).getEmail());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.name)
        TextView familyMemberName;

        @BindView(R.id.profile_pic)
        ImageView familyMemberProfilePic;

        @BindView(R.id.balance)
        TextView familyMemberBalance;

        @BindView(R.id.email)
        TextView familyMemberEmail;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            adapterCallback.onFamilyMemberClick(list.get(position));
        }
    }

    public interface FamilyMemberAdapterCallback {
        void onFamilyMemberClick(UserInfo userInfo);
    }

    private void loadEmptyProfilePic(ImageView view){
        Glide.with(context)
                .load(R.drawable.blank_profile_pic)
                .apply(RequestOptions.circleCropTransform())
                .into(view);
    }
}
