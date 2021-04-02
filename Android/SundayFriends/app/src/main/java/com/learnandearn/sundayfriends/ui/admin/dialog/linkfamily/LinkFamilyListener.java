package com.learnandearn.sundayfriends.ui.admin.dialog.linkfamily;

import com.learnandearn.sundayfriends.network.model.UserInfo;

public interface LinkFamilyListener {
    void onConfirmLinkFamilyClick(UserInfo userInfo, int familyIdToLink);
}
