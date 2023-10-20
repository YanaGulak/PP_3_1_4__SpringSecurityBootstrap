CREATE TABLE IF NOT EXISTS users
(
    `id`
                BIGINT
                             NOT
                                 NULL
        AUTO_INCREMENT,
    `name`
                VARCHAR(45)  NOT NULL,
    `last_name` VARCHAR(45)  NOT NULL,
    `age`       INT          NOT NULL,
    `email`     VARCHAR(45)  NOT NULL,
    `username`  VARCHAR(45)  NOT NULL,
    `password`  VARCHAR(100) NOT NULL,
    PRIMARY KEY
        (
         `id`
            ),
    UNIQUE INDEX `username_UNIQUE`
        (
         `username` ASC
            ) VISIBLE
);

CREATE TABLE IF NOT EXISTS roles
(
    id
        bigint
        auto_increment,
    role
        varchar(25) not null,
    primary key
        (
         id
            ),
    UNIQUE INDEX `role_UNIQUE`
        (
         `role` ASC
            ) VISIBLE
);


create table users_roles
(
    user_id bigint not null,
    role_id bigint null,
    constraint users_roles_pk
        unique (role_id, user_id),
    constraint users_roles_roles_id_fk
        foreign key (role_id) references roles (id),
    constraint users_roles_users_id_fk
        foreign key (user_id) references users (id)
);
INSERT INTO roles (role)
VALUES ('ROLE_USER');

INSERT INTO roles (role)
VALUES ('ROLE_ADMIN');

ALTER TABLE roles
    add constraint roles_pk
        unique (role);


INSERT INTO users (name, last_name, age, email, username, password)
VALUES ('Админ', 'Всемогущий', 45, 'admin@mail.ru', 'admin',
        '$2a$08$rpiT312X4Vq5.3U685EI9e4UrTvrB2hnr2fF1dbviq1p.nF5bnCtC');
INSERT INTO users (name, last_name, age, email, username, password)
VALUES ('Пользователь', 'Обыкновенный', 25, 'user@mail.ru', 'user',
        '$2a$08$j.2li2T86pya1o/RqPAMoO1SadL/nA/9qVi/RMLcgsr6T2XK00fvW');

INSERT users_roles (user_id, role_id)
VALUES (1, 1);
INSERT users_roles (user_id, role_id)
VALUES (1, 2);
INSERT users_roles (user_id, role_id)
VALUES (2, 1);
