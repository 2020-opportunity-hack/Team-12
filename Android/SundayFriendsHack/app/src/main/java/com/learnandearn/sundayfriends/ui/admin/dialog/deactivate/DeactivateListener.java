package com.learnandearn.sundayfriends.ui.admin.dialog.deactivate;

import com.learnandearn.sundayfriends.network.model.UserInfo;

public interface DeactivateListener {
    void onConfirmDeactivateClick(UserInfo userInfo, boolean deactivate);
}
