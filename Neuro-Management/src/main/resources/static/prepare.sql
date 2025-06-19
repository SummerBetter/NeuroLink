CREATE TABLE users (
                       user_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户ID，主键，自增',
                       username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名，唯一',
                       email VARCHAR(100) NOT NULL UNIQUE COMMENT '邮箱，唯一',
                       password_hash VARCHAR(255) NOT NULL COMMENT '密码哈希（使用 bcrypt 或 Argon2）',
                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                       updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                       status ENUM('ACTIVE', 'INACTIVE', 'SUSPENDED') NOT NULL DEFAULT 'active' COMMENT '用户状态：激活、未激活、暂停',
                       PRIMARY KEY (user_id),
                       INDEX idx_email (email),
                       INDEX idx_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户基本信息表';