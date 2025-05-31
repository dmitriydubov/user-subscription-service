User Subscription Service
Микросервис для управления пользователями и их подписками на цифровые сервисы (YouTube Premium, Netflix, Spotify и др.)

📌 Функционал
Управление пользователями:

Создание, просмотр, обновление, удаление пользователей

Управление подписками:

Добавление/удаление подписок для пользователей

Просмотр списка подписок пользователя

ТОП-3 самых популярных подписок

🛠 Технологический стек
Язык: Java 17

Фреймворки:

  Spring Boot 3
  
  Spring Data JPA
  
  Spring Web MVC
  
  Spring Validation

Документация API: Swagger (OpenAPI 3)

СУБД: PostgreSQL

Сборка: Gradle

Тестирование:
  модульное: JUnit5
  интеграционное: Testcontainers

Логирование: SLF4J

Контейнеризация: Docker

🚀 Запуск проекта
Требования
JDK 17+

Docker (для запуска через docker-compose)

PostgreSQL 17+

Запуск через Docker:
  Соберите образ:
    docker-compose build
  Запустите сервисы:
    docker-compose up
    
После запуска приложение будет доступно на порту 8080.

📚 Документация API
Документация API доступна через Swagger UI:

http://localhost:8080/swagger-ui.html

OpenAPI спецификация:

http://localhost:8080/v3/api-docs
