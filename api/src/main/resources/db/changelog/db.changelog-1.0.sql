--liquidbase formatted sql
--changeset table:1
create table news_online.author (
                                    author_id uuid not null,
                                    date_of_registration date,
                                    email varchar(255),
                                    lasname_author varchar(255),
                                    name_author varchar(255),
                                    phone_number varchar(255),
                                    primary key (author_id)
);
--changeset table:2
create table news_online.category (
                                      category_id uuid not null,
                                      section varchar(255) check (section in ('PEOPLE','CAR','INCIDENT','EVENT','FOOD','TRAVEL')),
                                      primary key (category_id)
);
--changeset table:3
create table news_online.comment (
                                     comment_id uuid not null,
                                     date_of_comment timestamp(6),
                                     text_comment varchar(255),
                                     author_id uuid,
                                     news_id uuid,
                                     primary key (comment_id)
);
--changeset table:4
create table news_online.news (
                                  news_id uuid not null,
                                  body_news varchar(255),
                                  date_of_news timestamp(6) default CURRENT_TIMESTAMP,
                                  header_news varchar(255),
                                  is_published boolean,
                                  short_description varchar(255),
                                  author_id uuid,
                                  category_id uuid,
                                  primary key (news_id)
);
--changeset table:5
create table news_online.news_picture (
                                          news_id uuid not null,
                                          picture_id uuid not null
);
--changeset table:6
create table news_online.picture (
                                     picture_id uuid not null,
                                     link_on_picture varchar(255),
                                     name_of_picture varchar(255),
                                     primary key (picture_id)
);
--changeset table:7
create table news_online.user (
                                  user_id uuid not null,
                                  login varchar(255),
                                  password varchar(255),
                                  username varchar(255),
                                  primary key (user_id)
);
--changeset update-table:8
alter table if exists news_online.comment
    add constraint FKh1gtv412u19wcbx22177xbkjp
        foreign key (author_id)
            references news_online.user;
--changeset update-table:9
alter table if exists news_online.comment
    add constraint FKnxm8x9npdhuwxv2x2wxsghm17
        foreign key (news_id)
            references news_online.news;
--changeset update-table:10
alter table if exists news_online.news
    add constraint FKfr9lepy44sqk5rm6221ngxxoc
        foreign key (author_id)
            references news_online.author;
--changeset update-table:11
alter table if exists news_online.news
    add constraint FKryugyuqj7jjkqd3byc5meoocy
        foreign key (category_id)
            references news_online.category;
--changeset update-table:12
alter table if exists news_online.news_picture
    add constraint FKgeijupaul1r5ugaatfa5ioq0c
        foreign key (picture_id)
            references news_online.news;
--changeset update-table:13
alter table if exists news_online.news_picture
    add constraint FK88bvgf0bf0ldwv0qj2nlulxjw
        foreign key (news_id)
            references news_online.picture;