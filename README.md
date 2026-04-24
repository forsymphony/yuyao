# product-admin

基于 Spring Boot 3 的商品后台管理后端项目。

## 技术栈

- Java 17
- Spring Boot 3
- Maven
- Spring Web
- Spring Data JPA
- MySQL
- Validation
- Lombok

## 商品字段

- `id`
- `name`
- `sku`
- `quantity`
- `purchasePrice`
- `salePrice`
- `location`
- `remark`
- `createdAt`
- `updatedAt`

## 接口

- `POST /api/products` 新增商品
- `PUT /api/products/{id}` 修改商品
- `DELETE /api/products/{id}` 删除商品
- `GET /api/products/{id}` 查询单个商品
- `GET /api/products?page=0&size=10&name=电脑&location=上海` 分页查询并支持名称、地点筛选

## 数据库配置

默认配置文件：`src/main/resources/application.yml`

默认连接：

```yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3307/product_admin?serverTimezone=GMT%2B8&characterEncoding=utf-8
    username: root
    password: hkd246891.
```

## 创建数据库

方式一：直接执行脚本

```sql
source sql/create_database.sql;
```

方式二：手动执行

```sql
CREATE DATABASE IF NOT EXISTS product_admin
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;
```

## 启动项目

```bash
mvn spring-boot:run
```

## 打包

```bash
mvn clean package
```
