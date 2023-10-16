# Messenger Relex
Требуется спроектировать и реализовать сервис, позволяющий пользователям отправлять сообщения друг другу (аналог мессенджера).

Приложение необходимо разработать в виде RESTful API на языке Java с использованием фреймворка Spring Boot. Данные, получаемые и отправляемые приложением, должны быть в формате JSON.

# Видео представление

https://drive.google.com/file/d/1HO80c64dAvmT1SgSjSx3a9_W809q_ikp/view?usp=sharing

# Было реализовано
- регистрация пользователя(указать логин, ник, имя, фамилию, почту, пароль)
- хэширование паролей
- подтверждение почты через ссылку  в письме, отправленном на указанную почту
- возможность войти в систему при помощи логина и пароля
- API, позволяющий залогиниться в системе и сохраняющее информацию о сессии
- поддержка Spring Security
- информация о сессии хранится в JWT токенах и передается в HTTP хэдерах
- API, позволяющий завершить текущую сессию и разлогиниться
- механизмы защиты от обхода разлогина
- возможность обновить данные своего профиля, такие как никнейм, имя, фамилию, email
- при изменении email есть подтверждение изменения ссылкой на указанный новый email
- API, позволяющий обновить пароль
- API, позволяющий удалить аккаунт пользователя
- реализован перевод профиля в статус “Не активен” с дальнейшей возможностью восстановить профиль в течение некоторого времени
- API, позволяющий отправить другому пользователю сообщение, реализована проверка на существование пользователя
- API, позволяющий просматривать историю сообщений с конкретным пользователем
- API, позволяющий просматривать друзей, а также добавлять в друзья другого пользователя
- есть возможность ограничивать получение сообщений только своим кругом друзей
- есть возможность просматривать друзей другого пользователя, и, соответственно, возможность скрывать свой список друзей
- использование базы данных PostgreSQL
- документирование запросов через Swagger

# Swagger
Для просмотра swagger документации перейдите по ссылке: http://localhost:8080/swagger-ui/index.html

# Стек технологий
- Java
- Gradle
- Liquibase
- PostgreSQL
- Swagger
- Spring Boot
- Spring Security
- Netty-socketio
- Samskivert
- Lombok
- Mapstruct

# База данных
> Для тестирования приложения были созданы следующие таблицы (создание происходило непосредственно при первом запуске проекта с помощью файла `db/changelog/db.changelog-master.yaml`, sql исходники находятся в `db/changelog/changeset/tables` )


# Запросы
## Authentication API
## Регистрация нового пользователя
- POST запрос по адресу http://localhost:8080/auth/register
> Примеры запросов
1. Корректный запрос
- request body:
  `{
  "nickname":"alina_shch",
  "login":"alina-alina",
  "password":"12345678",
  "repeatPassword":"12345678",
  "email":"alina280702@mail.ru",
  "firstName":"Алина",
  "lastName":"Щербинина"
  }`
- response (status: 200 OK) :
  `{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxNyIsImV4cCI6MTY5NzQwMzYwMSwicm9sZXMiOlsiVVNFUiJdLCJlbWFpbCI6ImFsaW5hMjgwNzAyQG1haWwucnUiLCJsb2dpbiI6ImFsaW5hLWFsaW5hIiwibmlja25hbWUiOiJhbGluYV9zaGNoIiwiZnVsbE5hbWUiOiLQkNC70LjQvdCwINCp0LXRgNCx0LjQvdC40L3QsCJ9.d_ID9UpGlNRjzN4qq5XrMvlxfz-F0aiiB0p1JEqHKmw",
  "refreshToken": "ddff5d25-2ce8-4534-a91b-e45f166a6ccb"
  }`

2. повторяющийся ник
- request body:
  `{
  "nickname":"petp",
  "login":"test",
  "password":"12345678",
  "repeatPassword":"12345678",
  "email":"test@mail.ru",
  "firstName":"test",
  "lastName":"test"
  }`
- response (status: 409 Conflict) :
  `    "message": "User with this nickname already exists",
  `
3. повторяющийся логин
- request body:
  `{
  "nickname":"test",
  "login":"petr-petrovich",
  "password":"12345678",
  "repeatPassword":"12345678",
  "email":"test@mail.ru",
  "firstName":"test",
  "lastName":"test"
  }`
- response (status: 409 Conflict) :
  `        "message": "User with this login already exists",
  `
4. не совпадают пароли
- request body:
  `{
  "nickname":"test",
  "login":"test",
  "password":"12345678",
  "repeatPassword":"12345678xaxax",
  "email":"test@mail.ru",
  "firstName":"test",
  "lastName":"test"
  }`
- response (status: 400 Bad Request) :
  `           "message": "Password does not match",
  `
5. Повторяющаяся почта
- request body:
  `{
  "nickname":"test",
  "login":"test",
  "password":"12345678",
  "repeatPassword":"12345678",
  "email":"petya@mail.ru",
  "firstName":"test",
  "lastName":"test"
  }`
- response (status: 409 Conflict) :
  `               "message": "User with this email already exists",

`
## Подтверждение почты
Без подтверждения почты не доступны функции мессенджера
- POST запрос по адресу http://localhost:8080/auth/verify/{code}, где {code}-код из письма.
  В примере 1. пришел код  5ab62be4-9c4d-42c3-9973-8fb96971e05b. Следовательно запрос на адрес http://localhost:8080/auth/verify/5ab62be4-9c4d-42c3-9973-8fb96971e05b
> Примеры запросов
1. Корректный код
- request body:
  `{
  }`
- response (status: 201 Created) :
  ` `
2. У кода закончился срок действия
- request body:
  `{
  }`
- response (status: 201 Created) :
  `     "message": "This verification code does not exist",
  `
## Повторная отправка кода на почту
- POST запрос по адресу http://localhost:8080/auth/resend-code
И в bearer token передать токен, полученный при регистрации.
- ![img.png](img.png)
> Примеры запросов
1. Корректный токен
- header Authorization: `{eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyNSIsImV4cCI6MTY5NzQ1MjY4MCwicm9sZXMiOlsiVVNFUiJdLCJlbWFpbCI6ImFsaW5hMjgwNzAyQG1haWwucnUiLCJsb2dpbiI6ImFsaW5hLWFsaW5hIiwibmlja25hbWUiOiJhbGluYV9zaGNoIiwiZnVsbE5hbWUiOiLQkNC70LjQvdCwINCp0LXRgNCx0LjQvdC40L3QsCJ9.InM9CO2j2v-XBfM2-APwtUqJQboOFP_M14xCZwVcw-8}`
- request body:
  `{
  }`
- response (status: 200 Ok) :
  `     
  `
2. некорректный токен
- header Authorization: `{122222222}`
- request body:
  ``
- response (status: 403 Forbidden) :
  `     {"message": "The token was expected to have 3 parts, but got 0."
  }
  `
3. Время дейсвия токена закончилось 
- header Authorization: `{eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyNSIsImV4cCI6MTY5NzQ1MjY4MCwicm9sZXMiOlsiVVNFUiJdLCJlbWFpbCI6ImFsaW5hMjgwNzAyQG1haWwucnUiLCJsb2dpbiI6ImFsaW5hLWFsaW5hIiwibmlja25hbWUiOiJhbGluYV9zaGNoIiwiZnVsbE5hbWUiOiLQkNC70LjQvdCwINCp0LXRgNCx0LjQvdC40L3QsCJ9.InM9CO2j2v-XBfM2-APwtUqJQboOFP_M14xCZwVcw-8}`
- request body:
  ``
- response (status: 403 Forbidden) :
  `     {"message": "The token was expected to have 3 parts, but got 0."
  }
  `
## Авторизация почты




