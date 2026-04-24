# 高级操作 — 批量初始化、废弃处理、重构场景

## 批量初始化（项目接入记忆层）

### 优先级顺序
Controller → Service → ServiceImpl → Mapper → DO → DTO → VO → Query → Enum → Utils

### 步骤
1. 扫描 src/ 所有 .java 文件，按包路径建立 .ai/ 目录结构
2. 对每个类，AI 解析：类类型、字段、方法签名、注解、依赖关系
3. 生成三件套（README.md/SKILL.md/index.json），其中：
   - SKILL.md 初始按类类型套用包级规则，类专属规则留空待补充
   - index.json 完整描述类结构（参考 prompts/index_class.json 模板）
4. 在每个包目录 index.json 中更新 classes 数组
5. 执行 sync_check.md 验证覆盖率和阿里规范强制项合规性

---

## 废弃处理

### 类废弃
1. 包 `index.json` 中：`"status": "deprecated"`, `"deprecatedBy": "NewClassName"`, `"deprecatedAt": "YYYY-MM-DD"`
2. 类 `README.md` 顶部加：`> ⚠️ 已废弃，替代类：[ClassName]，废弃日期：YYYY-MM-DD`
3. 源码加 @Deprecated，Javadoc 注明替代方案
4. 保留 .ai/ 类目录（历史记录，不删除）

### 方法废弃
1. index.json 对应方法：`"status": "deprecated"`, `"replacedBy": "newMethodName"`
2. 源码加 @Deprecated + Javadoc 说明替代方法
3. 禁止静默删除，必须有过渡期

---

## 重构场景

### 包结构调整（如拆分模块）
1. 先更新 .ai/ 目录结构，再重构源码（记忆层先行）
2. 旧路径 index.json 增加 `"movedTo"` 字段指向新路径
3. 执行 sync_check.md 确认一致性

### 方法提取/下沉
- 逻辑从 Controller 下沉到 Service → 确认新方法符合 service/SKILL.md 规范
- 公共逻辑抽取到 Utils → 确认工具方法无状态、线程安全
- 下沉后更新涉及的所有 index.json callChain 字段

### 异常体系重构
- 新增自定义异常类型 → 在 .ai/README.md 的全局异常体系中更新
- 错误码新增 → 在 .ai/README.md 的错误码章节中追加，同步附表 index.json
- 异常处理 → 确认所有业务方法都有异常处理，符合 SKILL.md 规范
- 异常处理 → 确认所有异常处理方法都有 Javadoc 注释，说明异常类型、处理逻辑