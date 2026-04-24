# PR 前一致性校验（基于阿里规范强制项）

## 检查清单

### 1. 记忆层覆盖率
- 扫描 src/ 所有 Java 类 → 对比 .ai/ 中是否存在对应类目录
- 缺失的类目录 → 标记待补充，执行 new_module.md 初始化流程

### 2. 方法一致性
- 读取各类 index.json 方法列表 → 对比源码实际方法
- 差异项（新增/删除/签名变更）→ 列出清单，提示同步更新

### 3. 阿里规范强制项合规性校验

**命名**
- [ ] 无拼音/中文命名，无下划线/美元符号开头结尾
- [ ] DO/DTO/VO/Enum 等后缀正确
- [ ] POJO 布尔字段无 is 前缀

**OOP**
- [ ] 覆写方法全部有 @Override
- [ ] POJO 类全部有 toString()
- [ ] POJO 属性全部为包装类型，无默认值

**分层**
- [ ] Controller 不含业务逻辑，不注入 Mapper
- [ ] Service 接口与 Impl 分离，Impl 加 @Override
- [ ] Mapper 无业务逻辑，无 HashMap 返回

**事务**
- [ ] ServiceImpl 写操作全部有 @Transactional

**数据库**
- [ ] 所有表有 id/create_time/update_time/is_deleted
- [ ] 无 SELECT *
- [ ] 无 ${} SQL 参数（防注入）
- [ ] 无外键约束定义

**安全**
- [ ] 写接口有 @NoRepeatSubmit 防重
- [ ] 用户 ID 从 getCurrentUserId() 获取，不从参数接收
- [ ] Long 类型 ID 返回前端已转 String

**响应**
- [ ] 分页接口返回 AjaxPage<T>，非分页返回 Result<T>
- [ ] 列表为空返回空集合，不返回 null

**日志**
- [ ] 无 System.out.println
- [ ] 异常日志包含入参信息和堆栈
- [ ] 使用 {} 占位符，无字符串 + 拼接

### 4. 输出报告格式

```
📋 PR 前规范校验报告
──────────────────────
✅ 记忆层覆盖：[X]/[Y] 类
⚠️  缺失记忆层：[类名列表]
⚠️  方法未同步：[类名.方法名]
❌ 规范违反：[类名.方法名] → [违反的阿里规范条目]
```