package com.elderlycare.platform.identity.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/** Initializes synthetic accounts only when the developer explicitly enables the dev profile. */
@Component
@Profile("dev")
public class DevelopmentDataInitializer implements ApplicationRunner {
    private final JdbcTemplate jdbc;
    private final PasswordEncoder encoder;
    private final String password;

    public DevelopmentDataInitializer(JdbcTemplate jdbc, PasswordEncoder encoder,
            @Value("${app.dev-password}") String password) {
        this.jdbc = jdbc;
        this.encoder = encoder;
        this.password = password;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (password.length() < 12 || password.length() > 64) {
            throw new IllegalStateException("Set APP_DEV_PASSWORD to 12-64 characters before starting dev profile");
        }
        Integer count = jdbc.queryForObject("SELECT COUNT(*) FROM community WHERE id = 10001", Integer.class);
        if (count != null && count == 0) {
            jdbc.update("INSERT INTO community (id, name) VALUES (10001, ?)", "开发示例社区");
        }
        Integer accounts = jdbc.queryForObject("SELECT COUNT(*) FROM user_account WHERE username = 'dev.operator'", Integer.class);
        if (accounts != null && accounts == 0) {
            jdbc.update("INSERT INTO user_account (id, username, password_hash, display_name, role, community_id) VALUES (?, ?, ?, ?, ?, ?)",
                    10001L, "dev.operator", encoder.encode(password), "开发运营账号", "COMMUNITY_OPERATOR", 10001L);
        }
    }
}
