# Banking Service

Это простое банковское приложение на Spring Boot с документацией Swagger и настройкой Docker Compose.

## Переменные окружения

Убедитесь, что вы задали следующие переменные окружения для приложения:

```env
POSTGRES_HOST - хост;
POSTGRES_NAME - имя базы данных;
POSTGRES_PASSWORD - пароль базы данных;
POSTGRES_PORT - порт базы данных;
POSTGRES_USER - имя пользователя БД;
SECRET_KEY - ключ для генерации JWT token
```

## Swagger
Присутствует функция Authorize для демонстрации написанного API

Доступ к swagger-ui при запуске на локальной машине:
```
http://localhost:8080/swagger-ui/index.html
```
