# Cards Management REST API

Приложение **Spring Boot 3 + PostgreSQL** для управления банковскими картами, лимитами и транзакциями.

---

## 🛠️ Используемые технологии:

- Java 17
- Spring Boot 3
- PostgreSQL
- JPA / Spring Data
- OpenAPI 3 + Swagger UI
- Docker & Docker-compose
- JUnit 5, Mockito (Unit/Integration tests)

---

## 🚦 Предварительные требования для запуска:

#### ✔️ Java Development Kit 17
Скачайте и установите с официального сайта [Eclipse Temurin 17](https://adoptium.net/temurin/releases/?version=17).

Проверьте версию Java:

```bash
java -version
```

#### ✔️ Maven
Убедитесь, что Maven установлен:

```bash
mvn -version
```
Если используешь Maven Wrapper, запуск будет через `./mvnw`.

#### ✔️ Docker
Скачать и установить Docker Desktop: [Docker](https://www.docker.com/products/docker-desktop)

Проверьте Docker:

```bash
docker -v
docker-compose -v
```

---

## 🎬 Запуск приложения локально (Docker-compose):

### 📌 Шаг 1: Сборка файла .jar

```bash
./mvnw clean package -DskipTests
```

### 📌 Шаг 2: Запуск контейнеров с Docker-compose

```bash
docker-compose up -d --build
```

### 📌 Шаг 3: Проверка поднятых контейнеров

```bash
docker-compose ps
```

Ожидаемый вывод:
```bash
      Name                     Command              State           Ports
------------------------------------------------------------------------------------
cards-api_cards-api_1   java -jar /app.jar          Up      0.0.0.0:8080->8080/tcp
cards-api_cards-db_1    docker-entrypoint.sh postgres  Up      
                        0.0.0.0:5433->5432/tcp
```

---

## 🧭 Проверка доступности приложения:

После запуска приложение доступно:

- REST API доступно по адресу:

```text
http://localhost:8080/api/
```

- Swagger документация доступна здесь:

```text
http://localhost:8080/swagger-ui/index.html
```

---

## 📍 Полезные Docker-compose команды:

| Команда                                 | Описание                               |
|-----------------------------------------|----------------------------------------|
| `docker-compose up -d`                  | Поднять контейнеры в режиме демона     |
| `docker-compose down`                   | Остановить и удалить контейнеры        |
| `docker-compose logs -f`                | Просмотр логов запускаемых контейнеров |

---

## ✔️ Запуск и выполнение тестов:

Юнит и интеграционные тесты запускаются командой:

```bash
./mvnw test
```

---

## 📧 Развёрнутый стек проекта и архитектура:

Проект построен в стандартной структуре Java/Spring Boot:

```
src/main/java
├── config           # Конфигурация приложения (security, db, openapi)
├── controller       # HTTP REST контроллеры
├── dto              # DTO для клиент-сервера обмена данными
├── entity           # Сущности JPA (База данных)
├── exception        # Исключения (обработка ошибок)
├── repository       # Spring Data Repository
├── service          # Сервисы, бизнес-логика
└── util             # Утилиты и вспомогательный код
```

---

## 🎯 Планы на развитие проекта:

1. Улучшение авторизации и безопасности (JWT, OAuth2)
2. RBAC (управление ролями пользователей)
3. Асинхронные операции и кеширование данных с Redis
4. Мониторинг и логирование (Prometheus/Grafana, ELK-stack)
5. CI/CD для автоматического деплоя на сервер
