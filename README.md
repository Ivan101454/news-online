# Задание: Система управления новостями
Запуск контейнеров:

В терминале перейдите в директорию с файлами Dockerfile и docker-compose.yml и выполните команду:
docker-compose up --build
Создадутся контейнера Redis, Postgres и приложения.
При запуске приложения по default профайлу заполнится БД. Затем в application.yml  необходимо поставить liquibase:
enabled: false
### !!!Сейчас добавлена конфигурация через spring cloud, запускать сначала Main модуля config-server и прописывать токен с него в application.yml модуля :api
### !!!В Докере должны быть запущены Редис и Постгрес контейнера 

### Приложение разбито на 2 модуля: *:api*, содержащий слой Repository и Controller,и *:core*, содержащий Service слой

### Начнем со :core
Были создан интерфейс ICrudService для создания контракта для CRUD операций + метод фильтра поиска по ключевым словам из
текстовых полей сущностей. Затем создан абстрактный класс CrudService реализующий сервисный слой этого интерфейса.
Также он содержит поля интерфейсов IRepository (для создания контракта для crud c БД), IFilterRepository(для контрак 
поиска по фильтру). 
Благодаря этому сервисный слой является универсальным, и подходит для любой сущности, потому что он реализован исключительно
на интерфейсах и дженериках. Поиск по фильтру ключевых слов тоже универсальный.
От этого абстрактного класс идет расширение на NewsService и UserService, дополнительно принимающий интерфейс IUserRepository,
для контракта описка по имени пользователя.(для Security)
### Переходим на модуль :api Repository
Слой Repository. Например NewsRepository наследует JPARepository, а также репозитории из модуля :core, и боагодаря что 
названия методов и сигнатура у Jpa совпадает с ними, мы получаем работающую реализацию. 
Также необходимо сконфигурированы бины сервис слоя в ServiceClassConfig.
Отдельно был созданы кастомный репозиторий IFilterEntityRepository для поиска по ключевым словам. Он наследует IFilterRepository
из сервисного слоя. А также его реализация IFilterEntityRepositoryImpl в той же директории, чтоб спринг его мог найти.
С помощью рефакторинга, дженериков и CriteriaApi был создан универсальный поиск по необходимым текстовым полям сущности,
достаточно подать файл, например NewsFilter, содержащий условие поиска, и фильтр автоматически определит заполненные поля,
создаст запрос и вернет список найденных совпадений.
### Были созданы сущности
Была реализована структора новости, указаны наименования столбцов в БД везде, по возможности везде была применена ленивая загрузка.
Сущность *News* предполагает, что сам текст новости будет просто ссылкой на файл с текстом.
Был создан слой Dto. Конвертацию сущность-дто и обратно производит NewsMapper.
### Был создан слой контроллеров 
NewsController для работы с новостями поиск, создание, удаление, а также с комментариями
UserController для регистрации пользователя, а также для изменения, удаления. 
Для запросов возвращающими списки реализована постраничность, с дефолтными, либо настраиваемыми объемами через @pathvariable
### Было добавлено Spring Security,
UserService из :core наследует UserDetailService и его метод, поиск юзера по username.
Enum Role, который применятся для сущности User, наследует GrantedAuthority. Имеются роли admin, journalist, subscriber.
WebSecurityConfig конфигурирует метода шифрования, а тажже URL, по которым могут ходит пользователи, с определенными ролями.
### В проекте используются профайлы prod, test, LRU, LFU, redis
### Подключена luqidbase
При запуске приложения (действует default профайл), накатываются скрипты из db/changelog. Они производят ddl и dml операции.
Все необходимые таблицы и записи в них.
### Для тестов ипользуется testcontainers,
который тоже автоматически запустит test профайл и заполнит бызу данных.
В интеграционных тестах реализован его вызвов и конфигурация  @DynamicPropertySource
### Реализованы кастомный алгоритмы LFU LRU. 
Реализация в директории Cache. Вызов через соответсвующий профайл. Применнены в тестах.
### Созданы различные Junit, IT, а также  WireMock тесты.
### Реализована логгирование контроллеров в аспекртном стиле
реализация в ru/clevertec/newsonline/aspect/ControllerAspect
### Предусмотрен выброс исключение в контроллерах
### Используется docker-compose.yml Dockerfile в корне проекта для создания контейнеров
### Подключен кеш провайдер Redis
сконфигурирован в ru/clevertec/newsonline/config/RedisConfig. Применен для метода findById
в абстрактном Crud классе в :core с помощью аннотации @Cacheable(value = "byIdCache", key = "#p0")
###  Код контроллеров документирован @JavaDoc
### 













