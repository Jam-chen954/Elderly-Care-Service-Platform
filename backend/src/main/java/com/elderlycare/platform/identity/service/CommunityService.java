package com.elderlycare.platform.identity.service;

import com.elderlycare.platform.common.api.BusinessException;
import com.elderlycare.platform.identity.api.CommunityDto;
import com.elderlycare.platform.identity.mapper.CommunityMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommunityService {
    private final CommunityAccess access;
    private final CommunityMapper mapper;

    public CommunityService(CommunityAccess access, CommunityMapper mapper) {
        this.access = access;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public CommunityDto getCurrentCommunity(Authentication authentication) {
        // Derive the data scope from the authenticated account; never accept a client-selected community.
        var community = mapper.findActiveById(access.requireCommunity(authentication));
        if (community == null) {
            throw new BusinessException(HttpStatus.FORBIDDEN, "COMMUNITY_UNAVAILABLE", "所属社区不可用，请联系管理员");
        }
        return new CommunityDto(community.id().toString(), community.name());
    }
}
