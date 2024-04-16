CREATE TABLE user_user (
    `id` int(10) NOT NULL AUTO_INCREMENT,
    `username` varchar(50) NOT NULL,
    `password` varchar(255) DEFAULT NULL,
    `email` varchar(255) DEFAULT NULL,
    `full_name` varchar(255) DEFAULT NULL,
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

CREATE TABLE race_race (
    `id` int(10) NOT NULL AUTO_INCREMENT,
    `name` varchar(50) NOT NULL,
    `location` varchar(50) DEFAULT NULL,
    `date` date DEFAULT NULL,
    `sign_up_until` date DEFAULT NULL,
    `description` varchar(255) DEFAULT NULL,
    `is_public` bit(1) NOT NULL,
    `organizer_id` int(10) NOT NULL,
    CONSTRAINT PK_race_race_id PRIMARY KEY (`id`),
    CONSTRAINT FK_race_race__organizer_id FOREIGN KEY (`organizer_id`) REFERENCES `user_user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE race_race_signed (
    `id` int(10) NOT NULL AUTO_INCREMENT,
    `race_id` int(10) NOT NULL,
    `user_id` int(10) NOT NULL,
    `status` varchar(15) DEFAULT NULL,
    CONSTRAINT PK_race_race_signed_id PRIMARY KEY (`id`),
    UNIQUE KEY UK_race_race_signed__race_user_unique (`race_id`, `user_id`),
    CONSTRAINT FK_race_race_signed__race_id FOREIGN KEY (`race_id`) REFERENCES `race_race` (`id`),
    CONSTRAINT FK_race_race_signed__user_id FOREIGN KEY (`user_id`) REFERENCES `user_user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;