# ServiceImpl 层 AI 生成约束

> 继承并遵守 service/SKILL.md 所有规则，本文件为 impl 包的补充约束。

---

## 【类定义规范】（强制）

- 实现类命名：接口名 + Impl（如 OrderServiceImpl 实现 OrderService）
- 必须加注解：@Service、@Slf4j
- 所有实现方法必须加 @Override 注解（可准确判断是否覆盖成功）
- 依赖注入使用 @Autowired 或构造函数注入，禁止字段注入 static 类型

## 【方法内代码规范】（强制）

- 方法总行数不超过 80 行；复杂逻辑抽取为私有方法
- if-else 嵌套不超过 3 层，超出使用卫语句/策略模式/状态模式重构
- 表达异常分支时优先使用卫语句（提前 return），减少嵌套
- 复杂条件判断结果赋值给有意义的布尔变量名，提高可读性
- 循环体内避免对象创建、数据库连接获取、不必要的 try-catch
- 字符串循环拼接使用 StringBuilder.append()，禁止 + 拼接
- 正则表达式使用预编译（Pattern.compile 放在方法体外为常量）

## 【数据操作规范】（强制）

- 涉及多表操作的业务必须在事务中执行
- 分页查询：count 为 0 时直接返回，不执行后续分页 SQL
- 禁止在 Service 中直接写 SQL 字符串，通过 Mapper 接口访问数据库
- 查询结果可能为 null 时，必须做 NPE 判断（使用 Optional 或显式 null 检查）
- 级联调用 obj.getA().getB().getC() 必须每步做 null 判断