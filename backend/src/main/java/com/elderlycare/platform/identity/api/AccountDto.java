package com.elderlycare.platform.identity.api;

import com.elderlycare.platform.identity.domain.UserAccount;

public record AccountDto(String id, String displayName, String role, String communityId) {
    public static AccountDto from(UserAccount account) {
        return new AccountDto(account.getId().toString(), account.getDisplayName(), account.getRole(),
                account.getCommunityId() == null ? null : account.getCommunityId().toString());
    }
}
