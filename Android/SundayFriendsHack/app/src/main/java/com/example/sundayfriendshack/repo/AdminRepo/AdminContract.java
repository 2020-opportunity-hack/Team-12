package com.example.sundayfriendshack.repo.AdminRepo;

import com.example.sundayfriendshack.model.UserInfo;
import com.example.sundayfriendshack.model.UserTransactionDto;

import java.util.ArrayList;

import retrofit2.Response;

public class AdminContract {
    public interface Model {
        interface onGetListUsers {
            void onSuccessGetListUsers(ArrayList<UserInfo> listUsers);
            void onFailedGetListUsers(Throwable t);
        }

        interface onGetUserTransactions {
            void onSuccessGetUserTransactions(UserTransactionDto userTransactionDto);
            void onFailedGetUserTransactions(Throwable t);

        }

        interface onTicketDeposit{
            void onSuccessTicketDeposit();
            void onFailedTicketDeposit(Throwable t);
        }

        interface onTicketWithdraw{
            void onSuccessTicketWithdraw();
            void onFailedTicketWithdraw(Throwable t);

        }

        interface onDeactivateUser{
            void onSuccessDeactivateUser(Response<Void> response);
            void onFailedDeactivateUser(Throwable t);
        }

        interface onGetDeactivatedUsers{
            void onSuccesGetDeactivatedUsers(ArrayList<UserInfo> list);
            void onFailedGetDeactivatedUsers(Throwable t);
        }
    }

}
