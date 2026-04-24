# 项目说明书

这份说明书是写给后端初学者的。

你现在手上的项目，是一个用 `Spring Boot 3` 写的商品管理后端。

它的作用很简单：

- 存商品数据到数据库
- 提供接口给前端调用
- 支持商品新增、修改、删除、查询

你可以把这个项目理解成一句话：

`前端点按钮 -> 后端收到请求 -> 后端处理数据 -> 后端读写数据库 -> 返回结果给前端`

---

## 1. 这个项目到底是干什么的

这个项目现在做的是一个“商品管理后台”的后端部分。

当前商品包含这些字段：

- `id`：商品主键，数据库自动生成
- `name`：商品名称
- `sku`：商品编码
- `quantity`：商品数量
- `purchasePrice`：入价
- `salePrice`：出价
- `location`：地点
- `remark`：备注
- `createdAt`：创建时间
- `updatedAt`：更新时间

你可以把它想成一个 Excel 表。

这张表每一行就是一个商品。

后端项目负责做两件事：

1. 把这些商品存进数据库
2. 提供接口让别人来增删改查

---

## 2. 你先记住后端项目最核心的 4 个概念

看这个项目前，你先把下面 4 个词记住。

### 2.1 接口

接口就是后端提供出来的“访问入口”。

比如：

- `GET /api/products`
- `POST /api/products`
- `PUT /api/products/1`
- `DELETE /api/products/1`

它们的意思分别是：

- `GET`：查数据
- `POST`：新增数据
- `PUT`：修改数据
- `DELETE`：删除数据

前端或者 Postman 就是通过这些地址来跟后端说话的。

---

### 2.2 实体

实体就是“数据库表在 Java 里的映射”。

比如数据库里有一张 `product` 表。

那么 Java 里就会有一个 `Product` 类去对应它。

你可以理解成：

- 数据库表：真实存数据的地方
- 实体类：Java 代码里对这张表的描述

---

### 2.3 仓库 Repository

`Repository` 是专门跟数据库打交道的。

你可以理解成它是“数据库操作员”。

比如：

- 查商品
- 保存商品
- 删除商品

这些操作大多都在 Repository 这一层完成。

---

### 2.4 服务 Service

`Service` 是“业务处理层”。

它不直接对外暴露接口，也不是只管数据库。

它负责把业务规则写进去。

比如：

- 新增商品时，先检查 `sku` 是否重复
- 修改商品时，先判断商品是否存在
- 查询列表时，加分页和搜索条件

所以你要记住：

- `Controller`：对外接请求
- `Service`：处理业务逻辑
- `Repository`：操作数据库
- `Entity`：映射数据库表

这是这个项目最重要的主线。

---

## 3. 这个项目的目录怎么读

你当前项目最重要的文件和目录如下：

```text
src
 ├─ main
 │   ├─ java
 │   │   └─ com.yuyao.productadmin
 │   │       ├─ controller
 │   │       ├─ dto
 │   │       ├─ entity
 │   │       ├─ exception
 │   │       ├─ repository
 │   │       ├─ service
 │   │       └─ ProductAdminApplication.java
 │   └─ resources
 │       └─ application.yml
 ├─ test
sql
pom.xml
README.md
PROJECT_GUIDE.md
```

下面我按最直接的话解释。

---

## 4. 每个文件夹是什么意思

### 4.1 `pom.xml`

这是 Maven 项目的核心配置文件。

它主要干两件事：

1. 告诉项目要用哪些依赖
2. 告诉项目用什么版本的 Java、Spring Boot

你可以把它理解成：

`项目的安装清单 + 配置清单`

当前里面最重要的依赖有：

- `spring-boot-starter-web`
  作用：写接口，接收 HTTP 请求

- `spring-boot-starter-data-jpa`
  作用：操作数据库

- `spring-boot-starter-validation`
  作用：校验前端传过来的参数

- `mysql-connector-j`
  作用：让 Java 能连接 MySQL

- `lombok`
  作用：少写 getter/setter 这些重复代码

---

### 4.2 `src/main/resources/application.yml`

这是项目配置文件。

当前最重要的配置就是数据库连接：

- 数据库地址
- 端口
- 数据库名
- 用户名
- 密码

还有：

- 项目端口 `8080`
- JPA 自动建表策略 `ddl-auto: update`

你可以这样理解：

`application.yml` 决定项目启动时去哪里连数据库，用什么端口启动。

如果以后项目连不上数据库，第一时间先看这个文件。

---

### 4.3 `ProductAdminApplication.java`

这是项目启动类。

它是整个项目的入口。

你运行项目，本质上就是运行这个类里的 `main` 方法。

只要它启动成功，就说明 Spring Boot 框架开始工作了。

---

### 4.4 `entity` 包

这里放的是实体类。

当前最重要的是：

- `Product.java`

这个类对应数据库中的 `product` 表。

也就是说：

- Java 里的 `Product`
- 数据库里的 `product`

它们是一一对应的。

你在 `Product.java` 里看到的每个字段，最后基本都会变成数据库表的一列。

比如：

- `private String name;`
- `private String sku;`
- `private Integer quantity;`

这些就会对应数据库表中的字段。

类上的注解也很重要：

- `@Entity`
  表示这是一个 JPA 实体类

- `@Table(name = "product")`
  表示它映射数据库中的 `product` 表

- `@Id`
  表示主键

- `@GeneratedValue(strategy = GenerationType.IDENTITY)`
  表示主键自增

- `@Column(...)`
  表示数据库字段的详细规则，比如是否为空、长度、是否唯一

这里你会看到：

- `sku` 设置了 `unique = true`

意思就是：

`sku` 不能重复

---

### 4.5 `repository` 包

这里放数据库访问层。

当前文件：

- `ProductRepository.java`

这个接口继承了：

`JpaRepository<Product, Long>`

你现在先不用怕这个写法。

你只要先记住一句话：

`继承 JpaRepository 以后，很多基础数据库操作 Spring 已经帮你写好了`

比如：

- `save()`：保存
- `findById()`：按 id 查
- `findAll()`：查全部
- `delete()`：删除

你现在项目里的这几个方法：

- `existsBySku(String sku)`
- `existsBySkuAndIdNot(String sku, Long id)`
- `findByNameContainingIgnoreCaseAndLocationContainingIgnoreCase(...)`

它们虽然看起来长，但其实是 Spring Data JPA 的“按方法名自动生成 SQL”。

你可以这样理解：

- `existsBySku`
  意思是：按 sku 判断是否存在

- `existsBySkuAndIdNot`
  意思是：判断某个 sku 是否存在，但排除当前 id
  这个常用在修改时防止重复

- `findByNameContainingIgnoreCaseAndLocationContainingIgnoreCase`
  意思是：按名称模糊查询，并且按地点模糊查询，而且忽略大小写

这个名字长，是因为它把查询逻辑直接写进了方法名。

---

### 4.6 `service` 包

这里是业务层。

当前文件：

- `ProductService.java`

这个类是整个项目最值得你重点看的地方。

因为这里写的是“真实业务规则”。

你可以按这几个方法来读：

#### `create(ProductRequest request)`

新增商品。

它做的事情是：

1. 先检查 `sku` 有没有重复
2. 把请求参数转成 `Product` 实体
3. 保存到数据库
4. 把保存结果转成返回对象

这就是一个标准的新增流程。

---

#### `update(Long id, ProductRequest request)`

修改商品。

它做的事情是：

1. 先查这个商品存不存在
2. 再检查修改后的 `sku` 有没有和别的商品重复
3. 更新字段
4. 保存回数据库

---

#### `delete(Long id)`

删除商品。

它做的事情是：

1. 先查商品是否存在
2. 存在就删掉

---

#### `getById(Long id)`

按 id 查询单个商品。

逻辑很简单：

1. 查数据库
2. 找不到就抛异常
3. 找到就返回

---

#### `list(String name, String location, int page, int size)`

分页查询商品列表。

这个方法很重要，因为实际后台项目里，列表页几乎都需要分页和搜索。

它做的事情是：

1. 创建分页对象
2. 带着名称和地点去数据库查
3. 把数据库结果转换成分页返回对象

这里的分页参数：

- `page`：第几页，从 `0` 开始
- `size`：每页多少条

比如：

- `page=0&size=10`

意思就是：

查第一页，每页 10 条。

---

### 4.7 `controller` 包

这里是控制器层，对外提供接口。

当前文件：

- `ProductController.java`

这是前端真正访问到的地方。

你可以把 `Controller` 理解成：

`项目的大门`

前端请求先到这里，再由这里调用 `Service`。

类上的这句：

`@RequestMapping("/api/products")`

意思是这个类里的所有接口，前面都统一带上：

`/api/products`

所以最终接口就变成：

- `POST /api/products`
- `PUT /api/products/{id}`
- `DELETE /api/products/{id}`
- `GET /api/products/{id}`
- `GET /api/products`

这些方法分别对应：

- 新增
- 修改
- 删除
- 查询单个
- 查询列表

这里你要特别认识几个注解：

- `@RestController`
  表示这是一个接口控制器，返回的是 JSON

- `@PostMapping`
  表示处理新增请求

- `@GetMapping`
  表示处理查询请求

- `@PutMapping`
  表示处理修改请求

- `@DeleteMapping`
  表示处理删除请求

- `@RequestBody`
  表示从请求体里接收 JSON 数据

- `@PathVariable`
  表示从 URL 路径里拿参数，比如 `/api/products/1` 里的 `1`

- `@RequestParam`
  表示从 URL 参数里拿值，比如 `?page=0&size=10`

---

### 4.8 `dto` 包

DTO 的全称你现在不用死记。

你直接把它理解成：

`专门用来接收参数和返回结果的对象`

当前有 3 个：

- `ProductRequest.java`
- `ProductResponse.java`
- `PageResponse.java`

#### `ProductRequest`

这是“前端传给后端”的商品参数。

比如新增商品时，前端会传：

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

这个 JSON 就会被接成 `ProductRequest`。

这里面的注解，比如：

- `@NotBlank`
- `@NotNull`
- `@Min`
- `@DecimalMin`
- `@Size`

这些都是参数校验。

意思是：

- 名称不能为空
- 数量不能小于 0
- 价格不能小于 0
- 字符长度不能超限制

也就是说，前端乱传数据时，后端会先拦下来。

---

#### `ProductResponse`

这是“后端返回给前端”的商品数据结构。

为什么不直接返回实体 `Product`？

因为真实项目里，返回什么字段、隐藏什么字段，最好自己控制。

所以通常会专门做一个返回对象。

你现在先记住一句：

`Request 是接收参数的，Response 是返回结果的`

---

#### `PageResponse`

这是分页返回对象。

它里面包含：

- 数据列表
- 当前页码
- 每页大小
- 总条数
- 总页数
- 是否第一页
- 是否最后一页

所以列表接口返回时，不只是商品数组，还会带分页信息。

---

### 4.9 `exception` 包

这里是异常处理。

当前有两个文件：

- `BusinessException.java`
- `GlobalExceptionHandler.java`

#### `BusinessException`

这是自定义业务异常。

比如：

- 商品不存在
- sku 重复

这些都属于“业务错误”。

不是程序崩了，而是业务不允许这样做。

所以就可以抛出这个异常。

---

#### `GlobalExceptionHandler`

这是全局异常处理器。

它的作用是：

当项目里抛异常时，不要直接把一大堆报错扔给前端，而是统一包装成 JSON 返回。

比如返回：

```json
{
  "timestamp": "2026-04-24T14:00:00",
  "status": 400,
  "message": "商品编码已存在: ABC001"
}
```

这样前端更容易处理，用户也更容易看懂。

这就是“统一异常处理”的意义。

---

## 5. 这个项目一次请求是怎么跑的

这个流程非常重要。

你把它看懂了，整个项目就通了。

比如前端要新增一个商品。

前端发请求：

```http
POST /api/products
Content-Type: application/json
```

请求体：

```json
{
  "name": "键盘",
  "sku": "KB001",
  "quantity": 50,
  "purchasePrice": 40,
  "salePrice": 68,
  "location": "杭州仓",
  "remark": "机械键盘"
}
```

后端内部流程是：

1. 请求先进入 `ProductController`
2. `ProductController` 调用 `ProductService.create()`
3. `ProductService` 检查 `sku` 是否重复
4. `ProductService` 把请求对象转成 `Product` 实体
5. `ProductRepository.save(product)` 把数据写入数据库
6. 保存成功后，后端返回商品结果 JSON

这条链路你要记牢：

`Controller -> Service -> Repository -> Database`

返回时再反过来：

`Database -> Repository -> Service -> Controller -> 前端`

---

## 6. 你应该按什么顺序读代码

你是后端小白，不建议一上来从头到尾乱看。

你应该按这个顺序读。

### 第一步：先看配置文件

先看：

[application.yml](F:\后端项目\yuyao\src\main\resources\application.yml)

你先搞清楚：

- 项目端口是多少
- 数据库连的是哪个库
- 用户名密码是什么

如果你连项目连哪里都不知道，后面会越看越乱。

---

### 第二步：看启动类

看：

[ProductAdminApplication.java](F:\后端项目\yuyao\src\main\java\com\yuyao\productadmin\ProductAdminApplication.java)

你先知道：

这是入口

项目就是从这里启动的。

---

### 第三步：先看 Controller

看：

[ProductController.java](F:\后端项目\yuyao\src\main\java\com\yuyao\productadmin\controller\ProductController.java)

因为你作为初学者，先从“项目对外提供了什么功能”开始最容易。

你要先看懂：

- 有哪些接口
- 每个接口对应什么功能
- 每个接口收什么参数

你把接口看明白了，项目就有轮廓了。

---

### 第四步：再看 Service

看：

[ProductService.java](F:\后端项目\yuyao\src\main\java\com\yuyao\productadmin\service\ProductService.java)

这里是核心业务逻辑。

你要重点看：

- 新增时做了哪些判断
- 修改时做了哪些判断
- 删除时有没有先判断存在
- 查询时怎么分页

你以后写后端，大多数时间都在这一层写逻辑。

---

### 第五步：再看 Entity

看：

[Product.java](F:\后端项目\yuyao\src\main\java\com\yuyao\productadmin\entity\Product.java)

你要搞清楚：

- Java 类字段和数据库字段怎么对应
- 哪个字段不能为空
- 哪个字段唯一
- 主键怎么生成

---

### 第六步：最后看 Repository

看：

[ProductRepository.java](F:\后端项目\yuyao\src\main\java\com\yuyao\productadmin\repository\ProductRepository.java)

你要先看懂它的方法名是什么意思。

现在你不用先研究底层 SQL。

你先知道：

- 这个方法是判断重复
- 这个方法是分页模糊查询

够了。

---

### 第七步：补看 DTO 和异常

看：

- [ProductRequest.java](F:\后端项目\yuyao\src\main\java\com\yuyao\productadmin\dto\ProductRequest.java)
- [ProductResponse.java](F:\后端项目\yuyao\src\main\java\com\yuyao\productadmin\dto\ProductResponse.java)
- [PageResponse.java](F:\后端项目\yuyao\src\main\java\com\yuyao\productadmin\dto\PageResponse.java)
- [GlobalExceptionHandler.java](F:\后端项目\yuyao\src\main\java\com\yuyao\productadmin\exception\GlobalExceptionHandler.java)

这一步是帮助你把“参数怎么进来，结果怎么出去，错误怎么返回”串起来。

---

## 7. 你第一次读 Controller 时，应该怎么看

以 `ProductController` 为例。

你看到一个方法，不要一上来盯着注解发呆。

你按下面这个顺序看：

### 7.1 这个方法是做什么的

先看方法名，比如：

- `create`
- `update`
- `delete`
- `getById`
- `list`

先知道功能。

---

### 7.2 它对应哪个接口地址

看注解，比如：

- `@PostMapping`
- `@GetMapping("/{id}")`

这就代表访问路径和请求方式。

---

### 7.3 它收什么参数

比如：

- `@RequestBody ProductRequest request`
- `@PathVariable Long id`
- `@RequestParam String name`

这代表参数来源不同。

---

### 7.4 它把事情交给谁做

你会看到控制器里通常都很短。

比如：

`return productService.create(request);`

这说明：

Controller 只是接请求，不负责真正业务处理。

真正逻辑在 `Service`。

这是标准写法。

---

## 8. 你第一次读 Service 时，应该怎么看

读 `Service` 时，重点不是语法，而是“业务步骤”。

比如新增：

你就问自己这几个问题：

1. 有没有参数校验
2. 有没有判断重复
3. 有没有把请求对象转成实体
4. 有没有保存数据库
5. 有没有返回结果

如果这 5 步你能顺下来，这个方法你就算读懂了。

修改、删除、查询也一样。

你以后写代码，也要学着这样拆步骤。

---

## 9. 数据库表是怎么来的

当前项目配置里有：

`spring.jpa.hibernate.ddl-auto: update`

它的意思是：

项目启动时，Hibernate 会根据实体类去更新数据库表结构。

简单理解就是：

- 如果表不存在，可能会帮你建表
- 如果字段变了，可能会尝试更新表结构

所以你这个项目的 `product` 表，大概率是项目启动时根据 `Product.java` 自动生成出来的。

也就是说：

`Product.java` 很像数据库表的“模板”

---

## 10. 你怎么判断项目有没有真正跑起来

有 3 个最直接的方法。

### 方法 1：看启动日志

如果没有报错，并且端口正常启动，一般说明项目起来了。

---

### 方法 2：访问接口

比如访问：

```text
http://127.0.0.1:8080/api/products
```

如果返回 JSON，而不是报错页面，说明接口通了。

你当前项目已经验证过这个地址是可以返回数据的。

---

### 方法 3：去数据库里看表

你可以在数据库里执行：

```sql
USE product_admin;
SHOW TABLES;
```

如果看到 `product` 表，说明数据库已经打通。

再执行：

```sql
SELECT * FROM product;
```

就能看到商品数据。

---

## 11. 这个项目里最重要的注解，你要先认识哪些

下面这些是你现在最应该认识的。

### Spring Boot 相关

- `@SpringBootApplication`
  项目启动入口

- `@RestController`
  表示这是返回 JSON 的控制器

- `@Service`
  表示这是业务层

### JPA 相关

- `@Entity`
  标记实体类

- `@Table`
  指定对应数据库表名

- `@Id`
  主键

- `@GeneratedValue`
  主键生成策略

- `@Column`
  字段规则

### 参数相关

- `@RequestBody`
  接 JSON 请求体

- `@PathVariable`
  接路径参数

- `@RequestParam`
  接查询参数

### 校验相关

- `@Valid`
  开启参数校验

- `@NotBlank`
  字符串不能为空

- `@NotNull`
  不能为 null

- `@Min`
  最小值限制

- `@DecimalMin`
  小数最小值限制

### 事务相关

- `@Transactional`
  表示这是一个事务方法

你现在不用背很深。

先知道它们各自大概干什么就够了。

---

## 12. 你现在可以怎么自己练这个项目

最好的学法不是一直看，而是边看边试。

你可以按下面顺序练。

### 练习 1：先查列表

访问：

```text
GET /api/products
```

先看返回结构长什么样。

---

### 练习 2：新增一条商品

发一个 `POST /api/products`

请求体：

```json
{
  "name": "显示器",
  "sku": "MONITOR001",
  "quantity": 20,
  "purchasePrice": 500,
  "salePrice": 699,
  "location": "深圳仓",
  "remark": "27寸"
}
```

然后去数据库里查：

```sql
SELECT * FROM product;
```

你就能看到数据进库了。

---

### 练习 3：故意传错参数

比如把 `name` 传空：

```json
{
  "name": "",
  "sku": "ERR001",
  "quantity": 10,
  "purchasePrice": 10,
  "salePrice": 20,
  "location": "北京仓",
  "remark": "测试"
}
```

你会看到后端返回参数校验错误。

这样你就能明白：

`ProductRequest` 上那些校验注解是真的在工作

---

### 练习 4：故意新增重复 sku

比如插入两次相同的 `sku`。

你会看到 `BusinessException` 的效果。

这样你就能明白：

`Service` 里的重复校验和异常处理是怎么配合的

---

### 练习 5：自己加一个字段

比如你给商品加一个：

`brand` 品牌

你就要改这些地方：

1. `Product.java`
2. `ProductRequest.java`
3. `ProductResponse.java`
4. `ProductService.java`

这样你就能真正理解一个字段从前端到数据库是怎么贯通的。

---

## 13. 你后面最容易混淆的几个点

### 13.1 Entity 和 DTO 的区别

很多初学者最容易晕这个。

你直接这么记：

- `Entity`：给数据库用
- `DTO`：给接口收发数据用

---

### 13.2 Controller 和 Service 的区别

你直接这么记：

- `Controller`：接请求
- `Service`：写真正业务逻辑

如果一个类里全是“接参数、调 service、返回结果”，那它就是 Controller 的感觉。

如果一个类里全是“判断、处理、转换、保存”，那它就是 Service 的感觉。

---

### 13.3 Repository 和 SQL 的关系

你现在虽然没手写 SQL，但不代表没查数据库。

只是 Spring Data JPA 帮你把很多 SQL 隐藏了。

所以：

- 你写的是 Repository 方法
- 底层还是会变成 SQL 去查数据库

---

## 14. 你现在最推荐的阅读路线

如果你今天只想真正读懂这个项目，按下面顺序走。

### 第一轮：只看整体

1. 看 `application.yml`
2. 看 `ProductController`
3. 看 `ProductService`
4. 看 `Product`
5. 看 `ProductRepository`

目标：

先知道项目怎么跑、接口有哪些、业务怎么流转。

---

### 第二轮：看细节

1. 看 `ProductRequest`
2. 看 `ProductResponse`
3. 看 `PageResponse`
4. 看 `GlobalExceptionHandler`

目标：

搞清楚参数怎么校验、结果怎么返回、异常怎么统一处理。

---

### 第三轮：边调接口边看代码

你每调一个接口，就反过来看对应的 Controller 和 Service。

比如你调 `POST /api/products`，就只看：

- `ProductController.create`
- `ProductService.create`

这样学得最快。

---

## 15. 一句话总结这个项目

这个项目本质上就是一个标准的 Spring Boot 分层后端项目。

它把商品管理拆成了几层：

- `Controller` 负责接请求
- `Service` 负责业务逻辑
- `Repository` 负责操作数据库
- `Entity` 负责映射数据库表
- `DTO` 负责接参与返回
- `Exception` 负责统一处理错误

你只要顺着这条主线去看：

`请求从哪里进 -> 业务在哪里处理 -> 数据库在哪里操作 -> 结果从哪里返回`

你就能把这个项目读懂。

---

## 16. 你下一步最应该做什么

不是继续死看代码，而是做这 3 件事。

1. 自己启动一次项目
2. 用 Postman 调一次新增商品接口
3. 去数据库里查这条数据有没有进去

只要你把这三步跑通，你对这个项目的理解会立刻清晰很多。

---

## 17. 如果你想继续学，我建议你下一个阶段看什么

当你把这个项目读懂后，下一步建议学：

1. `Swagger`
   这样你可以更方便地测试接口

2. `分页原理`
   明白为什么列表页不能一次查全部

3. `事务`
   明白为什么 Service 上会写 `@Transactional`

4. `JPA 查询`
   明白 Repository 方法名为什么能自动查数据库

5. `统一返回结构`
   让所有接口返回格式更统一

---

如果你愿意，我下一步可以继续直接给你写第二份文档：

- `接口测试手册`
  我会把每个接口怎么调、参数怎么传、返回什么，全部给你列出来。

或者我也可以继续给你写：

- `逐文件逐行讲解版`
  我按照你的项目文件，一个文件一个文件给你解释。
