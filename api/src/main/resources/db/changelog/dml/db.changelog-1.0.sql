--liquidbase formatted sql
--changeset ivan101454:1
INSERT INTO news_online.author (email, last_name_author,
                                name_author, phone_number)
VALUES ('ivan@gmail.com', 'Ivanov', 'Ivan', '1234567890'),
       ('petr@yande.ru', 'Petrov', 'Petr', '0987654321'),
       ('sidor@mail.ru', 'Sidorov', 'Sidr', '1597534569');
--changeset ivan101454:2
INSERT INTO news_online.category (section)
VALUES ('PEOPLE'), ('CAR'), ('WALLET'), ('ESTATE'), ('TECHNOLOGY'), ('TRAVEL');
--changeset ivan101454:3
INSERT INTO news_online.user(login, password, username)
VALUES ('Bert@mail.ru', 'Leeniam124', 'Vand'), ('Maever@mail.ru', 'Negrovey456', 'Kkelot'),
       ('Chollian@mail.ru', 'Xavisono345', 'Telmai'), ('Quinlan@mail.ru', 'Kabir', 'Saxtone'),
       ('Iana@mail.ry', 'Lseanaed', 'Cheld'), ('Lama@mail.ru', 'Myrali', 'Jacquest'),
       ('Alline@mail.ru', 'Ippaa', 'Unnyacha'), ('Wilforb@mail.ru', 'Xant', 'Minayas'),
       ('Palohan@mail.ru', 'Ondrague', 'Arist');
--changeset ivan101454:4
INSERT INTO news_online.news(content_link, header_news, is_published, short_description, author_id, category_id)
SELECT 'static/text/news1.txt', 'Почему все хотят попасть на Щелкунчика', true, '«„Щелкунчик“ — это гениальное произведение». Закулисье самого ожидаемого балета года', au.author_id, cat.category_id
FROM news_online.author AS au
JOIN news_online.category AS cat ON section = 'PEOPLE'
where au.last_name_author = 'Ivanov';
--changeset ivan101454:5
INSERT INTO news_online.news(content_link, header_news, is_published, short_description, author_id, category_id)
SELECT 'static/text/news2.txt', 'Цель на 2025 - Выйти замуж. О чем мечтают белорусы.', true,'«В канун Нового года мы ждем елку, мандарины и, конечно же, мечтаем. Кто-то из нас хочет сняться в сериале, кто-то — собраться с силами и, наконец, пойти в спортзал, еще кто-то — обзавестись жильем. Вместе со «Спортмастером» расспросили белорусов об их самых заветных желаниях и отправили их Деду Морозу, чтобы все сбылось наверняка.', au.author_id, cat.category_id
FROM news_online.author AS au
JOIN news_online.category AS cat ON section = 'PEOPLE'
where au.last_name_author = 'Petrov';
--changeset ivan101454:6
INSERT INTO news_online.news(content_link, header_news, is_published, short_description, author_id, category_id)
SELECT 'static/text/news3.txt', 'Топ-10 (и еще немного) лучших сериалов 2024 года по версии подкаста «Прослушка»', true, '« Итак, встречайте новогодний выпуск и десятку лучших сериалов года по версии «Прослушки». Лондонская сталкерша, социопаты-гедонисты, пятеро Робертов Дауни — младших, готэмские мафиози, японские феодалы и снова игроки «в кальмара» — в общем, здесь все, что вы ожидаете и что не очень.', au.author_id, cat.category_id
FROM news_online.author AS au
JOIN news_online.category AS cat ON section = 'PEOPLE'
where au.last_name_author = 'Sidorov';
--changeset ivan101454:7
INSERT INTO news_online.news(content_link, header_news, is_published, short_description, author_id, category_id)
SELECT 'static/text/news4.txt', 'Топ-10 (и еще немного) лучших сериалов 2024 года по версии подкаста «Прослушка»', true, '« Итак, встречайте новогодний выпуск и десятку лучших сериалов года по версии «Прослушки». Лондонская сталкерша, социопаты-гедонисты, пятеро Робертов Дауни — младших, готэмские мафиози, японские феодалы и снова игроки «в кальмара» — в общем, здесь все, что вы ожидаете и что не очень.', au.author_id, cat.category_id
FROM news_online.author AS au
JOIN news_online.category AS cat ON section = 'PEOPLE'
where au.last_name_author = 'Ivanov';
--changeset ivan101454:8
INSERT INTO news_online.news(content_link, header_news, is_published, short_description, author_id, category_id)
SELECT 'static/text/news5.txt', 'Постараемся обойтись без «китайцев». Ищем подержанные электромобили', true, 'Хит сезона — новые или слегка подержанные «электрички» из Китая. Но на этот же бюджет можно поискать и более возрастные модели западных брендов. Посмотрели на «Автобарахолке» электромобили не старше 2019 года выпуска.', au.author_id, cat.category_id
FROM news_online.author AS au
JOIN news_online.category AS cat ON section = 'CAR'
where au.last_name_author = 'Petrov';
--changeset ivan101454:9
INSERT INTO news_online.news(content_link, header_news, is_published, short_description, author_id, category_id)
SELECT 'static/text/news6.txt', 'На базе Lada Vesta построят минивэн. Новые подробности', true, 'Сейчас «АвтоВАЗ» активно работает над запуском производства бюджетной модели Iskra, которая должна выйти на рынок уже в этом году. Далее по планам кроссовер на базе Vesta — его покажут этим летом, а продажи должны стартовать в 2026 году. А вот затем придет очередь минивэна.', au.author_id, cat.category_id
FROM news_online.author AS au
JOIN news_online.category AS cat ON section = 'CAR'
where au.last_name_author = 'Sidorov';
--changeset ivan101454:10
INSERT INTO news_online.news(content_link, header_news, is_published, short_description, author_id, category_id)
SELECT 'static/text/news7.txt', 'В Казахстане массовая авария: столкнулись 95 автомобилей', true, 'Как сообщает Telegram-канал местного национального оператора по управлению автомобильными дорогами страны «КазАвтоЖол», все случилось около 13:28 на 153-м километре трассы Астана — Петропавловск, в районе села Жанаталап. Причиной называют несоблюдение водителями скоростного режима и безопасной дистанции.', au.author_id, cat.category_id
FROM news_online.author AS au
JOIN news_online.category AS cat ON section = 'CAR'
where au.last_name_author = 'Ivanov';
--changeset ivan101454:10
INSERT INTO news_online.news(content_link, header_news, is_published, short_description, author_id, category_id)
SELECT 'static/text/news8.txt', 'Жители одной из высоток «Минск-Мира» воссоздали в своем доме… весь «Минск-Мир»', true, 'Еще десять лет назад украшать подъезды было совершенно не принято. На весь город можно было отыскать лишь пару мест общего пользования с гирляндами и елочками. И те смотрелись как некая диковинка: придумают же люди. Со временем все изменилось. И, думается, немалую роль в этом сыграли застройщики, которые начали возводить дома с изначально красивыми лобби. Добавь к ним пару новогодних штрихов — и уже хорошо. А если постараться…', au.author_id, cat.category_id
FROM news_online.author AS au
JOIN news_online.category AS cat ON section = 'ESTATE'
where au.last_name_author = 'Petrov';
--changeset ivan101454:11
INSERT INTO news_online.news(content_link, header_news, is_published, short_description, author_id, category_id)
SELECT 'static/text/news9.txt', 'Смотрите, какой таунхаус построят под Минском, недалеко от гольф-полей. Цена — от $1900 за «квадрат»', true, 'В «Зеленой гавани» под Минском построят двухэтажный таунхаус, который станет последним в квартале «Долина холмов». По проекту в нем будет всего пять квартир, но зато каких! Рассказываем, сколько будет стоить «квадрат» в современном таунхаусе вблизи гольф-полей.', au.author_id, cat.category_id
FROM news_online.author AS au
JOIN news_online.category AS cat ON section = 'ESTATE'
where au.last_name_author = 'Sidorov';
--changeset ivan101454:12
INSERT INTO news_online.news(content_link, header_news, is_published, short_description, author_id, category_id)
SELECT 'static/text/news10.txt', 'Арендодатель впервые за два года навестил квартиру и обнаружил, что жилец превратил ее в курятник', true, 'Владелец квартиры в китайском Шанхае пребывает в глубоком шоке после того, как после долгого перерыва навестил свою собственность. Оказалось, что арендатор устроил в жилье курятник с соответствующей грязью и запахом. Теперь хозяин будет вынужден делать в квартире ремонт.', au.author_id, cat.category_id
FROM news_online.author AS au
JOIN news_online.category AS cat ON section = 'ESTATE'
where au.last_name_author = 'Ivanov';
--changeset ivan101454:13
INSERT INTO news_online.news(content_link, header_news, is_published, short_description, author_id, category_id)
SELECT 'static/text/news11.txt', '«40 лет на третьем этаже». Как трудятся и зарабатывают мастера из дома быта', true, 'Заточить ножи, починить обувь, отстирать любимую вещь, подстричься или зашить порванную рубашку — мы привыкли бегать по разным местам, решая бытовые вопросы. А что если все это можно сделать за один раз и в одном здании? Таких локаций, где все услуги собраны под одной крышей, у нас хватает.', au.author_id, cat.category_id
FROM news_online.author AS au
JOIN news_online.category AS cat ON section = 'WALLET'
where au.last_name_author = 'Petrov';
--changeset ivan101454:14
INSERT INTO news_online.news(content_link, header_news, is_published, short_description, author_id, category_id)
SELECT 'static/text/news12.txt', '«У меня был только робот-пылесос, теперь и палка будет».', true, 'Мы спросили у наших читателей, чем порадовали их работодатели к Новому году. Одним достались подарки, о которых мечтаешь весь год, другим — как будто из категории «лишь бы что-то подарить». Все самое интересное мы собрали в этом материале.', au.author_id, cat.category_id
FROM news_online.author AS au
JOIN news_online.category AS cat ON section = 'WALLET'
where au.last_name_author = 'Sidorov';
--changeset ivan101454:15
INSERT INTO news_online.news(content_link, header_news, is_published, short_description, author_id, category_id)
SELECT 'static/text/news13.txt', '«Семья полтора года откладывала монеты в банку. Сколько накопила?».', true, 'Способом накопления денег в банке — но не в том, что с карточками и очередями, а в обычной стеклянной таре — поделилась белорусская семья в TikTok. Эксперимент завершился спустя полтора года: они насобирали полтора литра монет, наконец вскрыли копилку и посчитали сбережения.', au.author_id, cat.category_id
FROM news_online.author AS au
JOIN news_online.category AS cat ON section = 'WALLET'
where au.last_name_author = 'Ivanov';
--changeset ivan101454:16
INSERT INTO news_online.news(content_link, header_news, is_published, short_description, author_id, category_id)
SELECT 'static/text/news14.txt', '«Инсайдер назвал TDP видеокарт RTX 5090 и RTX 5080 — они заметно подросли.', true, 'Инсайдер hongxing202 раскрыл информацию о TDP официально не анонсированных видеокарт NVIDIA RTX 5090 и RTX 5080. Как следует из озвученных им данных, TDP для RTX 5090 составит 575 Ватт, что на 125 Ватт выше, чем у RTX 4090. TDP RTX 5080, в свою очередь, составляет 360 Ватт.', au.author_id, cat.category_id
FROM news_online.author AS au
JOIN news_online.category AS cat ON section = 'TECHNOLOGY'
where au.last_name_author = 'Petrov';
--changeset ivan101454:17
INSERT INTO news_online.news(content_link, header_news, is_published, short_description, author_id, category_id)
SELECT 'static/text/news15.txt', '«На видео показали портативную консоль Atari Gamestation Go', true, 'На грядущей выставке CES 2025 будет представлена портативная игровая консоль Atari Gamestation Go, пока же в сети появился небольшой видеотизер, посвященный новинке. К сожалению, информации о характеристиках, возможностях и цене Atari Gamestation Go на данный момент нет.', au.author_id, cat.category_id
FROM news_online.author AS au
JOIN news_online.category AS cat ON section = 'TECHNOLOGY'
where au.last_name_author = 'Sidorov';
--changeset ivan101454:18
INSERT INTO news_online.news(content_link, header_news, is_published, short_description, author_id, category_id)
SELECT 'static/text/news16.txt', '«Honor представила «облегченную» версию флагмана — смартфон Magic7 Lite с огромной батареей', true, 'Honor представила смартфон среднего уровня Magic7 Lite на базе чипа Snapdragon 6 Gen 1 и с батареей емкостью 6600 мАч (последний аспект является основным аппаратным отличием от прошлогоднего Magic6 Lite с его аккумулятором на 5300 мАч). Как утверждает производитель, 2% заряда аккумулятора достаточно для 50 минут работы.', au.author_id, cat.category_id
FROM news_online.author AS au
JOIN news_online.category AS cat ON section = 'TECHNOLOGY'
where au.last_name_author = 'Ivanov';
--changeset ivan101454:19
INSERT INTO news_online.news(content_link, header_news, is_published, short_description, author_id, category_id)
SELECT 'static/text/news17.txt', 'Сколько излучают смартфоны в 5G?', true, 'Исследователи из Швейцарии и Испании в рамках инициативы Project GOLIAT разработали методику измерения уровня излучения 5G-смартфонов. Исследования проводились на территории Швейцарии, где существует обширная сеть нового поколения. Интенсивность излучения измеряли в трех сценариях: когда смартфоны находятся в «режиме полета», во время активной загрузки данных и их отправке.', au.author_id, cat.category_id
FROM news_online.author AS au
JOIN news_online.category AS cat ON section = 'TECHNOLOGY'
where au.last_name_author = 'Petrov';
--changeset ivan101454:20
INSERT INTO news_online.news(content_link, header_news, is_published, short_description, author_id, category_id)
SELECT 'static/text/news18.txt', 'Власти Японии просят туристов не ехать в Токио и Киото. В качестве замены предлагают сельскую местность', true, 'Совет по туризму Японии призвал отдыхающих поменять Токио и Киото на другие города — Тохоку и Канадзава. Причиной такого решения стали австралийцы, который буквально наводнили самые популярные туристические места, пишет Guardian.', au.author_id, cat.category_id
FROM news_online.author AS au
JOIN news_online.category AS cat ON section = 'TRAVEL'
where au.last_name_author = 'Sidorov';
--changeset ivan101454:21
INSERT INTO news_online.news(content_link, header_news, is_published, short_description, author_id, category_id)
SELECT 'static/text/news19.txt', 'Таиланд разрешил белорусам получать электронные визы. Но сходить в консульство все равно придется', true, 'С 1 января Таиланд меняет визовые правила для белорусов: теперь для длительных (больше 2 недель) поездок в страну туристам нужно будет получить электронную визу за $40. Правда, от похода в консульство это пока не освободит.', au.author_id, cat.category_id
FROM news_online.author AS au
JOIN news_online.category AS cat ON section = 'TRAVEL'
where au.last_name_author = 'Ivanov';
--changeset ivan101454:22
INSERT INTO news_online.news(content_link, header_news, is_published, short_description, author_id, category_id)
SELECT 'static/text/news20.txt', 'Из Минска прямым рейсом можно будет слетать в популярный у туристов регион Китая. Уже в декабре', true, 'Вылеты вечерние, в 20:40. Время в пути составит 8 часов 20 минут, то есть в Сиане окажетесь только утром воскресенья. Билет в экономкласс (в одну сторону) стоит не такие уж экономные 564 евро — для прямых перелетов в самый центр Азии, впрочем, цена вполне стандартная.', au.author_id, cat.category_id
FROM news_online.author AS au
JOIN news_online.category AS cat ON section = 'TRAVEL'
where au.last_name_author = 'Ivanov';
--changeset ivan101454:23
INSERT INTO news_online.comment(text_comment, user_id, news_id)
SELECT 'Hello! It"s great to see you here! How"s your day going? 😊', us.user_id, ns.news_id
FROM news_online.news AS ns
JOIN news_online.user AS us ON username = 'Vand'
WHERE ns.content_link = 'static/text/news1.txt';
--changeset ivan101454:24
INSERT INTO news_online.comment(text_comment, user_id, news_id)
SELECT 'ссылки ведут на новости какие-то неинтересные', us.user_id, ns.news_id
FROM news_online.news AS ns
JOIN news_online.user AS us ON username = 'Kkelot'
WHERE ns.content_link = 'static/text/news1.txt';
--changeset ivan101454:25
INSERT INTO news_online.comment(text_comment, user_id, news_id)
SELECT 'Здравствуйте! А на платформе iOS у всех проблемы с просмотром? На моем телефоне ни одно видео не работает😭 может быть есть этому решение?😥', us.user_id, ns.news_id
FROM news_online.news AS ns
JOIN news_online.user AS us ON username = 'Telmai'
WHERE ns.content_link = 'static/text/news1.txt';
--changeset ivan101454:26
INSERT INTO news_online.comment(text_comment, user_id, news_id)
SELECT 'попробуйте нажать на стрелку в правом верхнем углу, пока это единственное решение проблемы;(', us.user_id, ns.news_id
FROM news_online.news AS ns
JOIN news_online.user AS us ON username = 'Saxtone'
WHERE ns.content_link = 'static/text/news1.txt';
--changeset ivan101454:27
INSERT INTO news_online.comment(text_comment, user_id, news_id)
SELECT 'Повторите попытку позже.', us.user_id, ns.news_id
FROM news_online.news AS ns
JOIN news_online.user AS us ON username = 'Cheld'
WHERE ns.content_link = 'static/text/news1.txt';
--changeset ivan101454:28
INSERT INTO news_online.comment(text_comment, user_id, news_id)
SELECT 'Когда можно будет смотреть? и что это за ограничения', us.user_id, ns.news_id
FROM news_online.news AS ns
JOIN news_online.user AS us ON username = 'Jacquest'
WHERE ns.content_link = 'static/text/news1.txt';
--changeset ivan101454:29
INSERT INTO news_online.comment(text_comment, user_id, news_id)
SELECT 'Спасибо за понимание', us.user_id, ns.news_id
FROM news_online.news AS ns
JOIN news_online.user AS us ON username = 'Unnyacha'
WHERE ns.content_link = 'static/text/news1.txt';
--changeset ivan101454:30
INSERT INTO news_online.comment(text_comment, user_id, news_id)
SELECT 'Ссылка на фильм в телеграмм', us.user_id, ns.news_id
FROM news_online.news AS ns
JOIN news_online.user AS us ON username = 'Minayas'
WHERE ns.content_link = 'static/text/news1.txt';
--changeset ivan101454:31
INSERT INTO news_online.comment(text_comment, user_id, news_id)
SELECT 'та же ошибка на сайте', us.user_id, ns.news_id
FROM news_online.news AS ns
JOIN news_online.user AS us ON username = 'Arist'
WHERE ns.content_link = 'static/text/news1.txt';
