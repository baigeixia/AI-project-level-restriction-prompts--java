# Controller 层描述

## 职责
HTTP 请求入口层。负责：
- 接收 HTTP 请求，完成参数绑定与校验（`@Valid`）
- 调用 Service 层执行业务逻辑
- 将结果包装为统一响应格式（`Result<T>` 或 `AjaxPage<T>`）
- **不含任何业务逻辑**

## 本包类清单
<!-- index.json 中的 classes 数组自动同步 -->

## 依赖关系
HTTP 请求 → Controller → Service（接口）
## 关键基类
- 继承：`BaseController`（含全局参数绑定、用户上下文工具方法）
- 响应包装：`Result.success(data)` / `Result.fail(code, msg)`
- 分页响应：`AjaxPage.of(list, total)`