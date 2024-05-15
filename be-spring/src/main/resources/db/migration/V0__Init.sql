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

INSERT INTO user_user (id, username, password) VALUES (1, 'admin', '');
INSERT INTO user_user_role (user_id, role_id) VALUES (1, 1);

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

CREATE TABLE ship_ship (
    `id` int(10) NOT NULL AUTO_INCREMENT,
    `name` varchar(50) DEFAULT NULL,
    `registration` varchar(50) DEFAULT NULL,
    `owner_id` int(10) DEFAULT NULL,
    CONSTRAINT PK_ship_ship_id PRIMARY KEY (`id`),
    CONSTRAINT FK_ship_ship__owner_id FOREIGN KEY (`owner_id`) REFERENCES `user_user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE race_crew (
    `id` int(10) NOT NULL AUTO_INCREMENT,
    `ship_id` int(10) DEFAULT NULL,
    `race_id` int(10) DEFAULT NULL,
    `status` varchar(15) DEFAULT NULL,
    `position` int(10) DEFAULT NULL,
    `finishing_time` time DEFAULT NULL,
    CONSTRAINT PK_race_crew_id PRIMARY KEY (`id`),
    UNIQUE KEY UK_race_crew__ship_race_unique (`ship_id`, `race_id`),
    CONSTRAINT FK_race_crew__ship_id FOREIGN KEY (`ship_id`) REFERENCES `ship_ship` (`id`),
    CONSTRAINT FK_race_crew__race_id FOREIGN KEY (`race_id`) REFERENCES `race_race` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE race_crew_user (
    `id` int(10) NOT NULL AUTO_INCREMENT,
    `crew_id` int(10) DEFAULT NULL,
    `user_id` int(10) DEFAULT NULL,
    `status` varchar(15) DEFAULT NULL,
    CONSTRAINT PK_race_crew_user_id PRIMARY KEY (`id`),
    UNIQUE KEY UK_race_crew_user__crew_user_unique (`crew_id`, `user_id`),
    CONSTRAINT FK_race_crew_user__crew_id FOREIGN KEY (`crew_id`) REFERENCES `race_crew` (`id`),
    CONSTRAINT FK_race_crew_user__user_id FOREIGN KEY (`user_id`) REFERENCES `user_user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE race_race_course (
    `race_id` int(10) NOT NULL,
    `buoy1long` decimal(20, 10) DEFAULT NULL,
    `buoy1lat` decimal(20, 10) DEFAULT NULL,
    `buoy2long` decimal(20, 10) DEFAULT NULL,
    `buoy2lat` decimal(20, 10) DEFAULT NULL,
    `buoy3long` decimal(20, 10) DEFAULT NULL,
    `buoy3lat` decimal(20, 10) DEFAULT NULL,
    `center_long` decimal(20, 10) DEFAULT NULL,
    `center_lat` decimal(20, 10) DEFAULT NULL,
    `zoom` int(10) DEFAULT NULL,
    `wind_angle` int(10) DEFAULT NULL,
    CONSTRAINT PK_race_race_course_id PRIMARY KEY (`race_id`),
    CONSTRAINT FK_race_race_course__race_id FOREIGN KEY (`race_id`) REFERENCES `race_race` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;