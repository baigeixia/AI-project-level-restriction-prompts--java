# 全局 AI 生成约束 — 项目级锁定

> 每次 AI 操作必须首先读取本文件。本文件规则具有最高优先级，包级与类级 SKILL.md 不可与本文件冲突。
> 规范依据：阿里巴巴《Java开发手册》嵩山版（强制项必须遵守，推荐项默认遵守）

---

## 【命名】

- 类名使用 UpperCamelCase；方法名、参数名、变量名使用 lowerCamelCase；常量全大写+下划线
- 禁止拼音与英文混合命名，禁止中文命名
- 禁止以下划线或美元符号开头/结尾
- 抽象类以 Abstract/Base 开头；异常类以 Exception 结尾；测试类以 Test 结尾
- DO/DTO/VO/BO/AO/PO/UID 等后缀类名保留全大写后缀，不适用 UpperCamelCase 例外
- POJO 类布尔字段禁止加 is 前缀（数据库字段用 is_xxx，结果映射在 resultMap 中处理）
- 包名全小写，点分隔符之间仅一个语义英语单词，使用单数形式
- 枚举类名带 Enum 后缀，枚举成员全大写+下划线
- Service/DAO 接口实现类用 Impl 后缀

## 【常量】

- 禁止魔法值（未预先定义的常量）直接出现在代码中
- long/Long 赋值使用大写 L，禁止小写 l
- 常量按功能分类维护，禁止一个大而全的常量类
- 有固定范围的变量值使用 enum 定义

## 【代码格式】

- 缩进 4 个空格，禁止 Tab 字符
- 单行不超过 120 字符，超出按规范换行（运算符/点号与下文同行）
- 单个方法不超过 80 行
- 文件编码 UTF-8，换行符 Unix 格式
- 注释双斜线与内容之间有且只有一个空格

## 【OOP】

- 所有覆写方法必须加 @Override 注解
- POJO 类属性全部使用包装数据类型；RPC 方法返回值和参数使用包装数据类型；局部变量使用基本类型
- POJO 类禁止设置属性默认值
- POJO 类必须实现 toString() 方法
- 禁止在构造方法中加入业务逻辑，初始化逻辑放在 init() 方法
- 货币金额统一用最小单位整型存储
- BigDecimal 比较使用 compareTo()，禁止使用 equals()；禁止用 new BigDecimal(double)
- 整型包装类比较全部使用 equals()
- 浮点数不能用 == 或 equals 比较，使用误差范围或 BigDecimal
- 访问静态变量/方法直接用类名，不通过对象引用访问

## 【日期时间】

- 日期格式化年份统一小写 yyyy，禁止 YYYY
- 月份大写 M，分钟小写 m，24小时制大写 H，12小时制小写 h
- 获取毫秒用 System.currentTimeMillis()，禁止 new Date().getTime()
- 禁止使用 java.sql.Date / java.sql.Time / java.sql.Timestamp
- 禁止在代码中写死 365 天

## 【集合】

- 判断集合是否为空使用 isEmpty()，禁止 size()==0
- toMap() 必须提供 mergeFunction 参数，防止 key 重复抛出异常
- 禁止在 foreach 循环里 remove/add 元素，用 Iterator 方式
- 集合转数组必须使用 toArray(new T[0]) 形式
- HashMap 初始化时指定容量：(元素个数 / 0.75) + 1
- 遍历 Map 使用 entrySet，不用 keySet（JDK8 用 Map.forEach）
- ConcurrentHashMap 的 key/value 均不允许为 null

## 【并发】

- 线程资源必须通过线程池提供，禁止应用中显式 new Thread
- 线程池必须用 ThreadPoolExecutor 创建，禁止用 Executors（防止 OOM）
- 创建线程或线程池必须指定有意义的线程名称
- SimpleDateFormat 线程不安全，禁止定义为 static，推荐使用 DateTimeFormatter（JDK8）
- ThreadLocal 变量必须在 finally 块中 remove()
- 锁的粒度尽量小；多资源加锁保持一致的加锁顺序防死锁
- 并发场景中断/退出条件使用 >= / <= 区间判断，不用 == 等值判断
- 资金等金融敏感信息使用悲观锁

## 【异常日志】

- 【错误码】格式：错误来源(A/B/C) + 4位数字；A=用户端，B=系统，C=第三方
- 【错误码】全部正常返回 00000；禁止直接将错误码输出给用户作为提示
- 不允许用异常做流程控制/条件控制
- RuntimeException 能预检查的不通过 catch 处理（如 NPE、IndexOutOfBounds）
- catch 后必须处理，不允许空 catch 吞掉异常
- 事务场景 catch 后需要回滚时，必须手动回滚
- finally 必须关闭资源对象/流对象；finally 中禁止 return
- 禁止直接 throw new RuntimeException()，使用有业务含义的自定义异常
- 跨应用 RPC 调用优先用 Result 封装 isSuccess()/errorCode/errorMessage；应用内直接抛异常
- 日志使用 SLF4J 门面，禁止直接使用 Log4j/Logback API，使用 @Slf4j 注解
- 禁止生产环境 System.out / System.err / e.printStackTrace()
- 日志字符串拼接使用占位符 {}，禁止字符串 + 拼接
- error 级别只记录系统逻辑出错、异常或重要错误；用户输入错误用 warn 级别

## 【数据库】

- 表必备字段：id（bigint unsigned 雪花ID）、create_time（datetime）、update_time（datetime）、is_deleted（tinyint unsigned）
- 逻辑删除字段 is_deleted：1=删除，0=未删除；禁止物理删除生产数据
- 禁止使用外键与级联，外键关系在应用层处理
- 禁止使用存储过程
- 禁止 SELECT *，必须明确列名
- SQL 参数严格使用参数绑定（#{}），禁止字符串拼接 SQL（防注入）
- 小数类型用 decimal，禁止 float/double
- 更新记录必须同时更新 update_time 字段
- 超过 3 个表禁止 join
- 页面搜索禁止左模糊或全模糊

## 【安全】

- 用户个人页面/功能必须进行权限控制校验（水平权限）
- 敏感数据（手机号、密码、证件号）展示必须脱敏
- 用户输入参数必须做有效性验证
- 禁止向 HTML 输出未经转义的用户数据（防 XSS）
- 表单/AJAX 提交必须做 CSRF 安全校验
- 使用平台资源（短信/支付等）必须实现防重放机制
- URL 外部重定向必须白名单过滤

## 【前后端规约】

- API 路径只用名词（推荐复数），禁止动词；路径全小写，单词分隔用下划线
- 生产环境必须 HTTPS
- JSON 字段 key 全部 lowerCamelCase
- 超大整数（Long）返回前端必须转 String，防止 JS 精度丢失
- 列表接口为空返回 [] 或 {}，不返回 null
- 服务端错误响应包含：HTTP 状态码、errorCode、errorMessage、用户提示信息

## 【AI 操作规则】

- 先定位后生成：生成任何代码前必须通过 index.json 确认文件路径
- 每次修改前读取类级 index.json 获取当前方法快照
- 新增/修改代码后必须同步更新对应 index.json
- 禁止生成 TODO 注释代替实现
- 禁止单次对话修改超过 3 个无关联的类（防上下文污染）