package com.learnandearn.sundayfriends.ui.shared.usertransactions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learnandearn.sundayfriends.Constants;
import com.learnandearn.sundayfriends.R;
import com.learnandearn.sundayfriends.network.model.UserTransaction;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UsersTransactionsAdapter extends RecyclerView.Adapter<UsersTransactionsAdapter.ViewHolder> {


    private Context               context;
    private List<UserTransaction> list;

    public UsersTransactionsAdapter(Context context, List<UserTransaction> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_shared_single_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

        String amount;
        switch (list.get(i).getType()) {
            case Constants.TICKET_ACTION_TYPE_DEPOSIT:
                holder.amount.setTextColor(context.getResources().getColor(R.color.standardGreen));
                holder.userAction.setText("Deposit");

                amount = "+ " + list.get(i).getAmount();
                holder.amount.setText(amount);
                break;

            case Constants.TICKET_ACTION_TYPE_WITHDRAW:
                holder.amount.setTextColor(context.getResources().getColor(R.color.standardRed));
                holder.userAction.setText("Withdrawl");

                amount = "- " + list.get(i).getAmount();
                holder.amount.setText(amount);
                break;

            case Constants.TICKET_ACTION_TYPE_INTEREST_DEPOSIT:
                holder.amount.setTextColor(context.getResources().getColor(R.color.standardGreen));
                holder.userAction.setText("Interest Deposit");
                amount = "+ " + list.get(i).getAmount();
                holder.amount.setText(amount);
                break;
        }


        holder.date.setText(String.valueOf(list.get(i).getTime()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.action_type)
        TextView userAction;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.amount)
        TextView amount;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
