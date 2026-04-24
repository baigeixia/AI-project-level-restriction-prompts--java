# Utils 层 AI 生成约束

> 优先级低于 .ai/SKILL.md，不可与全局规则冲突。

---

## 【类定义规范】（强制）

- 工具类禁止有 public 或 default 构造方法（构造方法必须 private）
- 所有方法定义为 public static
- 工具类命名以 Utils 或 Helper 结尾
- 禁止在工具类中引入业务依赖（Spring Bean 注入）

## 【线程安全】（强制）

- 涉及日期格式化：禁止 SimpleDateFormat 定义为 static 变量；使用 DateTimeFormatter（线程安全）或 ThreadLocal<DateFormat>
- 涉及随机数：JDK7+ 使用 ThreadLocalRandom，禁止多线程共享 Random 实例
- 正则表达式：Pattern 必须预编译为 static final 常量，禁止在方法体内定义

## 【工具方法规范】（推荐）

- 单个方法不超过 80 行，复杂逻辑拆分为私有辅助方法
- 方法必须有 Javadoc 注释，说明功能、参数、返回值、异常、线程安全性
- 对输入参数做防御性校验（null 判断），并在 Javadoc 中注明为 null 时的行为
- 避免使用 Apache BeanUtils（性能差），推荐 Spring BeanUtils 或 Cglib BeanCopier（均为浅拷贝，注意说明）

## 【禁止行为】（强制）

- 禁止在工具类中维护状态（禁止成员变量存储状态）
- 禁止工具类依赖外部服务或数据库