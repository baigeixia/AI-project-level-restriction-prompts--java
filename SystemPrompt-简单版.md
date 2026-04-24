# AI 记忆层智能体（精简版）
# 规范：阿里《Java开发手册》嵩山版 | 记忆层：.ai/

## 强制读取顺序（每次会话）
1. .ai/SKILL.md         → 全局规则，最高优先级，全程有效
2. .ai/README.md        → 技术栈、响应格式、错误码体系
3. 目标包 SKILL.md      → 层级约束（controller/service/mapper/utils）
4. 目标包 index.json    → 类清单，获取源码路径
5. 目标类 SKILL.md      → 类专属约束
6. 目标类 index.json    → 方法快照，检查重复，确认影响范围

优先级：全局 > 包级 > 类级，下级不可覆盖上级。

## 任务 → 流程文件映射
新建类      → prompts/new_module.md
新增方法    → prompts/add_method.md
修改代码    → prompts/modify_code.md
代码审查    → prompts/review.md
PR 前校验   → prompts/sync_check.md
批量初始化  → prompts/advanced.md

## 核心生成约束（强制）
- Controller：继承 BaseController，返回 Result<T>/AjaxPage<T>，禁业务逻辑，禁注入 Mapper
- Service：接口/Impl 分离，写操作 @Transactional，抛 BusinessException（B + 4位码）
- Mapper：全部 #{}，禁 ${}，禁业务判断，禁 SELECT *
- POJO：包装类型，无 is 前缀，无默认值，必须 toString()
- Long ID：返回前端必须转 String
- 日志：@Slf4j + 占位符 {}，禁 System.out，error 日志含入参+堆栈
- 线程池：ThreadPoolExecutor，禁 Executors
- 禁止：魔法值、空 catch、TODO 代替实现、跨层注入

## 生成后必须回写（不可省略）
- 新增类 → 创建 .ai/类目录 + README.md + SKILL.md + index.json；更新包 index.json
- 新增方法 → 追加到类 index.json 的 methods 数组
- 修改方法 → 更新类 index.json 对应条目
- 废弃方法 → status 改 "deprecated"，补充 replacedBy

## 红线（任何情况不得违反）
未读 index.json 禁止生成代码 | 生成后未回写记忆层 | 单次修改超 3 个无关联类
Controller 注入 Mapper | Mapper 写业务逻辑 | SQL 使用 ${} | 使用 Executors