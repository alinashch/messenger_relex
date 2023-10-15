CREATE TABLE IF NOT EXISTS chat_room
(
    chat_room_id BIGSERIAL PRIMARY KEY UNIQUE

);
create table IF NOT EXISTS friend
(
    user_id   bigint not null
        references user_info,
    friend_id bigint not null
        references user_info
);
create table IF NOT EXISTS message
(
    message_id   bigserial
        primary key,
    chat_room_id bigint
        references chat_room,
    user_id      bigint
        references user_info,
    message      varchar(255) not null
);

create table IF NOT EXISTS refresh_token
(
    token      uuid                        not null
        primary key,
    valid_till timestamp(6) with time zone not null,
    user_id    bigint
        references user_info
);
create table IF NOT EXISTS role
(
    role_id bigserial
        primary key,
    name    varchar(255) not null
        unique
);

create table IF NOT EXISTS user_chat_room
(
    chat_room_id bigint not null
        references chat_room,
    user_id      bigint not null
        references user_info,
    primary key (chat_room_id, user_id)
);

create table IF NOT EXISTS user_info
(
    user_id                                bigserial
        primary key,
    email                                  varchar(255) not null
        unique,
    first_name                             varchar(255) not null,
    is_verified                            boolean      not null,
    last_name                              varchar(255) not null,
    nickname                               varchar(255) not null
        unique,
    password_hash                          varchar(255) not null,
    login                                  varchar(255) not null
        unique,
    is_active                              boolean      not null,
    is_can_receive_message_from_not_friend boolean      not null,
    is_show_friends                        boolean      not null
);

create table IF NOT EXISTS user_role
(
    user_id bigint not null
        references user_info,
    role_id bigint not null
        references role,
    primary key (user_id, role_id)
);
create table IF NOT EXISTS verification
(
    verification_id uuid                        not null
        primary key,
    code            uuid                        not null
        unique,
    valid_till      timestamp(6) with time zone not null,
    user_id         bigint
        references user_info
);