package com.learnandearn.sundayfriends.ui.shared;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learnandearn.sundayfriends.R;
import com.learnandearn.sundayfriends.network.model.UserInfo;
import com.learnandearn.sundayfriends.ui.admin.dialog.DialogType;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SimpleListUsersAdapter extends RecyclerView.Adapter<SimpleListUsersAdapter.ViewHolder> {

    private List<UserInfo>          list;
    private DialogType              dialogType;
    private BtnClickAdapterCallback adapterCallback;

    public SimpleListUsersAdapter(Context context, DialogType dialogType, List<UserInfo> list) {
        this.list = list;
        this.dialogType = dialogType;

        try {
            adapterCallback = (BtnClickAdapterCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement AdapterCallback. " + e);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_user_single_deactivated, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.userName.setText(list.get(i).getName());
        holder.email.setText(list.get(i).getEmail());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.name)
        TextView userName;

        @BindView(R.id.email)
        TextView email;

        int position;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            position = getAdapterPosition();
            switch (dialogType) {
                case LINK_FAMILY:
                    adapterCallback.onLinkFamilyFragBtnClick(list.get(position));
                    break;

                case ACTIVATE_USER:
                    adapterCallback.onActivateFragBtnClick(list.get(position));
                    break;

                case DEACTIVATE_USER:
                    adapterCallback.onDeactivateFragBtnClick(list.get(position));
                    break;
            }

        }
    }

    public interface BtnClickAdapterCallback {
        void onActivateFragBtnClick(UserInfo userInfo);

        void onDeactivateFragBtnClick(UserInfo userInfo);

        void onLinkFamilyFragBtnClick(UserInfo userInfo);
    }
}
