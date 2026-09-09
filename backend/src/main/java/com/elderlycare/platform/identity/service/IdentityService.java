package com.elderlycare.platform.identity.service;

import com.elderlycare.platform.identity.domain.UserAccount;
import com.elderlycare.platform.identity.mapper.UserAccountMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class IdentityService implements UserDetailsService {
    private final UserAccountMapper mapper;

    public IdentityService(UserAccountMapper mapper) { this.mapper = mapper; }

    public UserAccount find(String username) {
        return mapper.selectOne(new LambdaQueryWrapper<UserAccount>().eq(UserAccount::getUsername, username));
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserAccount account = find(username);
        if (account == null) { throw new UsernameNotFoundException("账号或密码错误"); }
        return User.withUsername(account.getUsername()).password(account.getPasswordHash())
                .roles(account.getRole()).disabled(!"ACTIVE".equals(account.getStatus())).build();
    }
}
