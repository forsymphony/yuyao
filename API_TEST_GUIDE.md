# 接口测试手册

这份文档是给后端初学者用的。

目标只有一个：

教你把这个项目的接口真正调通。

你不要只看代码。

后端一定要学会“自己发请求，自己看返回，自己查数据库”。

---

## 1. 先知道你在测什么

这个项目现在主要是商品管理接口。

接口地址统一前缀是：

`http://127.0.0.1:8080/api/products`

你会用到这些接口：

- `POST /api/products` 新增商品
- `PUT /api/products/{id}` 修改商品
- `DELETE /api/products/{id}` 删除商品
- `GET /api/products/{id}` 查询单个商品
- `GET /api/products` 查询商品列表

---

## 2. 测试前你要先准备什么

你只要准备 3 样东西：

1. 项目启动着
2. 数据库连通
3. 一个测试工具

测试工具你可以任选一个：

- Postman
- Apifox
- curl
- 浏览器

如果你是新手，我建议优先用 `Postman` 或 `Apifox`。

---

## 3. 怎么启动项目

在项目根目录打开 PowerShell，执行：

```bash
java -jar target\product-admin-0.0.1-SNAPSHOT.jar
```

如果你改了代码，先重新打包：

```bash
mvn -DskipTests clean package
java -jar target\product-admin-0.0.1-SNAPSHOT.jar
```

启动后项目默认运行在：

`http://127.0.0.1:8080`

---

## 4. 怎么判断项目启动成功

最直接的方法：

在浏览器打开：

`http://127.0.0.1:8080/api/products`

如果返回类似：

```json
{"content":[],"page":0,"size":10,"totalElements":0,"totalPages":0,"first":true,"last":true}
```

说明项目和接口已经起来了。

---

## 5. 先认识接口最常见的 4 种请求方式

- `GET`：查
- `POST`：新增
- `PUT`：修改
- `DELETE`：删除

这个项目就是围绕这 4 个动作做商品管理。

---

## 6. 第一个接口：查询商品列表

### 地址

```text
GET http://127.0.0.1:8080/api/products
```

### 作用

查所有商品列表，默认分页。

### 你可以直接在浏览器访问

因为 `GET` 接口最简单。

### 返回示例

```json
{
  "content": [],
  "page": 0,
  "size": 10,
  "totalElements": 0,
  "totalPages": 0,
  "first": true,
  "last": true
}
```

### 这些字段是什么意思

- `content`：当前页的数据
- `page`：当前第几页
- `size`：每页多少条
- `totalElements`：总数据量
- `totalPages`：总页数
- `first`：是不是第一页
- `last`：是不是最后一页

---

## 7. 第二个接口：新增商品

### 地址

```text
POST http://127.0.0.1:8080/api/products
```

### 请求头

```text
Content-Type: application/json
```

### 请求体

```json
{
  "name": "鼠标",
  "sku": "MOUSE001",
  "quantity": 100,
  "purchasePrice": 20.00,
  "salePrice": 35.00,
  "location": "上海仓",
  "remark": "办公用品"
}
```

### 成功返回示例

```json
{
  "id": 1,
  "name": "鼠标",
  "sku": "MOUSE001",
  "quantity": 100,
  "purchasePrice": 20.00,
  "salePrice": 35.00,
  "location": "上海仓",
  "remark": "办公用品",
  "createdAt": "2026-04-24T15:00:00",
  "updatedAt": "2026-04-24T15:00:00"
}
```

### 这个接口在代码里是哪里

[ProductController.java](F:\后端项目\yuyao\src\main\java\com\yuyao\productadmin\controller\ProductController.java)

对应的方法是：

- `create`

[ProductService.java](F:\后端项目\yuyao\src\main\java\com\yuyao\productadmin\service\ProductService.java)

对应的方法是：

- `create`

### 你发完请求后，最好做什么

去数据库执行：

```sql
USE product_admin;
SELECT * FROM product;
```

看看这条数据是不是真的进库了。

---

## 8. 第三个接口：按 ID 查询商品

### 地址

```text
GET http://127.0.0.1:8080/api/products/1
```

这里的 `1` 就是商品 id。

### 作用

查一条具体商品。

### 成功返回示例

```json
{
  "id": 1,
  "name": "鼠标",
  "sku": "MOUSE001",
  "quantity": 100,
  "purchasePrice": 20.00,
  "salePrice": 35.00,
  "location": "上海仓",
  "remark": "办公用品",
  "createdAt": "2026-04-24T15:00:00",
  "updatedAt": "2026-04-24T15:00:00"
}
```

### 如果 id 不存在会怎样

后端会返回业务错误，例如：

```json
{
  "timestamp": "2026-04-24T15:10:00",
  "status": 400,
  "message": "商品不存在，id=999"
}
```

这就是全局异常处理的效果。

---

## 9. 第四个接口：修改商品

### 地址

```text
PUT http://127.0.0.1:8080/api/products/1
```

### 请求头

```text
Content-Type: application/json
```

### 请求体

```json
{
  "name": "无线鼠标",
  "sku": "MOUSE001",
  "quantity": 88,
  "purchasePrice": 22.00,
  "salePrice": 39.00,
  "location": "上海仓A区",
  "remark": "已更新"
}
```

### 作用

根据 id 修改商品。

### 成功后你可以再查一次

```text
GET http://127.0.0.1:8080/api/products/1
```

确认数据已经改掉。

---

## 10. 第五个接口：删除商品

### 地址

```text
DELETE http://127.0.0.1:8080/api/products/1
```

### 作用

删除 id 为 1 的商品。

### 成功时返回

通常是：

`204 No Content`

意思是：

删除成功，但不返回具体内容。

### 删除后你应该怎么验证

再访问：

```text
GET http://127.0.0.1:8080/api/products/1
```

如果返回“商品不存在”，说明删除成功。

---

## 11. 列表接口怎么做分页

你可以这样请求：

```text
GET http://127.0.0.1:8080/api/products?page=0&size=10
```

意思是：

- 第 1 页
- 每页 10 条

注意：

这个项目里 `page` 是从 `0` 开始的。

也就是说：

- `page=0` 是第一页
- `page=1` 是第二页

---

## 12. 列表接口怎么搜索

### 按名称搜索

```text
GET http://127.0.0.1:8080/api/products?name=鼠标
```

### 按地点搜索

```text
GET http://127.0.0.1:8080/api/products?location=上海
```

### 名称和地点一起筛选

```text
GET http://127.0.0.1:8080/api/products?name=鼠标&location=上海&page=0&size=10
```

---

## 13. 用 Postman 应该怎么填

下面给你一个最直接的方法。

### 新增商品

1. 新建请求
2. 方法选 `POST`
3. URL 填：

```text
http://127.0.0.1:8080/api/products
```

4. 进入 `Body`
5. 选择 `raw`
6. 右边选择 `JSON`
7. 粘贴：

```json
{
  "name": "键盘",
  "sku": "KEYBOARD001",
  "quantity": 50,
  "purchasePrice": 40,
  "salePrice": 68,
  "location": "杭州仓",
  "remark": "机械键盘"
}
```

8. 点击 `Send`

---

## 14. 用 curl 怎么测

如果你以后不用 Postman，也可以直接在终端测。

### 查询列表

```bash
curl "http://127.0.0.1:8080/api/products"
```

### 新增商品

```bash
curl -X POST "http://127.0.0.1:8080/api/products" ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"耳机\",\"sku\":\"HEADSET001\",\"quantity\":30,\"purchasePrice\":80,\"salePrice\":120,\"location\":\"广州仓\",\"remark\":\"头戴式\"}"
```

Windows 终端里换行时一般用 `^`。

如果你嫌麻烦，先用 Postman。

---

## 15. 你测试接口时最该观察什么

不要只看“能不能返回”。

你要看这 4 件事：

1. HTTP 状态码对不对
2. 返回 JSON 结构对不对
3. 数据库数据有没有真的变化
4. 错误时提示是不是清楚

比如新增商品时：

- 接口返回成功
- 数据库里也确实多了一条

这才叫真正成功。

---

## 16. 你可以怎么查数据库

连接 MySQL 后执行：

```sql
USE product_admin;
SHOW TABLES;
SELECT * FROM product;
```

这 3 句就够你现在用。

含义分别是：

- `USE product_admin;`
  切换到这个项目的数据库

- `SHOW TABLES;`
  查看库里有哪些表

- `SELECT * FROM product;`
  查看商品表所有数据

---

## 17. 你测试时最常遇到的错误

### 17.1 端口不通

现象：

浏览器打不开 `127.0.0.1:8080`

原因通常是：

- 项目没启动
- 项目启动失败
- 端口被占用

---

### 17.2 参数校验失败

比如你传：

```json
{
  "name": "",
  "sku": "A001",
  "quantity": -1,
  "purchasePrice": -5,
  "salePrice": 10,
  "location": "",
  "remark": "test"
}
```

这时后端会返回校验错误。

原因是：

- 名称不能为空
- 数量不能小于 0
- 价格不能小于 0
- 地点不能为空

这些规则都写在：

[ProductRequest.java](F:\后端项目\yuyao\src\main\java\com\yuyao\productadmin\dto\ProductRequest.java)

---

### 17.3 sku 重复

如果你连续新增两条相同 `sku` 的数据，后端会报业务错误。

原因在：

[ProductService.java](F:\后端项目\yuyao\src\main\java\com\yuyao\productadmin\service\ProductService.java)

里面会先检查 `existsBySku(sku)`。

---

### 17.4 查不到商品

你请求：

```text
GET /api/products/999
```

如果 999 不存在，就会返回：

`商品不存在`

这也是正常设计。

---

## 18. 建议你按这个顺序亲手测一遍

### 第一步

查列表：

```text
GET /api/products
```

先确认项目是通的。

### 第二步

新增一条商品。

### 第三步

按 id 查询这条商品。

### 第四步

修改这条商品。

### 第五步

按条件搜索这条商品。

### 第六步

删除这条商品。

### 第七步

再查一次，确认真的删掉了。

---

## 19. 一套完整测试数据，直接拿去用

### 新增第一条

```json
{
  "name": "笔记本电脑",
  "sku": "LAPTOP001",
  "quantity": 10,
  "purchasePrice": 4200,
  "salePrice": 4999,
  "location": "上海仓",
  "remark": "15寸"
}
```

### 新增第二条

```json
{
  "name": "办公鼠标",
  "sku": "MOUSE002",
  "quantity": 60,
  "purchasePrice": 25,
  "salePrice": 39,
  "location": "北京仓",
  "remark": "无线"
}
```

### 修改第一条

```json
{
  "name": "高配笔记本电脑",
  "sku": "LAPTOP001",
  "quantity": 8,
  "purchasePrice": 4500,
  "salePrice": 5299,
  "location": "上海仓A区",
  "remark": "更新后"
}
```

---

## 20. 如果你想真正学会接口测试，要形成这个习惯

每次调接口都做这三步：

1. 看请求发了什么
2. 看返回结果是什么
3. 看数据库有没有真的变化

如果你长期坚持这个习惯，你学后端会快很多。

---

## 21. 这份项目里每个接口对应哪个方法

- `POST /api/products`
  对应 `ProductController.create`

- `PUT /api/products/{id}`
  对应 `ProductController.update`

- `DELETE /api/products/{id}`
  对应 `ProductController.delete`

- `GET /api/products/{id}`
  对应 `ProductController.getById`

- `GET /api/products`
  对应 `ProductController.list`

---

## 22. 一句话总结你现在怎么测

先启动项目，再用 Postman 发请求，再去数据库查结果。

只要你能把“新增、查询、修改、删除”四个动作都亲手测一遍，这个项目你就真的开始入门了。
