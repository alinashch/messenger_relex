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
## Настройка базы данны
Чтобы приложение корректно запускалось нужно создать таблицу jdbc:postgresql://localhost:5432/relex_chat и в файле application.yml  указать password  и username в соответствующие строки.
> Для корректной работы приложения таблица role должна содержать следующие данные. Вообще, Liquibase настроена, чтобы все автоматически вставилось в таблицу, но у вас может пойти что-то не так и тогда нужно вручную вставить данные
- ![image.png](image.png)

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
  `     {"message": "The token was expected to have 3 parts, but got 0."
  }
  `
3. Время дейсвия токена закончилось 
- header Authorization: `{eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyNSIsImV4cCI6MTY5NzQ1MjY4MCwicm9sZXMiOlsiVVNFUiJdLCJlbWFpbCI6ImFsaW5hMjgwNzAyQG1haWwucnUiLCJsb2dpbiI6ImFsaW5hLWFsaW5hIiwibmlja25hbWUiOiJhbGluYV9zaGNoIiwiZnVsbE5hbWUiOiLQkNC70LjQvdCwINCp0LXRgNCx0LjQvdC40L3QsCJ9.InM9CO2j2v-XBfM2-APwtUqJQboOFP_M14xCZwVcw-8}`
- request body:
  ``
- response (status: 403 Forbidden) :
  `     {    "message": "The Token has expired on 2023-10-16T10:38:00Z."
  }
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
  `{    "message": "The user with this login does not exist",
  }`

3. Некорректный пароль
- request body:
  `{
  "login":"alina-alina",
  "password":"test"
  }`
- response (status: 400 Bad Request) :
  `{        "message": "wrong password",
  }`

### Выход из мессенджера 
POST запрос по адресу http://localhost:8080/auth/signOut
> Примеры запросов
1. Корректный заголовок и верифицированный email
- Header authorization `{eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyNSIsImV4cCI6MTY5NzQ2MjU2Mywicm9sZXMiOlsiVVNFUiJdLCJlbWFpbCI6ImFsaW5hMjgwNzAyQG1haWwucnUiLCJsb2dpbiI6ImFsaW5hLWFsaW5hIiwibmlja25hbWUiOiJhbGluYV9zaGNoIiwiZnVsbE5hbWUiOiLQkNC70LjQvdCwINCp0LXRgNCx0LjQvdC40L3QsCJ9.mG8yBE5nLc8XDHeuY3yz7ImcJ1sr_6g-pL6n-67aT60}`
- request body:
  `{
  }`
- response (status: 204 No Content) :
  `{       
  }`
2. Корректный заголовок и не верифицированный email
- Header authorization `{eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyNSIsImV4cCI6MTY5NzQ2MjU2Mywicm9sZXMiOlsiVVNFUiJdLCJlbWFpbCI6ImFsaW5hMjgwNzAyQG1haWwucnUiLCJsb2dpbiI6ImFsaW5hLWFsaW5hIiwibmlja25hbWUiOiJhbGluYV9zaGNoIiwiZnVsbE5hbWUiOiLQkNC70LjQvdCwINCp0LXRgNCx0LjQvdC40L3QsCJ9.mG8yBE5nLc8XDHeuY3yz7ImcJ1sr_6g-pL6n-67aT60}`
- request body:
  `{
  }`
- response (status: 405 Method Not Allowed) :
  `{            "message": "The email is not verification ",
  }`
3. Некорректный заголовок
- header Authorization: `{122222222}`
- request body:
  ``
- response (status: 403 Forbidden) :
  `     {"message": "The token was expected to have 3 parts, but got 0."
  }
  `
## Chat API
> Для старта чата нужно передать токен в заголовке и в теле запроса ник пользователя, которому хотим отправить сообщение. 
> Аккаунт отправителя и получателя должны быть активны и верифицированы.
> Также у пользователей должно стоять, чтобы можно было получать сообщения от всех или они должны быть друг у друга в друзьях
1. старт чата
   POST запрос по адресу http://localhost:8080/auth/signOut
> Примеры запросов
1. Корректный запрос и токен
- Header authorization `{eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyNSIsImV4cCI6MTY5NzQ2MjU2Mywicm9sZXMiOlsiVVNFUiJdLCJlbWFpbCI6ImFsaW5hMjgwNzAyQG1haWwucnUiLCJsb2dpbiI6ImFsaW5hLWFsaW5hIiwibmlja25hbWUiOiJhbGluYV9zaGNoIiwiZnVsbE5hbWUiOiLQkNC70LjQvdCwINCp0LXRgNCx0LjQvdC40L3QsCJ9.mG8yBE5nLc8XDHeuY3yz7ImcJ1sr_6g-pL6n-67aT60}`
- request body:
  `{ "chatRoomId": 13
  }`
- response (status: 200 OK) :
  `{   
   "recipientNickname":"petp"
  }`
2. Корректный токен, но получатель перевел аккаунт в статус "Не активен"
- Header authorization `{eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyNSIsImV4cCI6MTY5NzQ2MjU2Mywicm9sZXMiOlsiVVNFUiJdLCJlbWFpbCI6ImFsaW5hMjgwNzAyQG1haWwucnUiLCJsb2dpbiI6ImFsaW5hLWFsaW5hIiwibmlja25hbWUiOiJhbGluYV9zaGNoIiwiZnVsbE5hbWUiOiLQkNC70LjQvdCwINCp0LXRgNCx0LjQvdC40L3QsCJ9.mG8yBE5nLc8XDHeuY3yz7ImcJ1sr_6g-pL6n-67aT60}`
- request body:
  `{     "recipientNickname":"natasha"
  }`
- response (status: 403 Forbidden) :
  `{       
  "message": "The user is not active",
  }`
3. Корректный токен, но email не верифицирован
- Header authorization `{eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyNSIsImV4cCI6MTY5NzQ2MjU2Mywicm9sZXMiOlsiVVNFUiJdLCJlbWFpbCI6ImFsaW5hMjgwNzAyQG1haWwucnUiLCJsb2dpbiI6ImFsaW5hLWFsaW5hIiwibmlja25hbWUiOiJhbGluYV9zaGNoIiwiZnVsbE5hbWUiOiLQkNC70LjQvdCwINCp0LXRgNCx0LjQvdC40L3QsCJ9.mG8yBE5nLc8XDHeuY3yz7ImcJ1sr_6g-pL6n-67aT60}`
- request body:
  `{        "recipientNickname":"mashenka"
  }`
- response (status: 405 Method Not Allowed) :
  `{       
  "message": "The email is not verification ",
  }`
4. Корректный токен, но пользователь поставил, что ему могут писать только друзья
- Header authorization `{eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyNSIsImV4cCI6MTY5NzQ2MjU2Mywicm9sZXMiOlsiVVNFUiJdLCJlbWFpbCI6ImFsaW5hMjgwNzAyQG1haWwucnUiLCJsb2dpbiI6ImFsaW5hLWFsaW5hIiwibmlja25hbWUiOiJhbGluYV9zaGNoIiwiZnVsbE5hbWUiOiLQkNC70LjQvdCwINCp0LXRgNCx0LjQvdC40L3QsCJ9.mG8yBE5nLc8XDHeuY3yz7ImcJ1sr_6g-pL6n-67aT60}`
- request body:
  `{        "recipientNickname":"Vasya"
  }`
- response (status: 405 Method Not Allowed) :
  `{       
  "message": "Not on the friends list",
  }`
