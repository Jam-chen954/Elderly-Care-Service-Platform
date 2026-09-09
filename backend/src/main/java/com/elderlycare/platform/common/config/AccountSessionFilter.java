package com.elderlycare.platform.common.config;

import com.elderlycare.platform.common.api.ApiResponse;
import com.elderlycare.platform.identity.domain.UserAccount;
import com.elderlycare.platform.identity.service.IdentityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/** Recheck account state and role on every session request, including existing sessions. */
final class AccountSessionFilter extends OncePerRequestFilter {
    private final IdentityService identities;
    private final ObjectMapper json;

    AccountSessionFilter(IdentityService identities, ObjectMapper json) {
        this.identities = identities;
        this.json = json;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            UserAccount account = identities.find(authentication.getName());
            if (account == null || !"ACTIVE".equals(account.getStatus())) {
                if (req.getSession(false) != null) { req.getSession(false).invalidate(); }
                SecurityContextHolder.clearContext();
                SecurityConfig.write(json, res, 401, ApiResponse.error("SESSION_REVOKED", "账号已停用，请联系社区"));
                return;
            }
            var fresh = UsernamePasswordAuthenticationToken.authenticated(authentication.getName(), null,
                    List.of(new SimpleGrantedAuthority("ROLE_" + account.getRole())));
            var context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(fresh);
            SecurityContextHolder.setContext(context);
        }
        chain.doFilter(req, res);
    }
}
