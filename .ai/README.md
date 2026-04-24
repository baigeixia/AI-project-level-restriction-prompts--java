# 项目记忆层 — 全局项目描述

> 本文件由 AI 自动维护，描述整个项目的技术全貌，供每次会话初始化读取。

## 项目概述
- **项目名称**：vast-knowledge
- **业务域**：基于知乎和掘金的知识分享平台
- **架构模式**：Spring Boot MVC 分层架构（Controller → Service → Mapper）

## 技术栈
| 层 | 技术 | 版本 |
|---|---|---|
| 框架 | Spring Boot | 2.x |
| ORM | MyBatis-Plus | 3.x |
| 数据库 | MySQL 8.0 | — |
| 缓存 | Redis | 6.x |
| 构建 | Maven | 3.x |

## 包结构
com.example.project
├── controller    # HTTP 入口，参数校验，结果包装
├── service       # 业务逻辑（接口 + impl 分离）
├── mapper        # 数据访问层（MyBatis-Plus）
├── entity        # 数据库实体
├── dto           # 数据传输对象
├── vo            # 视图对象（响应给前端）
├── enums         # 枚举常量
├── config        # 配置类
├── exception     # 全局异常体系
└── utils         # 工具类
## 全局异常体系
- `GlobalExceptionHandler`：统一异常拦截，返回标准 `Result<T>` 结构
- `BusinessException`：业务异常，含 errorCode
- HTTP 异常 → 4xx；业务异常 → 200 + 非 0 code；系统异常 → 500

## 统一响应结构
```json
{
  "code": 0,
  "message": "success",
  "data": {}
}
```
分页响应：`AjaxPage<T>`，含 `total`、`list`、`pageNum`、`pageSize`

## 代码规范
- Java 编码：UTF-8，缩进 4 空格
- 命名：类 UpperCamelCase，方法/变量 lowerCamelCase，常量 UPPER_SNAKE_CASE
- 注释：公共方法必须有 JavaDoc；复杂逻辑必须有行内注释
- 事务：Service impl 层写操作必须加 `@Transactional`
- 日志：使用 `@Slf4j`，禁止 `System.out.println`

## 安全规范
- 接口鉴权：统一走 JWT 拦截器，`@NoAuth` 注解跳过
- SQL 注入：禁止字符串拼接 SQL，全部走 MyBatis 参数绑定
- 敏感字段：手机号/密码/证件号在日志和响应中必须脱敏

## 数据库规范
- 所有表必须有 `id`（雪花ID）、`create_time`、`update_time`、`is_deleted`（逻辑删除）
- 逻辑删除字段：`is_deleted`，值 0=正常 1=删除
- 禁止物理删除生产数据