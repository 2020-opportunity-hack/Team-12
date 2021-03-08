package com.learnandearn.sundayfriends.ui.shared.usertransactions;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.learnandearn.sundayfriends.Constants;
import com.learnandearn.sundayfriends.network.model.UserTransaction;
import com.learnandearn.sundayfriends.network.shared.SharedRepo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class UserTransactionsViewModel extends AndroidViewModel {


    private SharedRepo                              repo;
    private MediatorLiveData<List<UserTransaction>> mediatorLiveData = new MediatorLiveData<>();

    public UserTransactionsViewModel(@NonNull Application application) {
        super(application);

        repo = new SharedRepo(application);

        mediatorLiveData.addSource(repo.getTransactionsLiveData(), new Observer<List<UserTransaction>>() {
            @Override
            public void onChanged(List<UserTransaction> list) {
                formatTransactionTime(list);

                mediatorLiveData.setValue(list);

            }
        });
    }

    private void formatTransactionTime(List<UserTransaction> list) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.USER_TRANSACTION_FORMAT_PATTERN, Locale.US);
        try {
            for (int i = 0; i < list.size(); i++) {
                Date time = dateFormat.parse(list.get(i).getTime());
                list.get(i).setTime(time.toString());
            }
        } catch (Exception e) {
            Log.d(TAG, "onChanged: Formatting exception: " + e);
        }

    }

    public MediatorLiveData<List<UserTransaction>> getMediatorLiveData() {
        return mediatorLiveData;
    }

    public void getUserTransactions(int userId) {
        repo.getUserTransactions(userId);
    }


}
