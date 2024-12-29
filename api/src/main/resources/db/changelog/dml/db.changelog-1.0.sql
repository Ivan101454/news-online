--liquidbase formatted sql
--changeset ivan101454:1
INSERT INTO news_online.author (email, last_name_author,
                                name_author, phone_number )
VALUES ('ivan@gmail.com', 'Ivanov', 'Ivan', '1234567890'),
       ('petr@yande.ru', 'Petrov', 'Petr', '0987654321'),
       ('sidor@mail.ru', 'Sidorov', 'Sidr', '1597534569');
