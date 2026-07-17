# Digital Business Management System

数字化事业部经营管理系统，面向企业内部数字化部门的项目、合同、商机、投标与月度经营数据管理。系统基于 yudao-ruoyi-pro / ruoyi-vue-pro 框架改造，保留通用后台能力，并新增经营管理业务模块，支持部门负责人按月查看产值、成本、合同、利润、应收、商机和投标等核心经营指标。

## 项目简介

本系统用于解决数字化事业部日常经营统计分散、项目产值口径不统一、合同收付款数据难追踪、商机与投标转化无法沉淀等问题。

系统围绕“项目 - 合同 - 商机 - 投标 - 经营看板”建立数据闭环：

- 项目过程管理：维护项目基础信息、状态、进度、工时和成本。
- 合同履约管理：维护合同基础信息、付款阶段、开票和收款状态。
- 商机投标管理：维护跟踪商机、投标报价、沟通记录和转化结果。
- 经营数据统计：按月、按年统计产值、成本、利润、合同额、应收未收、应结未结等指标。

## 功能特性

### 经营看板

- 首页经营概览，展示月度经营核心指标。
- 月度经营看板，默认查看当前月份数据。
- 年度经营看板，展示年度目标完成进度、月度趋势、商机与投标转化。
- 年度指标录入，支持按年度维护产值目标、合同额目标和利润目标。
- 金额统一按万元展示，便于部门经营会议汇报。

### 项目管理

- 项目列表查询、新增、编辑和分页。
- 项目信息维护：项目编号、项目名称、项目类型、负责人、客户、成员、计划周期、项目状态等。
- 项目类型支持：客户交付、内部自研、运维服务、其他。
- 项目状态支持：项目立项、需求评审、研发、交付试运行、运维、暂停、已结束。
- 项目进度更新：按月份维护完成比例，用于计算合同项目产值。
- 项目工时记录：按月份、成员维护投入工时，用于内部研发项目成本计算。
- 项目产值明细：展示不同项目的产值来源和计算口径。
- 项目成本明细：展示人工成本、管理费用分摊和本月项目成本。

### 合同管理

- 合同列表查询、新增、编辑和分页。
- 合同基础信息维护：合同编号、合同名称、客户、合同金额、签订日期、关联项目等。
- 付款阶段维护：管理阶段金额、计划付款日期、付款状态、开票状态、收款状态。
- 支持统计新签合同、应结未结、应收未收等经营指标。

### 商机管理

- 跟踪商机管理：维护客户、预计金额、预计签约月份、阶段和负责人。
- 沟通记录维护：记录沟通日期、沟通过程和后续事项。
- 投标报价管理：维护报价金额、投标状态、中标状态和转合同结果。
- 商机与投标转化统计，用于年度经营看板分析。

### 经营数据口径

- 合同项目产值 = 合同金额 × 经确认里程碑完成比例。
- 内部研发项目产值 = 工时 × 标准人工单价。
- 运维项目产值 = 年度服务费按履约月份分摊。
- 本月实际成本 = 部门管理费用 + 人工成本。
- 人工成本 = 人员工资 + 五险一金费用。
- 项目人工成本通过项目投入人员工时进行分摊。

## 技术栈

### 后端

- JDK 8
- Spring Boot 2.7.x
- Maven 多模块工程
- MyBatis Plus
- MySQL
- Redis
- Spring Security
- yudao-ruoyi-pro 基础能力

### 前端

- Vue 3
- TypeScript
- Vite
- Element Plus
- Pinia
- ECharts
- UnoCSS

## 快速启动

### 环境要求

- JDK 8
- Maven 3.6+
- Node.js 20.19+
- pnpm 8.6+
- MySQL 8
- Redis

### 数据库初始化

创建 MySQL 数据库：

```sql
CREATE DATABASE `ruoyi-vue-pro-jdk8` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

依次导入初始化脚本：

```bash
mysql -uroot -p ruoyi-vue-pro-jdk8 < sql/mysql/ruoyi-vue-pro.sql
mysql -uroot -p ruoyi-vue-pro-jdk8 < sql/mysql/quartz.sql
mysql -uroot -p ruoyi-vue-pro-jdk8 < sql/mysql/biz_project_management.sql
```

本地数据库连接配置位于：

```text
yudao-server/src/main/resources/application-local.yaml
```

### 启动后端

```bash
mvn -pl yudao-server -am spring-boot:run -Dspring-boot.run.profiles=local
```

默认后端地址：

```text
http://localhost:48080
```

### 启动前端

```bash
cd yudao-ui/yudao-ui-admin-vue3
pnpm install
pnpm dev
```

默认前端地址：

```text
http://localhost
```

## 默认账号

```text
账号：admin
密码：admin123
租户：数字化事业部
```

## 项目结构

```text
.
├── yudao-server                         # 后端启动模块
├── yudao-module-biz                     # 经营管理业务模块
├── yudao-module-system                  # 系统管理模块
├── yudao-module-infra                   # 基础设施模块
├── yudao-framework                      # 框架公共能力
├── yudao-ui/yudao-ui-admin-vue3         # 管理后台前端
├── sql/mysql                            # MySQL 初始化脚本
└── script                               # 部署与辅助脚本
```

经营管理相关前端页面位于：

```text
yudao-ui/yudao-ui-admin-vue3/src/views/biz
```

经营管理相关后端代码位于：

```text
yudao-module-biz/src/main/java/cn/iocoder/yudao/module/biz
```

## 业务模块说明

| 模块 | 说明 |
| --- | --- |
| 经营看板 | 首页、月度经营看板、年度经营看板、年度指标 |
| 项目管理 | 项目列表、进度更新、工时记录、产值明细、成本明细 |
| 合同管理 | 合同列表、付款阶段、开票状态、收款状态 |
| 商机管理 | 跟踪商机、沟通记录、投标报价、转化结果 |
| 系统管理 | 用户、角色、菜单、权限、字典等基础后台能力 |

## 开发说明

前端类型检查：

```bash
cd yudao-ui/yudao-ui-admin-vue3
pnpm ts:check
```

前端构建：

```bash
cd yudao-ui/yudao-ui-admin-vue3
pnpm build:local
```

后端编译：

```bash
mvn -pl yudao-server -am clean package -DskipTests
```

## 安全说明

- 仓库中的 SQL 示例密钥已统一替换为占位符。
- 生产环境请勿直接使用默认账号、默认密码和本地配置。
- 生产环境建议通过环境变量或配置中心管理数据库、Redis、短信、对象存储等敏感配置。

## 开源协议

本项目基于 MIT License 开源。详见 [LICENSE](LICENSE)。

## 致谢

本项目基于 yudao-ruoyi-pro / ruoyi-vue-pro 框架进行二次开发，感谢原项目提供的后台基础能力和工程脚手架。
