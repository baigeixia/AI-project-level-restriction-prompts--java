# Controller 层 AI 生成约束

> 优先级低于 .ai/SKILL.md，不可与全局规则冲突。
> 规范依据：阿里巴巴《Java开发手册》嵩山版 —— 工程结构·应用分层 + 前后端规约

---

## 【层职责边界】（强制）

- Controller 层仅负责：请求转发、基本参数校验、响应包装
- 禁止在 Controller 中编写业务逻辑（超过 3 行纯逻辑视为违规）
- 禁止在 Controller 中直接操作 Mapper/DAO
- 禁止在 Controller 中进行数据库查询
- 禁止 try-catch 业务异常（由 GlobalExceptionHandler 统一处理）
- Web 层绝不向上抛异常；意识到异常将导致页面无法渲染时，直接转向友好错误页

## 【类定义规范】（强制）

- 类必须继承 BaseController
- 类必须加注解：@RestController、@RequestMapping、@Slf4j
- 方法级路由注解：GET 用 @GetMapping，POST 用 @PostMapping，PUT 用 @PutMapping，DELETE 用 @DeleteMapping
- 禁止使用未指定 method 的 @RequestMapping（防止接口被任意方式调用）
- 类上的 @RequestMapping 路径使用名词复数，全小写，单词间用下划线
- 生产环境接口路径必须对应 HTTPS

## 【入参规范】（强制）

- 入参对象必须用 @Valid 注解触发 JSR-303 参数校验
- @RequestBody 用于 POST/PUT；@ModelAttribute 或 @RequestParam 用于 GET
- 禁止用 Map 接收超过 2 个参数的请求（必须封装成 Query/DTO 对象）
- 批量操作接口必须对入参数量做上限保护（防内存溢出）
- 翻页参数：pageNum < 1 时返回第一页；pageNum 超过总页数时返回最后一页

## 【响应规范】（强制）

- 非分页接口返回类型必须是 Result<T>
- 分页接口返回类型必须是 AjaxPage<T>，禁止用 List<T> 或 Page<T>
- 列表为空返回 Result.success([]) 或 AjaxPage 空对象，禁止返回 null
- Long 类型 ID 字段必须转 String 返回前端，防止 JS 精度丢失
- 错误响应必须包含：HTTP 状态码 + errorCode + errorMessage + 用户提示信息
- errorCode 格式：A/B/C + 4位数字（参考全局错误码表）

## 【安全规范】（强制）

- 涉及用户个人数据的接口必须做水平权限校验（从 BaseController.getCurrentUserId() 获取当前用户，禁止从请求参数接收 userId）
- 写操作接口必须加防重提交注解 @NoRepeatSubmit
- 表单/AJAX 提交接口必须做 CSRF 校验
- 入参中的敏感字段（手机号、密码、证件号）日志输出前必须脱敏

## 【命名规范】（推荐）

| 操作 | 方法名前缀 | 返回值 |
|---|---|---|
| 查单个 | get[Entity] | Result<EntityVO> |
| 查列表/分页 | list[Entity] / get[Entity]Page | AjaxPage<EntityVO> |
| 新增 | create[Entity] / add[Entity] | Result<Long> |
| 修改 | update[Entity] | Result<Void> |
| 删除 | delete[Entity] / remove[Entity] | Result<Void> |

## 【日志规范】（强制）

- 使用 @Slf4j，禁止 System.out.println
- 关键写操作入参必须打印 info 日志（脱敏后）
- 禁止在日志中直接用 JSON 工具序列化对象（某些 get 方法可能抛异常）
- 使用占位符 {} 拼接日志，禁止字符串 + 拼接