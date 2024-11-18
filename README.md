Product Project
Описание проекта
Product Project — это Spring Boot приложение для управления продуктами. Оно предоставляет REST API для выполнения операций CRUD (создание, чтение, обновление, удаление), а также позволяет выполнять различные действия, такие как уменьшение количества продукта, вычисление общей стоимости по статусу и центру выполнения, удаление продуктов по определённому критерию.

Установка и запуск:

** Требования:
Для работы проекта вам потребуются:
- Java 17 или выше
- Maven 3.8 или выше
- PostgreSQL
- Git
 ** Установка проекта
1. Клонируйте репозиторий:
git clone https://github.com/Teoteya/ProductProject.git
cd ProductProject
2. Создайте базу данных product.
- Убедитесь, что PostgreSQL запущен, и создайте базу данных:
CREATE DATABASE product;
- Для последующего подключения к базе данных используйте:
    url: jdbc:postgresql://localhost:5432/product
    username: postgres
    password: 12345
3. Соберите проект: mvn clean install
4. Запустите приложение: mvn spring-boot:run
 - Приложение будет доступно на http://localhost:8080.
 - Liquibase инициализирует базу данных. После первого запуска структура таблицы product будет создана, а данные из скрипта будут автоматически добавлены.
** Документация API.
  Swagger UI:
- После запуска приложения вы можете открыть Swagger UI для тестирования API:
http://localhost:8080/swagger-ui.html
- Документация API: http://localhost:8080/v3/api-docs

** Основные API операции:
1. Получение всех продуктов:
GET /products/all
Возвращает список всех продуктов.

2. Получение списка продуктов по productId:
GET /products/get/list/{productId}
Возвращает список продуктов с указанным productId.

3. Получение продукта по id:
GET /products/get/one/{id}
Возвращает продукт по его уникальному идентификатору id.

4. Добавление нового продукта:
POST /products/add
Добавляет новый продукт.

5. Обновление продукта:
PUT /products/update/{id}
Обновляет данные существующего продукта.

6. Удаление продукта по id:
DELETE /products/delete/{id}
Удаляет продукт по его id.

7. Удаление продуктов по productId:
DELETE /products/delete/list/{productId}
Удаляет все продукты с указанным productId.

8. Фильтрация продуктов по статусу:
GET /products/status
Возвращает список продуктов с указанным статусом.
Пример запроса:
http://localhost:8080/products/status?status=Sellable

9. Общая стоимость продуктов со статусом "Sellable":
GET /products/value/status/sellable
Возвращает общую стоимость (value) всех продуктов со статусом Sellable.

10. Общая стоимость продуктов для указанного статуса:
GET /products/value/status
Возвращает общую стоимость (value) всех продуктов для указанного статуса.
Пример запроса:
http://localhost:8080/products/value/status?status=Inbound

11. Общая стоимость продуктов для Fulfillment Center:
GET /products/value/fulfillment_center
Возвращает общую стоимость (value) всех продуктов для указанного Fulfillment Center.
Пример запроса:
http://localhost:8080/products/value/fulfillment_center?ffmCenter=fc5

12. Уменьшение количества продуктов по productId:
PUT /products/decrease/{productId}
Уменьшает количество продукта по указанному productId на единицу.

13. Уменьшение количества продуктов по id:
PUT /products/decrease/one/{id}
Уменьшает количество продукта по указанному id на единицу.
