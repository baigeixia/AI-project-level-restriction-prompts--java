# 新增方法流程

## 执行步骤

1. 读取类 `index.json` → 检查是否已有同名/同功能方法，避免重复
2. 读取类 `SKILL.md` + 包 `SKILL.md` → 确认命名前缀、注解、异常处理、返回值规范
3. 确认方法定义：
   - 方法名（lowerCamelCase，符合层级命名规范：get/list/count/save/update/remove）
   - 参数列表（类型、校验注解、是否封装 Query 对象）
   - 返回值类型（Controller 层 Result<T>/AjaxPage<T>；Service 层业务对象）
   - 是否写操作 → 加 @Transactional
   - 是否高频读操作 → 考虑 @Cacheable
   - 是否涉及并发 → 确认锁策略
   - 是否涉及金额 → 使用 BigDecimal + compareTo + 悲观锁
4. 生成方法代码
5. 同步更新类 `index.json`，追加方法完整描述

## index.json 方法条目模板

```json
{
  "name": "createOrder",
  "access": "public",
  "returnType": "Result<Long>",
  "httpMethod": "POST",
  "path": "/create",
  "params": [
    { "name": "dto", "type": "OrderCreateDTO", "annotation": "@RequestBody @Valid", "desc": "创建订单请求体" }
  ],
  "desc": "创建订单，防重提交，返回订单ID",
  "annotations": ["@PostMapping(\"/create\")", "@NoRepeatSubmit"],
  "throws": ["BusinessException(B0101-库存不足)"],
  "callChain": ["orderService.createOrder(dto, currentUserId)"],
  "transactional": false,
  "status": "active"
}
```