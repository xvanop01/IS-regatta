CREATE TABLE user_user (
    `id` int(10) NOT NULL AUTO_INCREMENT,
    `username` varchar(50) NOT NULL,
    `password` varchar(255) DEFAULT NULL,
    CONSTRAINT PK_user_user_id PRIMARY KEY (`id`),
    UNIQUE KEY UK_user_user_username (`username`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE user_role (
    `id` int(10) NOT NULL AUTO_INCREMENT,
    `role` varchar(255) DEFAULT NULL,
    CONSTRAINT PK_user_role_id PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

INSERT INTO user_role (id, role) VALUES (1, 'ADMIN');
INSERT INTO user_role (id, role) VALUES (2, 'ORGANIZER');
INSERT INTO user_role (id, role) VALUES (3, 'USER');

CREATE TABLE user_user_role (
    `user_id` int(10) NOT NULL,
    `role_id` int(10) NOT NULL,
    CONSTRAINT PK_user_user_role_id PRIMARY KEY (`user_id`, `role_id`),
    CONSTRAINT FK_user_user_role__user_id FOREIGN KEY (`user_id`) REFERENCES `user_user` (`id`),
    CONSTRAINT FK_user_user_role__role_id FOREIGN KEY (`role_id`) REFERENCES `user_role` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;