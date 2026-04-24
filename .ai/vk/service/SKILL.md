# Service 层 AI 生成约束

> 优先级低于 .ai/SKILL.md，不可与全局规则冲突。
> 规范依据：阿里巴巴《Java开发手册》嵩山版 —— 工程结构·应用分层 + 异常日志

---

## 【层职责边界】（强制）

- Service 层承载具体业务逻辑，是业务规则的唯一实现层
- 禁止在 Service 中直接处理 HTTP 请求/响应（不可依赖 HttpServletRequest）
- Service 接口与实现分离：接口放 service/，实现放 service/impl/，实现类用 Impl 后缀
- 接口方法不加任何修饰符（public 也不加），保持简洁，必须有 Javadoc 注释
- 禁止在接口中定义与业务无关的变量

## 【事务规范】（强制）

- 所有写操作方法（insert/update/delete）必须加 @Transactional 注解
- @Transactional 不可滥用：纯查询方法不加事务（影响 QPS）
- 事务场景中 catch 异常后需要回滚时，必须手动调用 TransactionAspectSupport.currentTransactionStatus().setRollbackOnly()
- 注意事务的传播行为（propagation）和隔离级别（isolation）的显式声明
- 缓存回滚、搜索引擎回滚、消息补偿等需在事务设计时一并考虑

## 【异常处理规范】（强制）

- Service 层出现异常时必须记录错误日志到磁盘，尽可能带上入参信息（保护案发现场）
- 禁止直接 throw new RuntimeException()，使用有业务含义的自定义异常（如 BusinessException）
- 自定义异常必须包含 errorCode（格式：B + 4位数字）和 errorMessage
- 应用内部直接抛出异常，跨应用 RPC 调用使用 Result 封装返回

## 【并发与锁】（强制）

- 并发修改同一记录时必须加锁：应用层/缓存/数据库乐观锁（version 字段），冲突概率 > 20% 用悲观锁
- 资金等金融敏感操作必须使用悲观锁（遵循一锁二判三更新四释放原则）
- 乐观锁重试次数不得少于 3 次
- 多资源加锁时保持一致的加锁顺序，防止死锁
- 高并发退出/中断条件使用 >= / <= 区间判断，禁止用 == 等值判断

## 【参数校验规范】（强制）

- 对外暴露的 Service 方法必须做入参非空校验（防 NPE）
- 批量操作方法必须做数量上限保护
- 方法返回值可以为 null，但必须在 Javadoc 中注明何时返回 null
- 禁止用 Map 传输超过 2 个参数的查询条件，封装成 Query 对象

## 【命名规范】（参考）

| 操作 | 方法名前缀 | 说明 |
|---|---|---|
| 查单个 | get[Entity] | 返回单个对象，可为 null |
| 查列表 | list[Entity]s | 返回 List，不返回 null（空时返回空集合） |
| 统计 | count[Entity] | 返回 Long |
| 新增 | save[Entity] / insert[Entity] | 返回主键 ID 或 void |
| 修改 | update[Entity] | 返回 boolean 或 void |
| 删除 | remove[Entity] / delete[Entity] | 逻辑删除，返回 boolean 或 void |

## 【日志规范】（强制）

- 关键业务操作前后必须打印 info 日志（入参脱敏后）
- 异常日志必须包含案发现场信息和异常堆栈：logger.error("params:{}", dto, e)
- 使用占位符 {} 拼接，禁止字符串 + 拼接
- 禁止在日志中直接 JSON 序列化对象