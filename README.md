# Messenger Relex
Требуется спроектировать и реализовать сервис, позволяющий пользователям отправлять сообщения друг другу (аналог мессенджера).

Приложение необходимо разработать в виде RESTful API на языке Java с использованием фреймворка Spring Boot. Данные, получаемые и отправляемые приложением, должны быть в формате JSON.

# Видео представление

https://drive.google.com/file/d/1HO80c64dAvmT1SgSjSx3a9_W809q_ikp/view?usp=sharing

# Было реализовано
>Обязательная часть
- регистрация пользователя(указать логин, ник, имя, фамилию, почту, пароль)
- возможность войти в систему при помощи логина и пароля
- API, позволяющий завершить текущую сессию и разлогиниться
- возможность обновить данные своего профиля, такие как никнейм, имя, фамилию, email
- API, позволяющий обновить пароль
- API, позволяющий удалить аккаунт пользователя
- API, позволяющий отправить другому пользователю сообщение, реализована проверка на существование пользователя

> Необязательная часть 
- хэширование паролей
- подтверждение почты через ссылку в письме, отправленном на указанную почту
- API, позволяющий залогиниться в системе и сохраняющее информацию о сессии
- поддержка Spring Security
- информация о сессии хранится в JWT токенах и передается в HTTP хэдерах
- механизмы защиты от обхода разлогина
- при изменении email есть подтверждение изменения ссылкой на указанный новый email
- реализован перевод профиля в статус “Не активен” с дальнейшей возможностью восстановить профиль в течение некоторого времени
- API, позволяющий просматривать историю сообщений с конкретным пользователем
> Дополнительная часть 
- API, позволяющий просматривать друзей, а также добавлять в друзья другого пользователя
- API, позволяющий удалить пользователя(сама добавила)
- есть возможность ограничивать получение сообщений только своим кругом друзей
- есть возможность просматривать друзей другого пользователя, и, соответственно, возможность скрывать свой список друзей

> "будет плюсом"
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
## user_info
| user_id | email           | first_name   | is_verified | last_name | is_show_friends   | nickname   | password_hash                                                | login          | is_active  | is_can_receive_message_from_not_friend   |
|---------|-----------------|--------------|-----------|-----------|-------------------|------------|--------------------------------------------------------------|----------------|------------|------------------------------------------|
| 24      | vanya@mail.ru   | Иван         | true      | Иванов    | false             | Ivan       | $2a$10$dweYEelyC84Lb2ekACcDi..DSMzmxq6Fb3.BAMKs.gpRMGscurRmq | nanya          | true       | true                                     |
| 23      | vaciliy@mail.ru | Василий      | true      | Маркин    | true              | Vasya      | $2a$10$3Xu4.bjMPWeJlPRMod8Qf.43dt.3DeogjIZ8tYxphUw4WNPeP72L. | vasya          | true       | false                                    |
| 21      | nata@mail.ru    | Наталья      | true      | Маркина   | true              | natasha    | $2a$10$Mhdk0CiNHL0U7wET7NVbFOdMfrl5bdeN.wI6w7NT1GyCMcbieq9qK | nata-nata      | false      | true                                     |
| 22      | masha@mail.ru   | Мария        | false     | Маркина   | true              | mashenka   | $2a$10$0NS.P857yHgBXXxv1o7REO//tItCJN0MThj1C0M1Vet5JVeRwIxhK | masha-mary     | true       | true                                     |
| 20       | petya@mail.ru   | Петр         | true      | Петров    | true              | petp       | $2a$10$lY2x0A0HDrfZcXlhj/QhA.zipoN6uJbzAnB.eU80UsdcRZetLv7fa | petr-petrovich | true       | true                                     |

## user_role

| user_id | role_id |
|---------|---------|
| 20      | 1       |
| 21      | 1       |
| 22      | 1       |
| 23      | 1       |
| 24      | 1       |

## verification

| verification_id                      | code                                 | valid_till                        | user_id |
|--------------------------------------|--------------------------------------|-----------------------------------|---------|
| 872a6121-d3c9-4bf3-bb36-10c80fd5aeb7 | 22ac50cf-fe54-4a4c-81c0-eac87c7f21a1 | 2023-10-16 11:06:53.639564 +00:00 | 20      |
| 7decbbb7-b7be-48f9-aef9-3d198fc5a2a3 | bb9f5e19-7fc4-4016-b714-5d5748e47f8a | 2023-10-16 11:06:53.639564 +00:00 | 21      |
| 3565c9d9-5611-4214-845b-794016f0f013 | 00f32f2c-45b7-452d-b1ad-b545690b1a53 | 2023-10-16 11:06:53.639564 +00:00 | 22      |
| 2b354b9e-692c-4520-bd7d-7c6969519636 | 477894a2-5a72-49d7-bf98-dbb8d9dfc70c | 2023-10-16 11:06:53.639564 +00:00 | 23      |
| 1d17c0e7-ecf9-4cc6-8192-cf1358818072 | a9c33671-f5b0-45d1-9545-acb3ea069dc2 | 2023-10-16 11:06:53.639564 +00:00 | 24      |


## refresh-token

| token                                | valid_till                        | user_id |
|--------------------------------------|-----------------------------------|---------|
| ffe9c30f-37c7-48d8-9111-f01f43e9981c | 2023-11-15 10:03:39.934483 +00:00 | 20      |
| 0906d86e-5eca-4493-9cce-eaab457ecfe5 | 2023-11-15 10:03:39.934483 +00:00 | 21      |
| 4e332c19-156c-46a2-9aa5-cb21260e5af9 | 2023-11-15 10:03:39.934483 +00:00 | 22      |
| 47219aa6-c291-4d95-9da2-bf1e2ae39eef | 2023-11-15 10:03:39.934483 +00:00 | 23      |
| 2b62bba2-5c43-42bd-817c-251d44c56bcd | 2023-11-15 10:03:39.934483 +00:00 | 24      |

## chat_room

| chat_room_id |
|--------------|

## user_chat_room

| chat_room_id | user_id |
|--------------|---------|

## friend

| user_id | friend_id |
|---------|-----------|
| 20      | 24        |
| 24      | 20        |

## message

| message_id | chat_room_id | user_id | message |
|------------|--------------|---------|---------|


## Настройка базы данны
Чтобы приложение корректно запускалось нужно создать базу данных jdbc:postgresql://localhost:5432/relex_chat и в файле application.yml  указать password  и username в соответствующие строки.
> Для корректной работы приложения таблица role должна содержать следующие данные. Вообще, Liquibase настроена, чтобы все автоматически вставилось в таблицу, но у вас может пойти что-то не так и тогда нужно вручную вставить данные
![image.png](image.png)

# Запросы 
Для многих некорректных запросов в postman  выводится stackTrace. С целью более понятных ответов на запросы, здесь вставлены только message, которые находятся в конце некорректного запроса.

## Authentication API
### Регистрация нового пользователя
POST запрос по адресу http://localhost:8080/auth/register

Подключено BCryptPasswordEncoder для безопасного хранения паролей в базе данныхю

Есть ограничения на длину и валидность некоторых данных в запросе:
- Никнейм не может быть пустым
- Логин не может быть пустым. Логин не может быть меньше 8 и больше 20 символов
- Пароль не может быть пустым. Пароль не может быть меньше 8 и больше 20 символов.
- Password и repeatPassword должны совпадать. 
- почта в формате email и Почта не может быть пустой
- Имя не может быть пустым и больше 100 символов
- Фамилия не может быть пустой и не может быть больше 100 символов

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
### Подтверждение почты
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
### Повторная отправка кода на почту
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
  `  {"message": "The token was expected to have 3 parts, but got 0."}
  `
3. Время дейсвия токена закончилось 
- header Authorization: `{eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyNSIsImV4cCI6MTY5NzQ1MjY4MCwicm9sZXMiOlsiVVNFUiJdLCJlbWFpbCI6ImFsaW5hMjgwNzAyQG1haWwucnUiLCJsb2dpbiI6ImFsaW5hLWFsaW5hIiwibmlja25hbWUiOiJhbGluYV9zaGNoIiwiZnVsbE5hbWUiOiLQkNC70LjQvdCwINCp0LXRgNCx0LjQvdC40L3QsCJ9.InM9CO2j2v-XBfM2-APwtUqJQboOFP_M14xCZwVcw-8}`
- request body:
  ``
- response (status: 403 Forbidden) :
  `     {"message": "The Token has expired on 2023-10-16T10:38:00Z."}
  `
### Авторизация 
POST запрос по адресу http://localhost:8080/auth/login
C данными в теле пользователя, который уже был зарегистрирован
> Примеры запросов
1. Корректный запрос
- request body:
  `{
  "login":"alina-alina",
  "password":"12345678"
  }`
- response (status: 200 OK) :
  `{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyNSIsImV4cCI6MTY5NzQ2MjU2Mywicm9sZXMiOlsiVVNFUiJdLCJlbWFpbCI6ImFsaW5hMjgwNzAyQG1haWwucnUiLCJsb2dpbiI6ImFsaW5hLWFsaW5hIiwibmlja25hbWUiOiJhbGluYV9zaGNoIiwiZnVsbE5hbWUiOiLQkNC70LjQvdCwINCp0LXRgNCx0LjQvdC40L3QsCJ9.mG8yBE5nLc8XDHeuY3yz7ImcJ1sr_6g-pL6n-67aT60",
  "refreshToken": "626a6643-bcc2-48a3-8f54-629ac57a7ed0"
  }`
2. Некорректный логин
- request body:
  `{
  "login":"test",
  "password":"12345678"
  }`
- response (status: 404 Not Found) :
  `{"message": "The user with this login does not exist",}`

3. Некорректный пароль
- request body:
  `{
  "login":"alina-alina",
  "password":"test"
  }`
- response (status: 400 Bad Request) :
  `{"message": "wrong password",}`

### Выход из мессенджера 
- POST запрос по адресу http://localhost:8080/auth/signOut
- В заголовке передать токен

> Примеры запросов
1. Корректный заголовок и верифицированный email
- Header authorization `{eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyNSIsImV4cCI6MTY5NzQ2MjU2Mywicm9sZXMiOlsiVVNFUiJdLCJlbWFpbCI6ImFsaW5hMjgwNzAyQG1haWwucnUiLCJsb2dpbiI6ImFsaW5hLWFsaW5hIiwibmlja25hbWUiOiJhbGluYV9zaGNoIiwiZnVsbE5hbWUiOiLQkNC70LjQvdCwINCp0LXRgNCx0LjQvdC40L3QsCJ9.mG8yBE5nLc8XDHeuY3yz7ImcJ1sr_6g-pL6n-67aT60}`
- request body:
  `{}`
- response (status: 204 No Content) :
  `{}`
2. Корректный заголовок и не верифицированный email
- Header authorization `{eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyNSIsImV4cCI6MTY5NzQ2MjU2Mywicm9sZXMiOlsiVVNFUiJdLCJlbWFpbCI6ImFsaW5hMjgwNzAyQG1haWwucnUiLCJsb2dpbiI6ImFsaW5hLWFsaW5hIiwibmlja25hbWUiOiJhbGluYV9zaGNoIiwiZnVsbE5hbWUiOiLQkNC70LjQvdCwINCp0LXRgNCx0LjQvdC40L3QsCJ9.mG8yBE5nLc8XDHeuY3yz7ImcJ1sr_6g-pL6n-67aT60}`
- request body:
  `{}`
- response (status: 405 Method Not Allowed) :
  `{"message": "The email is not verification ",}`
3. Некорректный заголовок
- header Authorization: `{122222222}`
- request body:
  ``
- response (status: 403 Forbidden) :
  ` {"message": "The token was expected to have 3 parts, but got 0."}
  `
## Chat API
> Для старта чата нужно передать токен в заголовке и в теле запроса ник пользователя, которому хотим отправить сообщение. 
> Аккаунт отправителя и получателя должны быть активны и верифицированы.
> Также у пользователей должно стоять, чтобы можно было получать сообщения от всех или они должны быть друг у друга в друзьях
### старт chat-room
- POST запрос по адресу http://localhost:8080/auth/signOut
- В заголовке передать токен

> Примеры запросов
1. Корректный запрос и токен
- Header authorization `{eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyNSIsImV4cCI6MTY5NzQ2MjU2Mywicm9sZXMiOlsiVVNFUiJdLCJlbWFpbCI6ImFsaW5hMjgwNzAyQG1haWwucnUiLCJsb2dpbiI6ImFsaW5hLWFsaW5hIiwibmlja25hbWUiOiJhbGluYV9zaGNoIiwiZnVsbE5hbWUiOiLQkNC70LjQvdCwINCp0LXRgNCx0LjQvdC40L3QsCJ9.mG8yBE5nLc8XDHeuY3yz7ImcJ1sr_6g-pL6n-67aT60}`
- request body:
  `{"recipientNickname":"petp"}`
- response (status: 200 OK) :
  `{ "chatRoomId": 13}`
2. Корректный токен, но получатель перевел аккаунт в статус "Не активен"
- Header authorization `{eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyNSIsImV4cCI6MTY5NzQ2MjU2Mywicm9sZXMiOlsiVVNFUiJdLCJlbWFpbCI6ImFsaW5hMjgwNzAyQG1haWwucnUiLCJsb2dpbiI6ImFsaW5hLWFsaW5hIiwibmlja25hbWUiOiJhbGluYV9zaGNoIiwiZnVsbE5hbWUiOiLQkNC70LjQvdCwINCp0LXRgNCx0LjQvdC40L3QsCJ9.mG8yBE5nLc8XDHeuY3yz7ImcJ1sr_6g-pL6n-67aT60}`
- request body:
  `{"recipientNickname":"natasha"}`
- response (status: 403 Forbidden) :
  `{"message": "The user is not active",}`
3. Корректный токен, но email не верифицирован
- Header authorization `{eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyNSIsImV4cCI6MTY5NzQ2MjU2Mywicm9sZXMiOlsiVVNFUiJdLCJlbWFpbCI6ImFsaW5hMjgwNzAyQG1haWwucnUiLCJsb2dpbiI6ImFsaW5hLWFsaW5hIiwibmlja25hbWUiOiJhbGluYV9zaGNoIiwiZnVsbE5hbWUiOiLQkNC70LjQvdCwINCp0LXRgNCx0LjQvdC40L3QsCJ9.mG8yBE5nLc8XDHeuY3yz7ImcJ1sr_6g-pL6n-67aT60}`
- request body:
  `{"recipientNickname":"mashenka"}`
- response (status: 405 Method Not Allowed) :
  `{"message": "The email is not verification ",}`
4. Корректный токен, но пользователь поставил, что ему могут писать только друзья
- Header authorization `{eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyNSIsImV4cCI6MTY5NzQ2MjU2Mywicm9sZXMiOlsiVVNFUiJdLCJlbWFpbCI6ImFsaW5hMjgwNzAyQG1haWwucnUiLCJsb2dpbiI6ImFsaW5hLWFsaW5hIiwibmlja25hbWUiOiJhbGluYV9zaGNoIiwiZnVsbE5hbWUiOiLQkNC70LjQvdCwINCp0LXRgNCx0LjQvdC40L3QsCJ9.mG8yBE5nLc8XDHeuY3yz7ImcJ1sr_6g-pL6n-67aT60}`
- request body:
  `{"recipientNickname":"Vasya"}`
- response (status: 405 Method Not Allowed) :
  `{"message": "Not on the friends list"}`

### старт чата, если пользователи раньше не переписывались 
1. В postman выбираем socket.io 
![img_1.png](img_1.png)
2. Переходим в настройки и выбираем v2
![img_2.png](img_2.png)
3. В параметры вводим chatRoomId, полученный из предыдущего шагв(в нашем примере 13) и senderNickname.
![img_3.png](img_3.png)
4. Переходим в параметры. И задаем events и включаем listen
![img_4.png](img_4.png)
5. Нажимаем connect  и должно подключиться
![img_6.png](img_6.png)
6. Повторяем шаги 1-5 для второго пользователя- получателя
7. И вводим сообщение в формате json: 
   `{
   "message":"Как дела?"
   }`
8. И вводим send_message
![img_7.png](img_7.png)
9. Видим, что сообщение отправлено
   send_message { "message":"Привет" }
![img_9.png](img_9.png)
10. Переходим на страницу получателя и видим полученное сообщение
    `{
    "message": "Привет",
    "userSender": {
    "nickname": "alina_shch",
    "firstName": "Алина",
    "lastName": "Щербинина"
    }
    }`
![img_8.png](img_8.png)

### старт чата, если пользователи переписывались уже
> При старте чата вернется chatRoom, по которому уже переписывались пользователи. 
> При подключении пользователи будем выведена история общений

![img_10.png](img_10.png)

## Friend API

### Добавить пользователя в друзья
- POST запрос по адресу http://localhost:8080/friends/{nickname}, где  {nickname}-ник пользователя, кого хотим добавить 
- В заголовке передать токен
>Примеры запросов
1. Корректный токен и ник пользователя
-  http://localhost:8080/friends/petp
- Header authorization `{eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyNSIsImV4cCI6MTY5NzQ2MjU2Mywicm9sZXMiOlsiVVNFUiJdLCJlbWFpbCI6ImFsaW5hMjgwNzAyQG1haWwucnUiLCJsb2dpbiI6ImFsaW5hLWFsaW5hIiwibmlja25hbWUiOiJhbGluYV9zaGNoIiwiZnVsbE5hbWUiOiLQkNC70LjQvdCwINCp0LXRgNCx0LjQvdC40L3QsCJ9.mG8yBE5nLc8XDHeuY3yz7ImcJ1sr_6g-pL6n-67aT60}`
- request body:
  ``
- response (status: 200 OK) :
  ``
2. Некорректный токен и корректный  ник пользователя
- http://localhost:8080/friends/petp
- Header authorization `{eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxNiIsImV4cCI6MTY5NzM5ODI1OCwicm9sZXMiOlsiVVNFUiJdLCJlbWFpbCI6InBldHlhQG1haWwucnUiLCJsb2dpbiI6InBldHItcGV0cm92aWNoIiwibmlja25hbWUiOiJwZXRwIiwiZnVsbE5hbWUiOiLQn9C10YLRgCDQn9C10YLRgNC-0LIifQ.5UYyGiyytCgSxNkG8lHIsIb-GUmDlftnwjEEQCWdDmA}`
- request body:
  ``
- response (status: 403 Forbidden) :
  `    {"message": "The Token has expired on 2023-10-15T19:30:58Z."} `
3. Корректный токен и пользовтель уже в друзьях
-  http://localhost:8080/friends/petp
- Header authorization `{eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyNSIsImV4cCI6MTY5NzQ2MjU2Mywicm9sZXMiOlsiVVNFUiJdLCJlbWFpbCI6ImFsaW5hMjgwNzAyQG1haWwucnUiLCJsb2dpbiI6ImFsaW5hLWFsaW5hIiwibmlja25hbWUiOiJhbGluYV9zaGNoIiwiZnVsbE5hbWUiOiLQkNC70LjQvdCwINCp0LXRgNCx0LjQvdC40L3QsCJ9.mG8yBE5nLc8XDHeuY3yz7ImcJ1sr_6g-pL6n-67aT60}`
- request body:
  ``
- response (status: 409 Conflict) :
  `    "message": "Пользователь уже добавлен в друзья",`
4. Корректный токен , но некорректный ник 
-  http://localhost:8080/friends/test
- Header authorization `{eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyNSIsImV4cCI6MTY5NzQ2MjU2Mywicm9sZXMiOlsiVVNFUiJdLCJlbWFpbCI6ImFsaW5hMjgwNzAyQG1haWwucnUiLCJsb2dpbiI6ImFsaW5hLWFsaW5hIiwibmlja25hbWUiOiJhbGluYV9zaGNoIiwiZnVsbE5hbWUiOiLQkNC70LjQvdCwINCp0LXRgNCx0LjQvdC40L3QsCJ9.mG8yBE5nLc8XDHeuY3yz7ImcJ1sr_6g-pL6n-67aT60}`
- request body:
  ``
- response (status: 404 Not Found) :
  `        "message": "The user with this nickname does not exist",`

### Посмотреть свой список друзей 
- GET запрос по адресу http://localhost:8080/friends
- В заголовке передать токен

>Примеры запросов
1. Корректный токен 
- Header authorization `{eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyNSIsImV4cCI6MTY5NzQ2MjU2Mywicm9sZXMiOlsiVVNFUiJdLCJlbWFpbCI6ImFsaW5hMjgwNzAyQG1haWwucnUiLCJsb2dpbiI6ImFsaW5hLWFsaW5hIiwibmlja25hbWUiOiJhbGluYV9zaGNoIiwiZnVsbE5hbWUiOiLQkNC70LjQvdCwINCp0LXRgNCx0LjQvdC40L3QsCJ9.mG8yBE5nLc8XDHeuY3yz7ImcJ1sr_6g-pL6n-67aT60}`
- request body:
  ``
- response (status: 200 OK) :
  `{
   "firstName": "Петр",
   "lastName": "Петров",
   "email": "petya@mail.ru",
   "nickname": "petp",
   "isVerified": true,
   "isActive": true,
   "isShowFriends": true,
   "isCanReceiveMessageFromNotFriend": true,
   "roles": [
   {
   "roleId": 1,
   "name": "USER",
   "authority": "USER"
   } }`
### Посмотреть список друзей другого пользователя
- GET запрос по адресу http://localhost:8080/friends/{nickname}/all-friends,где  {nickname}-ник пользователя, чьих друзей хотим помотреть
- В заголовке передать токен

>Примеры запросов
1. Корректный токен и пользователь не скрыл список друзей
- http://localhost:8080/friends/petp/all-friends
- Header authorization `{eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyNSIsImV4cCI6MTY5NzQ2MjU2Mywicm9sZXMiOlsiVVNFUiJdLCJlbWFpbCI6ImFsaW5hMjgwNzAyQG1haWwucnUiLCJsb2dpbiI6ImFsaW5hLWFsaW5hIiwibmlja25hbWUiOiJhbGluYV9zaGNoIiwiZnVsbE5hbWUiOiLQkNC70LjQvdCwINCp0LXRgNCx0LjQvdC40L3QsCJ9.mG8yBE5nLc8XDHeuY3yz7ImcJ1sr_6g-pL6n-67aT60}`
- request body:
  ``
- response (status: 200 OK) :
`[
  {
  "firstName": "Алина",
  "lastName": "Щербинина",
  "email": "alina280702@mail.ru",
  "nickname": "alina_shch",
  "isVerified": true,
  "isActive": true,
  "isShowFriends": true,
  "isCanReceiveMessageFromNotFriend": true,
  "roles": [
  {
  "roleId": 1,
  "name": "USER",
  "authority": "USER"
  }
  ]
  },
  {
  "firstName": "Иван",
  "lastName": "Иванов",
  "email": "vanya@mail.ru",
  "nickname": "Ivan",
  "isVerified": true,
  "isActive": true,
  "isShowFriends": false,
  "isCanReceiveMessageFromNotFriend": true,
  "roles": [
  {
  "roleId": 1,
  "name": "USER",
  "authority": "USER"
  }
  ],
  ]`
2. Корректный токен, но пользователь скрыл список друзей
- http://localhost:8080/friends/Ivan/all-friends
- Header authorization `{eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyNSIsImV4cCI6MTY5NzQ2MjU2Mywicm9sZXMiOlsiVVNFUiJdLCJlbWFpbCI6ImFsaW5hMjgwNzAyQG1haWwucnUiLCJsb2dpbiI6ImFsaW5hLWFsaW5hIiwibmlja25hbWUiOiJhbGluYV9zaGNoIiwiZnVsbE5hbWUiOiLQkNC70LjQvdCwINCp0LXRgNCx0LjQvdC40L3QsCJ9.mG8yBE5nLc8XDHeuY3yz7ImcJ1sr_6g-pL6n-67aT60}`
- request body:
  ``
- response (status: 403 Forbidden) :
`"message": "The user has hidden the friends lis",
  `
### Удалить пользователя из друзьей
- DELETE запрос по адресу http://localhost:8080/friends/{nickname}, где  {nickname}-ник пользователя, кого хотим добавить
- В заголовке передать токен
>Примеры запросов
1. Корректный токен и ник пользователя
-  http://localhost:8080/friends/petp
- Header authorization `{eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyNSIsImV4cCI6MTY5NzQ2MjU2Mywicm9sZXMiOlsiVVNFUiJdLCJlbWFpbCI6ImFsaW5hMjgwNzAyQG1haWwucnUiLCJsb2dpbiI6ImFsaW5hLWFsaW5hIiwibmlja25hbWUiOiJhbGluYV9zaGNoIiwiZnVsbE5hbWUiOiLQkNC70LjQvdCwINCp0LXRgNCx0LjQvdC40L3QsCJ9.mG8yBE5nLc8XDHeuY3yz7ImcJ1sr_6g-pL6n-67aT60}`
- request body:
  ``
- response (status: 200 OK) :
  ``
2. Некорректный токен и корректный ник пользователя
- http://localhost:8080/friends/petp
- Header authorization `{eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxNiIsImV4cCI6MTY5NzM5ODI1OCwicm9sZXMiOlsiVVNFUiJdLCJlbWFpbCI6InBldHlhQG1haWwucnUiLCJsb2dpbiI6InBldHItcGV0cm92aWNoIiwibmlja25hbWUiOiJwZXRwIiwiZnVsbE5hbWUiOiLQn9C10YLRgCDQn9C10YLRgNC-0LIifQ.5UYyGiyytCgSxNkG8lHIsIb-GUmDlftnwjEEQCWdDmA}`
- request body:
  ``
- response (status: 403 Forbidden) :
  `    {"message": "The Token has expired on 2023-10-15T19:30:58Z."} `
3. Корректный токен и пользовтель не был в друзьях
-  http://localhost:8080/friends/petp
- Header authorization `{eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyNSIsImV4cCI6MTY5NzQ2MjU2Mywicm9sZXMiOlsiVVNFUiJdLCJlbWFpbCI6ImFsaW5hMjgwNzAyQG1haWwucnUiLCJsb2dpbiI6ImFsaW5hLWFsaW5hIiwibmlja25hbWUiOiJhbGluYV9zaGNoIiwiZnVsbE5hbWUiOiLQkNC70LjQvdCwINCp0LXRgNCx0LjQvdC40L3QsCJ9.mG8yBE5nLc8XDHeuY3yz7ImcJ1sr_6g-pL6n-67aT60}`
- request body:
  ``
- response (status: 409 Conflict) :
  `"message": "Пользователя не было в друзьях",`
4. Корректный токен , но некорректный ник
-  http://localhost:8080/friends/test
- Header authorization `{eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyNSIsImV4cCI6MTY5NzQ2MjU2Mywicm9sZXMiOlsiVVNFUiJdLCJlbWFpbCI6ImFsaW5hMjgwNzAyQG1haWwucnUiLCJsb2dpbiI6ImFsaW5hLWFsaW5hIiwibmlja25hbWUiOiJhbGluYV9zaGNoIiwiZnVsbE5hbWUiOiLQkNC70LjQvdCwINCp0LXRgNCx0LjQvdC40L3QsCJ9.mG8yBE5nLc8XDHeuY3yz7ImcJ1sr_6g-pL6n-67aT60}`
- request body:
  ``
- response (status: 404 Not Found) :
  `"message": "The user with this nickname does not exist",`

## User API

### Посмотр личной информации пользователя
- GET запрос по адресу http://localhost:8080/user/credentials
- В заголовке передать токен
>Примеры запросов
1. Корректный токен 
- http://localhost:8080/user/credentials
- Header authorization `{eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyNSIsImV4cCI6MTY5NzQ2MjU2Mywicm9sZXMiOlsiVVNFUiJdLCJlbWFpbCI6ImFsaW5hMjgwNzAyQG1haWwucnUiLCJsb2dpbiI6ImFsaW5hLWFsaW5hIiwibmlja25hbWUiOiJhbGluYV9zaGNoIiwiZnVsbE5hbWUiOiLQkNC70LjQvdCwINCp0LXRgNCx0LjQvdC40L3QsCJ9.mG8yBE5nLc8XDHeuY3yz7ImcJ1sr_6g-pL6n-67aT60}`
- request body:
  ``
- response (status: 200 OK) :
`{
  "userId": 25,
  "firstName": "Алина",
  "lastName": "Щербинина",
  "email": "alina280702@mail.ru",
  "nickname": "alina_shch",
  "isVerified": true,
  "isActive": true,
  "login": "alina-alina",
  "roles": [
  {
  "roleId": 1,
  "name": "USER",
  "authority": "USER"
  }
  ]
  }`



### Обновление личной информации пользователя
- PUT запрос по адресу http://localhost:8080/user/profile/update/personal-information
- В заголовке передать токен
>Примеры запросов
1. Корректный токен и не повторяющийся ник
- Header authorization `{eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyNSIsImV4cCI6MTY5NzQ2MjU2Mywicm9sZXMiOlsiVVNFUiJdLCJlbWFpbCI6ImFsaW5hMjgwNzAyQG1haWwucnUiLCJsb2dpbiI6ImFsaW5hLWFsaW5hIiwibmlja25hbWUiOiJhbGluYV9zaGNoIiwiZnVsbE5hbWUiOiLQkNC70LjQvdCwINCp0LXRgNCx0LjQvdC40L3QsCJ9.mG8yBE5nLc8XDHeuY3yz7ImcJ1sr_6g-pL6n-67aT60}`
- request body:
  `{
  "firstName":"Alina",
  "lastName":"Shcherbinina",
  "nickname":"alinashch"
  }`
- response (status: 200 OK) :
  ``
2. Корректный токен и  повторяющийся ник
- Header authorization `{eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyNSIsImV4cCI6MTY5NzQ2MjU2Mywicm9sZXMiOlsiVVNFUiJdLCJlbWFpbCI6ImFsaW5hMjgwNzAyQG1haWwucnUiLCJsb2dpbiI6ImFsaW5hLWFsaW5hIiwibmlja25hbWUiOiJhbGluYV9zaGNoIiwiZnVsbE5hbWUiOiLQkNC70LjQvdCwINCp0LXRgNCx0LjQvdC40L3QsCJ9.mG8yBE5nLc8XDHeuY3yz7ImcJ1sr_6g-pL6n-67aT60}`
- request body:
  `{
  "firstName":"Alina",
  "lastName":"Shcherbinina",
  "nickname":"petp"
  }`
- response (status: 409 Conflict) : 
  `"message": "User with this nickname already exists",` 

### Обновление личной пароля
- PUT запрос по адресу http://localhost:8080/user/profile/update/password
- В заголовке передать токен
>Примеры запросов
1. Корректный токен и не повторяющийся ник
- Header authorization `{eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyNSIsImV4cCI6MTY5NzQ2MjU2Mywicm9sZXMiOlsiVVNFUiJdLCJlbWFpbCI6ImFsaW5hMjgwNzAyQG1haWwucnUiLCJsb2dpbiI6ImFsaW5hLWFsaW5hIiwibmlja25hbWUiOiJhbGluYV9zaGNoIiwiZnVsbE5hbWUiOiLQkNC70LjQvdCwINCp0LXRgNCx0LjQvdC40L3QsCJ9.mG8yBE5nLc8XDHeuY3yz7ImcJ1sr_6g-pL6n-67aT60}`
- request body:
  `{
  "password":"1234asdf",
  "repeatPassword":"1234asdf"
  }`
- response (status: 200 OK) :
  ``
2. Корректный токен и  повторяющийся ник
- Header authorization `{eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyNSIsImV4cCI6MTY5NzQ2MjU2Mywicm9sZXMiOlsiVVNFUiJdLCJlbWFpbCI6ImFsaW5hMjgwNzAyQG1haWwucnUiLCJsb2dpbiI6ImFsaW5hLWFsaW5hIiwibmlja25hbWUiOiJhbGluYV9zaGNoIiwiZnVsbE5hbWUiOiLQkNC70LjQvdCwINCp0LXRgNCx0LjQvdC40L3QsCJ9.mG8yBE5nLc8XDHeuY3yz7ImcJ1sr_6g-pL6n-67aT60}`
- request body:
  `{
   password":"1234asdf",
  "repeatPassword":"123456asdf"
  }`
- response (status: 400 Bad Request) :
  `"message": "Password does not match",` 


### Обновление email
- PUT запрос по адресу http://localhost:8080/user/profile/update/email
- В заголовке передать токен
>Примеры запросов
1. Корректный токен и не повторяющийся email
- Header authorization `{eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyNSIsImV4cCI6MTY5NzQ2MjU2Mywicm9sZXMiOlsiVVNFUiJdLCJlbWFpbCI6ImFsaW5hMjgwNzAyQG1haWwucnUiLCJsb2dpbiI6ImFsaW5hLWFsaW5hIiwibmlja25hbWUiOiJhbGluYV9zaGNoIiwiZnVsbE5hbWUiOiLQkNC70LjQvdCwINCp0LXRgNCx0LjQvdC40L3QsCJ9.mG8yBE5nLc8XDHeuY3yz7ImcJ1sr_6g-pL6n-67aT60}`
- request body:
  `{
  "email":"alina22222@mail.ru"
  }`
- response (status: 200 OK) :
  ``
2. Корректный токен и повторяющийся email
- Header authorization `{eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyNSIsImV4cCI6MTY5NzQ2MjU2Mywicm9sZXMiOlsiVVNFUiJdLCJlbWFpbCI6ImFsaW5hMjgwNzAyQG1haWwucnUiLCJsb2dpbiI6ImFsaW5hLWFsaW5hIiwibmlja25hbWUiOiJhbGluYV9zaGNoIiwiZnVsbE5hbWUiOiLQkNC70LjQvdCwINCp0LXRgNCx0LjQvdC40L3QsCJ9.mG8yBE5nLc8XDHeuY3yz7ImcJ1sr_6g-pL6n-67aT60}`
- request body:
  `{
   "email":"masha@mail.ru"
  }`
- response (status: 409 Conflict) :
  `"message": "User with this email already exists",` 

### Обновление статуса профиля - установить "активен"
- PUT запрос по адресу http://localhost:8080/user/profile/update/set-active
- В заголовке передать токен
>Примеры запросов
1. Корректный токен 
- Header authorization `{eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyNSIsImV4cCI6MTY5NzQ2MjU2Mywicm9sZXMiOlsiVVNFUiJdLCJlbWFpbCI6ImFsaW5hMjgwNzAyQG1haWwucnUiLCJsb2dpbiI6ImFsaW5hLWFsaW5hIiwibmlja25hbWUiOiJhbGluYV9zaGNoIiwiZnVsbE5hbWUiOiLQkNC70LjQvdCwINCp0LXRgNCx0LjQvdC40L3QsCJ9.mG8yBE5nLc8XDHeuY3yz7ImcJ1sr_6g-pL6n-67aT60}`
- request body:
  ``
- response (status: 200 OK) :
  ``

### Обновление статуса профиля - установить "неактивен"
- PUT запрос по адресу http://localhost:8080/user/profile/update/set-not-active
- В заголовке передать токен
>Примеры запросов
1. Корректный токен 
- Header authorization `{eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyNSIsImV4cCI6MTY5NzQ2MjU2Mywicm9sZXMiOlsiVVNFUiJdLCJlbWFpbCI6ImFsaW5hMjgwNzAyQG1haWwucnUiLCJsb2dpbiI6ImFsaW5hLWFsaW5hIiwibmlja25hbWUiOiJhbGluYV9zaGNoIiwiZnVsbE5hbWUiOiLQkNC70LjQvdCwINCp0LXRgNCx0LjQvdC40L3QsCJ9.mG8yBE5nLc8XDHeuY3yz7ImcJ1sr_6g-pL6n-67aT60}`
- request body:
  ``
- response (status: 200 OK) :
  ``


### Обновление информации и показе списка друзей-установить "показывать друзей"
- PUT запрос по адресу http://localhost:8080/user/profile/update/set-show-friends
- В заголовке передать токен
>Примеры запросов
1. Корректный токен
- Header authorization `{eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyNSIsImV4cCI6MTY5NzQ2MjU2Mywicm9sZXMiOlsiVVNFUiJdLCJlbWFpbCI6ImFsaW5hMjgwNzAyQG1haWwucnUiLCJsb2dpbiI6ImFsaW5hLWFsaW5hIiwibmlja25hbWUiOiJhbGluYV9zaGNoIiwiZnVsbE5hbWUiOiLQkNC70LjQvdCwINCp0LXRgNCx0LjQvdC40L3QsCJ9.mG8yBE5nLc8XDHeuY3yz7ImcJ1sr_6g-pL6n-67aT60}`
- request body:
  ``
- response (status: 200 OK) :
  ``

### Обновление информации и показе списка друзей-установить "не показывать друзей"
- PUT запрос по адресу http://localhost:8080/user/profile/update/set-not-show-friends
- В заголовке передать токен
>Примеры запросов
1. Корректный токен
- Header authorization `{eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyNSIsImV4cCI6MTY5NzQ2MjU2Mywicm9sZXMiOlsiVVNFUiJdLCJlbWFpbCI6ImFsaW5hMjgwNzAyQG1haWwucnUiLCJsb2dpbiI6ImFsaW5hLWFsaW5hIiwibmlja25hbWUiOiJhbGluYV9zaGNoIiwiZnVsbE5hbWUiOiLQkNC70LjQvdCwINCp0LXRgNCx0LjQvdC40L3QsCJ9.mG8yBE5nLc8XDHeuY3yz7ImcJ1sr_6g-pL6n-67aT60}`
- request body:
  ``
- response (status: 200 OK) :
  ``
### Удалить пользователя
- DELETE запрос по адресу http://localhost:8080/user/delete
- В заголовке передать токен
>Примеры запросов
1. Корректный токен
- Header authorization `{eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyNSIsImV4cCI6MTY5NzQ2MjU2Mywicm9sZXMiOlsiVVNFUiJdLCJlbWFpbCI6ImFsaW5hMjgwNzAyQG1haWwucnUiLCJsb2dpbiI6ImFsaW5hLWFsaW5hIiwibmlja25hbWUiOiJhbGluYV9zaGNoIiwiZnVsbE5hbWUiOiLQkNC70LjQvdCwINCp0LXRgNCx0LjQvdC40L3QsCJ9.mG8yBE5nLc8XDHeuY3yz7ImcJ1sr_6g-pL6n-67aT60}`
- request body:
  ``
- response (status: 204 No Content) :
  ``

