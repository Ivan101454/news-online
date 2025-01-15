--liquidbase formatted sql
--changeset ivan101454:0.2
CREATE SCHEMA IF NOT EXISTS security;
--changeset ivan101454:0.3
DROP EXTENSION IF EXISTS "uuid-ossp";
--changeset ivan101454:0.4
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
--changeset ivan101454:1
create table security.user
(
    user_id  uuid DEFAULT uuid_generate_v4(),
    login    varchar(255),
    password varchar(255),
    username varchar(255),
    role varchar(255),
    primary key (user_id),
    unique (username)
);