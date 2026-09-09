-- Foundation only. Business tables will be introduced with their own reviewed migrations.
CREATE TABLE community (
    id BIGINT NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
);

CREATE TABLE user_account (
    id BIGINT NOT NULL PRIMARY KEY,
    username VARCHAR(64) NOT NULL,
    password_hash VARCHAR(100) NOT NULL,
    display_name VARCHAR(100) NOT NULL,
    role VARCHAR(32) NOT NULL,
    community_id BIGINT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    version BIGINT NOT NULL DEFAULT 0,
    CONSTRAINT uk_user_account_username UNIQUE (username),
    CONSTRAINT fk_user_account_community FOREIGN KEY (community_id) REFERENCES community(id),
    CONSTRAINT ck_user_account_role CHECK (role IN ('ELDER', 'FAMILY', 'STAFF', 'COMMUNITY_OPERATOR', 'DUTY_OFFICER', 'PLATFORM_ADMIN', 'AUDITOR')),
    CONSTRAINT ck_user_account_status CHECK (status IN ('ACTIVE', 'DISABLED'))
);
CREATE INDEX idx_user_account_community ON user_account(community_id, status);
