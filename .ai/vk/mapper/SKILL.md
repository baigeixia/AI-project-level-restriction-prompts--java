# Mapper 层 AI 生成约束

> 优先级低于 .ai/SKILL.md，不可与全局规则冲突。
> 规范依据：阿里巴巴《Java开发手册》嵩山版 —— MySQL数据库 + ORM映射

---

## 【层职责边界】（强制）

- Mapper 层只负责数据访问，禁止包含任何业务判断逻辑
- 禁止在 Mapper 中调用其他 Mapper（多 Mapper 组合在 Service/Manager 层）
- 产生的异常统一 catch(Exception e) 并 throw new DAOException(e)，不打印日志（由上层打印）
- 禁止直接用 HashMap / Hashtable 作为查询结果集的输出类型

## 【SQL 规范】（强制）

- 禁止 SELECT *，必须明确列名
- SQL 参数全部使用 #{}，禁止 ${}（防 SQL 注入）
- 禁止使用外键与级联，外键关系在应用层处理
- 禁止使用存储过程
- 超过 3 个表禁止 join；join 字段数据类型必须保持一致；被关联字段必须有索引
- 分页查询 count 为 0 时直接返回，不执行分页 SQL
- 更新操作必须同时更新 update_time 字段值为当前时间
- 禁止大批量 update set 所有字段（只更新有变动的字段）
- 多表关联查询必须在列名前加表别名限定，防止字段名歧义
- 表别名前加 as，按 t1、t2、t3 顺序命名
- in 操作集合元素控制在 1000 个以内
- 页面搜索禁止左模糊或全模糊（走搜索引擎解决）

## 【ORM 映射规范】（强制）

- 每个表必须有对应的 resultMap，禁止用 resultClass 直接映射
- POJO 布尔属性不能加 is，数据库字段加 is_，在 resultMap 中做映射
- 禁止 iBATIS 的 queryForList(String, int, int) 方法（全量查询再截取，性能差）

## 【索引规范】（强制）

- 业务唯一字段（含组合字段）必须建唯一索引
- 索引命名：主键 pk_字段名，唯一索引 uk_字段名，普通索引 idx_字段名
- varchar 字段建索引必须指定长度
- 组合索引区分度最高的列放最左边
- 防止字段类型不同造成隐式转换导致索引失效

## 【建表规范】（强制）

- 表名、字段名全小写字母或数字，禁止大写，禁止两个下划线中间只有数字
- 表名不用复数；禁用保留字（desc/range/match/delayed 等）
- 是否概念字段：is_xxx，类型 tinyint unsigned（1=是，0=否）
- 小数类型用 decimal，禁止 float/double
- varchar 长度不超过 5000，超过用 text 单独建表
- 字符集使用 utf8（需存储表情用 utf8mb4）