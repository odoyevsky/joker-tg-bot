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

## Используемые технологии:

* Java 17
* Spring Boot, Spring Data, Spring MVC, Spring Kafka
* Docker/Docker Compose
* Project Lombok
* TelegramBots
* HtmlUnit
* PostgreSQL
* Maven