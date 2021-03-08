package com.learnandearn.sundayfriends.ui.admin.dialog.activate;

import com.learnandearn.sundayfriends.network.model.UserInfo;

public interface ActivateListener {
    void onConfirmActivateClick(UserInfo userInfo, boolean activate);
}
