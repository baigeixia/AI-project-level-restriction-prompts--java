# 新建类流程

## 执行步骤

### 1. 确认类信息
- 类名（UpperCamelCase，含正确层级后缀：Controller/Service/ServiceImpl/Mapper/DO/DTO/VO/Query/Enum）
- 所属包路径与分层层级
- 类类型与主要职责

### 2. 读取对应包 SKILL.md
确认必须满足的约束（继承关系、注解、命名后缀、返回值类型等）。

### 3. 按类类型生成代码模板

**Controller**：继承 BaseController，加 @RestController/@RequestMapping/@Slf4j，方法返回 Result<T> 或 AjaxPage<T>
**Service 接口**：方法无修饰符，必须有 Javadoc，接口名不含 Impl
**ServiceImpl**：实现对应接口，加 @Service/@Slf4j/@Override，写操作加 @Transactional
**Mapper**：继承 BaseMapper<T>（MyBatis-Plus），禁止返回 HashMap
**DO**：与数据库表一一对应，字段用包装类型，含 id/create_time/update_time/is_deleted，必须 toString()
**DTO**：用于接收前端入参，字段用包装类型，含 @Valid 校验注解，必须 toString()
**VO**：用于返回前端，Long 类型 ID 加 @JsonSerialize(using=ToStringSerializer.class)，必须 toString()
**Query**：分页查询参数对象，继承 PageQuery 基类（含 pageNum/pageSize）
**Enum**：类名含 Enum 后缀，成员全大写+下划线，含 code/desc 字段，必须有注释

### 4. 同步更新 .ai/ 记忆层（代码生成后立即执行）

a. 在包目录 `index.json` 的 `classes` 数组追加新类条目
b. 创建类目录：`[包路径]/[ClassName]/`，生成 README.md、SKILL.md、index.json
c. 如果是 Service 接口 → 同步创建 `impl/[ClassName]Impl/` 目录及三件套
d. 如果是 DO → 同步确认数据库是否有对应表（提示用户确认建表规范）