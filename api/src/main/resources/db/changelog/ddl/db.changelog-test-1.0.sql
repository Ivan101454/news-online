--liquidbase formatted sql
--changeset ivan101454:0.2
CREATE SCHEMA IF NOT EXISTS news_online;
--changeset ivan101454:0.3
DROP EXTENSION IF EXISTS "uuid-ossp";
--changeset ivan101454:0.4
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
--changeset ivan101454:1
create table news_online.author
(
    author_id            uuid      DEFAULT uuid_generate_v4(),
    date_of_registration TIMESTAMP DEFAULT current_timestamp,
    email                varchar(255),
    last_name_author     varchar(255),
    name_author          varchar(255),
    phone_number         varchar(255),
    primary key (author_id)
);
--changeset ivan101454:2
create table news_online.category
(
    category_id uuid DEFAULT uuid_generate_v4(),
    section     varchar(255),
    primary key (category_id)
);
--changeset ivan101454:3
create table news_online.comment
(
    comment_id      uuid         DEFAULT uuid_generate_v4(),
    date_of_comment timestamp(6) DEFAULT current_timestamp,
    text_comment    varchar(255),
    user_id         uuid,
    news_id         uuid,
    primary key (comment_id)
);
--changeset ivan101454:4
create table news_online.news
(
    news_id           uuid         DEFAULT uuid_generate_v4(),
    content_link      varchar(255),
    date_of_news      timestamp(6) default CURRENT_TIMESTAMP,
    header_news       varchar(255),
    is_published      boolean,
    short_description varchar(500),
    author_id         uuid,
    category_id       uuid,
    primary key (news_id)
);
--changeset ivan101454:5
create table news_online.news_picture
(
    news_id    uuid DEFAULT uuid_generate_v4(),
    picture_id uuid not null
);
--changeset ivan101454:6
create table news_online.picture
(
    picture_id      uuid DEFAULT uuid_generate_v4(),
    link_on_picture varchar(255),
    name_of_picture varchar(255),
    primary key (picture_id)
);
--changeset ivan101454:7
create table news_online.user
(
    user_id  uuid DEFAULT uuid_generate_v4(),
    login    varchar(255),
    password varchar(255),
    username varchar(255),
    role varchar(255),
    primary key (user_id),
    unique (username)
);
--changeset ivan101454:8
alter table if exists news_online.comment
    add constraint FKh1gtv412u19wcbx22177xbkjp
        foreign key (user_id)
            references news_online.user;
--changeset ivan101454:9
alter table if exists news_online.comment
    add constraint FKnxm8x9npdhuwxv2x2wxsghm17
        foreign key (news_id)
            references news_online.news;
--changeset ivan101454:10
alter table if exists news_online.news
    add constraint FKfr9lepy44sqk5rm6221ngxxoc
        foreign key (author_id)
            references news_online.author;
--changeset ivan101454:11
alter table if exists news_online.news
    add constraint FKryugyuqj7jjkqd3byc5meoocy
        foreign key (category_id)
            references news_online.category;
--changeset ivan101454:12
alter table if exists news_online.news_picture
    add constraint FKgeijupaul1r5ugaatfa5ioq0c
        foreign key (picture_id)
            references news_online.news;
--changeset ivan101454:13
alter table if exists news_online.news_picture
    add constraint FK88bvgf0bf0ldwv0qj2nlulxjw
        foreign key (news_id)
            references news_online.picture;