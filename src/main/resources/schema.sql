CREATE TABLE IF NOT EXISTS user (
    id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '唯一主键',
    nick_name VARCHAR(100) comment '昵称',
    create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    phone VARCHAR(100) NOT NULL COMMENT '手机号',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    avatar VARCHAR(500) NULL COMMENT '头像',
    PRIMARY KEY (`id`),
    KEY `idx_nick_name` (`nick_name`),
    KEY `idx_phone` (`phone`)
);

CREATE TABLE IF NOT EXISTS biji_round_info (
    id      bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '唯一主键',
    room_id bigint NOT NULL COMMENT '房间ID',
    status  VARCHAR(100) NOT NULL COMMENT '回合状态',
    create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    start_time datetime NULL COMMENT '开始时间',
    end_time datetime NULL COMMENT '终止时间',
    data text COMMENT '回合数据',
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS biji_room_info (
     id      bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '唯一主键',
     status  VARCHAR(100) NOT NULL COMMENT '房间状态',
     main_user_id  bigint NOT NULL COMMENT '房主ID',
     create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
     start_time datetime NULL COMMENT '开始时间',
     end_time datetime NULL COMMENT '终止时间',
     data text COMMENT '房间数据',
     PRIMARY KEY (`id`),
     KEY `idx_main_user_id` (`main_user_id`)
);

CREATE TABLE IF NOT EXISTS biji_user_room_ref (
      id      bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '唯一主键',
      user_id  bigint NOT NULL COMMENT 'userID',
      room_id bigint NOT NULL COMMENT '房间ID',
      PRIMARY KEY (`id`),
      KEY `idx_user_id` (`user_id`),
      KEY `idx_room_id` (`room_id`)
);
