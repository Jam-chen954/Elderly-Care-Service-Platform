package com.elderlycare.platform.identity.service;

import com.elderlycare.platform.common.api.BusinessException;
import com.elderlycare.platform.identity.domain.UserAccount;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class CommunityAccess {
    private final IdentityService identities;

    public CommunityAccess(IdentityService identities) { this.identities = identities; }

    public UserAccount current(Authentication authentication) {
        UserAccount account = authentication == null ? null : identities.find(authentication.getName());
        if (account == null || !"ACTIVE".equals(account.getStatus())) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED, "UNAUTHENTICATED", "请重新登录");
        }
        return account;
    }

    public long requireCommunity(Authentication authentication) {
        Long communityId = current(authentication).getCommunityId();
        if (communityId == null) {
            throw new BusinessException(HttpStatus.FORBIDDEN, "COMMUNITY_REQUIRED", "账号尚未分配社区");
        }
        return communityId;
    }
}
