package com.example.sundayfriendshack.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sundayfriendshack.R;
import com.example.sundayfriendshack.model.UserInfo;
import com.example.sundayfriendshack.ui.menu.ActivateMenu;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivateUserAdapter extends RecyclerView.Adapter<ActivateUserAdapter.ViewHolder> {

    private Context context;
    private ArrayList<UserInfo> mList;

    public ActivateUserAdapter(Context context, ArrayList<UserInfo> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_single_deactivated_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

        holder.userName.setText(mList.get(i).getName());
        holder.email.setText(mList.get(i).getEmail());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.vh_activateuser_name) TextView userName;

        @BindView(R.id.vh_activateuser_email) TextView email;

        @BindView(R.id.vh_activateuser_activate_btn) ImageView activateBtn;

        int position;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            activateBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            position = getAdapterPosition();

            if(v.getId() == R.id.vh_activateuser_activate_btn){
                PopupWindow popupWindow = new ActivateMenu(
                        context,
                        mList.get(position).getUserId()
                );
                popupWindow.showAsDropDown(activateBtn);
            }
        }
    }
}
