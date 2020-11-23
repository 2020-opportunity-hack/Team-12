package com.example.sundayfriendshack.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sundayfriendshack.Constants;
import com.example.sundayfriendshack.R;
import com.example.sundayfriendshack.model.UserTransactionDto;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UsersTransactionsAdapter extends RecyclerView.Adapter<UsersTransactionsAdapter.ViewHolder> {


    private Context context;
    private ArrayList<UserTransactionDto.UserTransaction> mList;

    public UsersTransactionsAdapter(Context context, ArrayList<UserTransactionDto.UserTransaction> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_single_user_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {


        if(mList.get(i).isType() == Constants.TICKET_ACTION_TYPE_BOOLEAN_DEPOSIT){
            holder.amount.setTextColor(context.getResources().getColor(R.color.standardGreen));
            holder.userAction.setText("Deposit");


            String amount = "+ " + mList.get(i).getAmount();
            holder.amount.setText(amount);

        }else if (mList.get(i).isType() == Constants.TICKET_ACTION_TYPE_BOOLEAN_WITHDRAW){
            holder.amount.setTextColor(context.getResources().getColor(R.color.standardRed));
            holder.userAction.setText("Withdrawl");

            String amount = "- " + mList.get(i).getAmount();
            holder.amount.setText(amount);
        }

        holder.date.setText(String.valueOf(mList.get(i).getTime()));

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.vh_singletransaction_user_action) TextView userAction;

        @BindView(R.id.vh_singletransaction_date) TextView date;

        @BindView(R.id.vh_singletransactions_amount) TextView amount;

        int position;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            position = getAdapterPosition();

            //Onclick display list of that user's transactions
        }
    }
}
