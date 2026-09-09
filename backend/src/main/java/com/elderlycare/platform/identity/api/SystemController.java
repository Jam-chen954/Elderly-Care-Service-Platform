package com.elderlycare.platform.identity.api;

import com.elderlycare.platform.common.api.ApiResponse;
import com.elderlycare.platform.identity.service.CommunityService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/system")
public class SystemController {
    private final CommunityService communities;

    public SystemController(CommunityService communities) { this.communities = communities; }

    @GetMapping("/community")
    public ApiResponse<CommunityDto> community(Authentication authentication) {
        return ApiResponse.ok(communities.getCurrentCommunity(authentication));
    }
}
