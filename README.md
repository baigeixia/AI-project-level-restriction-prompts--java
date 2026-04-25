# AI Project-Level Restriction Prompts (Java) Demo
# AI Project-Level Memory Layer for Java Enterprise Development

> 完全 AI 自生长的项目级记忆层 · 企业级 Java 适配 · 阿里巴巴《Java 开发手册》嵩山版

[![规范基准](https://img.shields.io/badge/规范基准-阿里巴巴Java开发手册·嵩山版-7C3AED)](https://github.com/alibaba/p3c)
[![框架](https://img.shields.io/badge/框架-Spring_Boot_MVC-6DB33F)](https://spring.io/projects/spring-boot)
[![维护](https://img.shields.io/badge/记忆层-AI_闭环维护-0EA5E9)]()

---

## 是什么

这是一套「完全 AI 自生长的项目级记忆层」框架，专为企业级 Java（Spring Boot）项目设计。

核心思路：在 `.ai/` 目录下维护与 `src/` 源码**一一对应**的结构化元数据，让 AI 每次生成、修改代码时都能**精准定位文件、读取约束、回写状态**，实现渐进式、无感、闭环维护。

> AI 不再猜测文件路径，不再丢失上下文，不再重复犯规范错误——每次操作前读约束，操作后写记忆，形成自强化闭环。

---

## 解决什么问题

| 痛点 | 现象 |
|---|---|
| 上下文不集中 | AI 每次对话都忘记项目结构，重复描述背景，浪费 Token |
| 文件定位困难 | AI 猜测或询问文件路径，生成位置错误，代码散落各处 |
| 规范难以落地 | 阿里规范、分层约束停留在文档里，AI 生成的代码随意违反 |
| 记忆层人工维护 | 手动维护文档费力，一旦滞后就失去导航价值 |
| 企业协作成本高 | 新成员不了解项目约定，代码风格不统一 |

---

## 目录结构

```
.ai/                          # AI 记忆层根目录
├── SKILL.md                  # 全局最高优先级规则（阿里规范强制项）
├── README.md                 # AI 使用说明 & 会话初始化入口
│
├── com/                      # 与 src/ 源码包一一镜像
│   └── xxx-module/
│       ├── SKILL.md          # 模块级约束（层职责、命名、事务等）
│       ├── README.md         # 模块说明（接口清单、依赖关系）
│       └── index.json        # 模块索引（类路径、方法签名、callChain）
│
└── prompts/                  # 执行流程模板（AI 操作手册）
    ├── session_init.md       # 会话初始化：强制读取顺序
    ├── new_module.md         # 新建类流程
    ├── add_method.md         # 新增方法流程
    ├── modify_code.md        # 修改代码控制流程
    ├── review.md             # 代码审查（基于阿里规范）
    ├── sync_check.md         # PR 前一致性校验
    ├── advanced.md           # 批量初始化、废弃处理、重构
    ├── index.json            # 包级 index 模板
    └── index_class.json      # 类级 index 模板
```

---

## 核心文件说明

### SKILL.md — 三级约束链

SKILL.md 是 AI 的「行为宪法」，分三级，优先级从高到低：

| 文件路径 | 作用范围 |
|---|---|
| `.ai/SKILL.md` | **全局级**——最高优先级，阿里规范全部强制项，全项目有效，不可被下级覆盖 |
| `com/xxx-module/SKILL.md` | **模块级**——层职责约束（Controller / Service / Mapper 分层规则、事务、异常处理） |
| `com/xxx-module/ClassName/SKILL.md` | **类级**——类专属规则（业务域隔离、特定注解、错误码范围等） |

> **规则继承原则**：下级只能在上级允许的范围内补充约束，当下级与上级矛盾时，以上级为准。

---

### index.json — 两级导航索引

index.json 是记忆层的「导航核心」，AI 必须通过它定位文件，**不得猜测路径**。

| 类型 | 内容 |
|---|---|
| 包级 `index.json` | 记录包内所有类的名称、源码路径（`file`）、`.ai/` 目录路径（`aiDir`）、简要描述、状态 |
| 类级 `index.json` | 完整描述一个类：类型、注解、依赖注入、每个方法的签名 / 参数 / 返回值 / callChain / 事务标记 |

类级 index.json 方法条目示例：

```json
{
  "name": "createOrder",
  "access": "public",
  "returnType": "Result<Long>",
  "params": [
    { "name": "dto", "type": "OrderCreateDTO", "annotation": "@RequestBody @Valid", "desc": "创建订单请求体" }
  ],
  "desc": "创建订单，防重提交，返回订单 ID",
  "annotations": ["@PostMapping(\"/create\")", "@NoRepeatSubmit"],
  "throws": ["BusinessException(B0101-库存不足)"],
  "callChain": ["orderService.createOrder(dto, currentUserId)"],
  "transactional": false,
  "status": "active"
}
```

---

### prompts/ — 标准化工作流

每个 prompt 文件对应一种 AI 操作场景，AI 根据任务类型自动加载对应流程文件，按步骤执行，**不得跳过任何步骤**。

| 文件 | 用途 |
|---|---|
| `session_init.md` | 会话初始化：强制读取顺序（SKILL → README → 包 SKILL → 包 index → 类 SKILL → 类 index） |
| `new_module.md` | 新建类：确认类型 → 读包约束 → 生成代码 → 创建 `.ai/` 三件套 → 更新包 index |
| `add_method.md` | 新增方法：检查重复 → 读约束 → 确认签名 → 生成 → 追加 methods 条目 |
| `modify_code.md` | 修改代码：读方法快照 → 确认影响范围 → 执行 → 更新 index（含废弃标记） |
| `review.md` | 代码审查：阿里规范强制项逐条校验（命名 / OOP / 分层 / 事务 / 安全 / 日志 / DB） |
| `sync_check.md` | PR 前校验：覆盖率检查 + 方法一致性 + 规范合规性，输出标准报告 |
| `advanced.md` | 高级操作：批量初始化、废弃 / 迁移处理、重构场景（包结构调整、方法提取下沉） |

---

## 工作流程

```
① 会话启动    读取 .ai/SKILL.md + .ai/README.md，输出初始化确认，等待指令
      ↓
② 任务识别    根据用户意图，匹配对应的 prompts/ 流程文件
      ↓
③ 分层读取    按优先级链读取：全局 SKILL → 包 SKILL → 类 SKILL
      ↓
④ index 定位  通过 index.json 确认源码路径、方法快照、调用方影响范围
      ↓
⑤ 代码生成    在所有约束满足后执行生成，输出完整可运行代码
      ↓
⑥ 记忆层回写  更新 index.json（方法条目）、README.md（接口清单），同步废弃标记
```

---

## 核心规范基准

规范基准为阿里巴巴《Java 开发手册》嵩山版，以下为关键强制约定：

### 分层约束

| 层 | 约束要点 |
|---|---|
| Controller | 继承 `BaseController`，返回 `Result<T>` / `AjaxPage<T>`，禁止业务逻辑，禁止注入 Mapper |
| Service | 接口与 Impl 分离，写操作加 `@Transactional`，抛 `BusinessException`（B + 4位码） |
| Mapper | 全部用 `#{}`，禁止 `${}`，禁止业务判断，禁止 `SELECT *`，禁止返回 `HashMap` |
| Utils | 无状态，构造方法 `private`，线程安全，不依赖 Spring Bean |

### 命名规范（强制）

- 类名 `UpperCamelCase`；方法 / 变量 `lowerCamelCase`；常量 `UPPER_SNAKE_CASE`
- 禁止拼音 / 中文命名，禁止下划线 / 美元符号开头结尾
- POJO 布尔字段禁止 `is` 前缀；枚举类名加 `Enum` 后缀；Service 实现类加 `Impl` 后缀
- Service 层方法命名：`get` / `list` / `count` / `save` / `update` / `remove` 前缀

### 安全与响应规范（强制）

- `Long` 类型 ID 返回前端必须转 `String`，防止 JS 精度丢失
- `userId` 从 `BaseController.getCurrentUserId()` 获取，禁止前端传入
- 写接口加 `@NoRepeatSubmit` 防重提交
- 错误码格式：`A`（用户端）/ `B`（系统）/ `C`（第三方）+ 4 位数字
- 列表为空返回空集合，禁止返回 `null`

### 数据库规范（强制）

- 表必备字段：`id`（雪花 ID）、`create_time`、`update_time`、`is_deleted`
- 禁止物理删除生产数据，统一逻辑删除（`is_deleted = 1`）
- 禁止外键与级联，外键关系在应用层处理
- 禁止 `float` / `double` 存储小数，使用 `decimal`

---

## 快速上手

### 接入已有项目（批量初始化）

1. 复制本仓库的 `.ai/` 目录到你的项目根目录
2. 修改 `.ai/README.md` 中的技术栈、包路径、统一响应类名等项目信息
3. 修改 `.ai/SKILL.md` 中与项目实际情况不符的约束（如自定义基类名称）
4. 在 AI 工具中输入：`请按 .ai/prompts/advanced.md 对当前项目执行批量初始化`
5. AI 将扫描 `src/` 所有 Java 类，生成对应的 `.ai/` 目录结构和 `index.json`
6. 执行 `sync_check.md` 验证覆盖率和规范合规性

### 新项目接入

1. 初始化 Spring Boot 项目，配置好基础依赖
2. 复制本仓库 `.ai/` 目录，按项目信息修改全局配置
3. 开始开发时，每次对话以 `session_init.md` 初始化，AI 自动完成后续维护

### 配套 AI 工具配置

| 工具 | 配置方式 |
|---|---|
| Claude Projects | 粘贴 `.ai/prompts/session_init.md` 内容到 System Prompt |
| `.cursorrules` | 粘贴精简版系统提示词（见仓库根目录 `.cursorrules.example`） |
| `.github/copilot-instructions.md` | 同上，适用于 GitHub Copilot |
| Cursor / Windsurf | 在 Rules for AI 中引用 `.ai/SKILL.md` 路径 |

---

## 智能体提示词

本框架配套完整的智能体系统提示词，分两个版本：

| 版本 | 适用场景 |
|---|---|
| **完整版（Full）** | 包含完整流程控制、异常处理、输出格式规范。适合 Claude Projects / API 调用，Token 充足场景 |
| **精简版（Lite）** | 核心约束全量保留，去掉格式模板。适合 `.cursorrules` / Copilot Instructions / Token 紧张场景 |

两个版本都依赖 `prompts/` 目录作为「执行手册」：系统提示词负责「读什么、什么时候读、按什么顺序读」，具体步骤由对应的 prompt 文件承载，使系统提示词保持精简稳定，流程细节可独立迭代。

---

## 设计原则

| 原则 | 说明 |
|---|---|
| 先定位，后生成 | 生成任何代码前必须通过 `index.json` 确认文件路径，禁止猜测 |
| 约束优先级链 | 全局 → 包 → 类，下级约束不可覆盖上级，冲突时以上级为准 |
| 生成后必须回写 | 每次操作后立即更新 `index.json`，保持记忆层与源码同步 |
| 操作原子性 | 单次对话修改不超过 3 个无关联的类，防止上下文污染 |
| 废弃不删除 | 方法 / 类废弃时标记 `deprecated` + `replacedBy`，保留历史记录 |
| 规范即护栏 | `SKILL.md` 不是建议文档，是 AI 的行为约束，违反即拒绝生成 |

---

## 规范来源

本框架规范基准为 **阿里巴巴《Java 开发手册》嵩山版**，涵盖：编程规约、异常日志、单元测试、安全规约、MySQL 数据库、工程结构、设计规约七个维度的强制项与推荐项。

---

*规范基准：阿里巴巴《Java 开发手册》嵩山版 · AI 记忆层框架 · 企业级 Java 适配*
