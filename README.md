## **Joke telegram bot**

Телеграм бот с шутками, который является Spring Boot приложением.

Вычитывает шутки из Kafka, сохраняя в БД.

Приложение доступно: https://t.me/RussianJokeBot

### Функционал:

- выдача случайной шутки
- выдача шутки по конкретной теме
- выдача избранных шуток пользователя
- добавление шутки в избранное пользователя
- удаление шутки из избранного пользователя
- выдача справочной информации с командами

## **Joke scraper**

Spring Boot приложение, которое осуществляет скрейпинг шуток

По HTTP запросу стартует скрейпинг и запись в Kafka.

## **Запуск**
Приложению необходимы 4 параметра переменной среды:
* POSTGRES_USER
* POSTGRES_PASSWORD
* POSTGRES_DB
* BOT_TOKEN

Необходим файл **.env** в директории проекта с перечисленными параметрами, чтобы при запуске **docker-compose** не указывать их вручную.

В проекте есть файл **.env - example**, его можно переименовать в **.env** и подставить свои значения.

* Для сборки проекта используем maven wrapper
  ```sh
  ./mvnw clean package
  ```
* Создаем docker образы
  ```sh
  docker compose build
  ```
* Запускаем контейнеры
  ```sh
  docker compose up -d
  ```

## Используемые технологии:

* Java 17
* Spring Boot, Spring Data, Spring MVC, Spring Kafka
* Docker/Docker Compose
* Project Lombok
* TelegramBots
* HtmlUnit
* PostgreSQL
* Maven