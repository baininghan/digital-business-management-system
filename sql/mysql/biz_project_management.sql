-- 数字化事业部经营管理模块
-- 导入前请确认已经初始化 ruoyi-vue-pro 基础库。

CREATE TABLE IF NOT EXISTS biz_project (
  id bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  project_no varchar(64) DEFAULT NULL COMMENT '项目编号',
  name varchar(128) NOT NULL COMMENT '项目名称',
  type varchar(32) NOT NULL COMMENT '项目类型：客户交付、内部自研、运维、其他',
  owner_name varchar(64) NOT NULL COMMENT '项目负责人',
  members varchar(512) DEFAULT NULL COMMENT '项目成员',
  contract_id bigint DEFAULT NULL COMMENT '关联合同编号',
  output_calc_type varchar(64) DEFAULT NULL COMMENT '产值计算类型',
  plan_start_date date DEFAULT NULL COMMENT '计划开始日期',
  plan_end_date date DEFAULT NULL COMMENT '计划结束日期',
  actual_start_date date DEFAULT NULL COMMENT '实际开始日期',
  actual_end_date date DEFAULT NULL COMMENT '实际结束日期',
  status varchar(32) DEFAULT NULL COMMENT '项目状态',
  completion_rate decimal(10,2) DEFAULT 0 COMMENT '完成比例',
  accumulated_output decimal(18,2) DEFAULT 0 COMMENT '累计产值',
  actual_cost decimal(18,2) DEFAULT 0 COMMENT '实际成本',
  description varchar(1024) DEFAULT NULL COMMENT '项目描述',
  creator varchar(64) DEFAULT '' COMMENT '创建者',
  create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updater varchar(64) DEFAULT '' COMMENT '更新者',
  update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  deleted bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  tenant_id bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='经营项目';

CREATE TABLE IF NOT EXISTS biz_contract (
  id bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  contract_no varchar(64) DEFAULT NULL COMMENT '合同编号',
  name varchar(128) NOT NULL COMMENT '合同名称',
  customer_name varchar(128) NOT NULL COMMENT '客户名称',
  project_id bigint DEFAULT NULL COMMENT '关联项目编号',
  contract_type varchar(32) DEFAULT NULL COMMENT '合同类型',
  amount decimal(18,2) DEFAULT 0 COMMENT '合同金额',
  sign_date date DEFAULT NULL COMMENT '签订日期',
  start_date date DEFAULT NULL COMMENT '合同开始日期',
  end_date date DEFAULT NULL COMMENT '合同结束日期',
  due_settle_amount decimal(18,2) DEFAULT 0 COMMENT '应结金额',
  settled_amount decimal(18,2) DEFAULT 0 COMMENT '已结算金额',
  invoiced_amount decimal(18,2) DEFAULT 0 COMMENT '已开票金额',
  received_amount decimal(18,2) DEFAULT 0 COMMENT '已收款金额',
  status varchar(32) DEFAULT NULL COMMENT '合同状态',
  attachment_url varchar(512) DEFAULT NULL COMMENT '合同附件',
  creator varchar(64) DEFAULT '' COMMENT '创建者',
  create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updater varchar(64) DEFAULT '' COMMENT '更新者',
  update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  deleted bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  tenant_id bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='经营合同';

CREATE TABLE IF NOT EXISTS biz_opportunity (
  id bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  opportunity_no varchar(64) DEFAULT NULL COMMENT '商机编号',
  name varchar(128) NOT NULL COMMENT '商机名称',
  customer_name varchar(128) DEFAULT NULL COMMENT '客户名称',
  estimated_amount decimal(18,2) DEFAULT 0 COMMENT '预计金额',
  stage varchar(32) DEFAULT NULL COMMENT '商机阶段',
  expected_sign_month varchar(7) DEFAULT NULL COMMENT '预计签约月份',
  owner_name varchar(64) DEFAULT NULL COMMENT '负责人',
  converted_to_bid bit(1) DEFAULT b'0' COMMENT '是否转投标',
  bid_id bigint DEFAULT NULL COMMENT '关联报价',
  converted_to_contract bit(1) DEFAULT b'0' COMMENT '是否转合同',
  contract_id bigint DEFAULT NULL COMMENT '关联合同',
  last_follow_date date DEFAULT NULL COMMENT '最近跟进日期',
  follow_record varchar(1024) DEFAULT NULL COMMENT '跟进记录',
  creator varchar(64) DEFAULT '' COMMENT '创建者',
  create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updater varchar(64) DEFAULT '' COMMENT '更新者',
  update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  deleted bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  tenant_id bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='经营商机';

CREATE TABLE IF NOT EXISTS biz_bid (
  id bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  bid_no varchar(64) DEFAULT NULL COMMENT '报价编号',
  name varchar(128) NOT NULL COMMENT '报价名称',
  customer_name varchar(128) DEFAULT NULL COMMENT '客户名称',
  bid_type varchar(32) DEFAULT NULL COMMENT '报价类型',
  amount decimal(18,2) DEFAULT 0 COMMENT '报价金额',
  submit_date date DEFAULT NULL COMMENT '提交日期',
  status varchar(32) DEFAULT NULL COMMENT '报价状态',
  owner_name varchar(64) DEFAULT NULL COMMENT '负责人',
  opportunity_id bigint DEFAULT NULL COMMENT '关联商机',
  win_status varchar(32) DEFAULT NULL COMMENT '是否中标/成交：WON、PENDING、LOST',
  converted_to_contract bit(1) DEFAULT b'0' COMMENT '是否转合同',
  contract_id bigint DEFAULT NULL COMMENT '关联合同',
  expected_result_date date DEFAULT NULL COMMENT '预计开标/反馈日期',
  remark varchar(1024) DEFAULT NULL COMMENT '备注',
  creator varchar(64) DEFAULT '' COMMENT '创建者',
  create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updater varchar(64) DEFAULT '' COMMENT '更新者',
  update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  deleted bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  tenant_id bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='经营投标报价';

CREATE TABLE IF NOT EXISTS biz_project_progress (
  id bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  project_id bigint NOT NULL COMMENT '项目编号',
  progress_month varchar(7) NOT NULL COMMENT '月份',
  completion_rate decimal(10,2) DEFAULT 0 COMMENT '当月完成比例',
  remark varchar(1024) DEFAULT NULL COMMENT '确认说明',
  creator varchar(64) DEFAULT '' COMMENT '创建者',
  create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updater varchar(64) DEFAULT '' COMMENT '更新者',
  update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  deleted bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  tenant_id bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (id),
  KEY idx_project_month (project_id, progress_month)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='项目月度完成比例';

CREATE TABLE IF NOT EXISTS biz_project_worklog (
  id bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  project_id bigint NOT NULL COMMENT '项目编号',
  work_month varchar(7) NOT NULL COMMENT '月份',
  member_name varchar(64) NOT NULL COMMENT '项目成员名称',
  hours decimal(10,2) DEFAULT 0 COMMENT '本月投入工时',
  creator varchar(64) DEFAULT '' COMMENT '创建者',
  create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updater varchar(64) DEFAULT '' COMMENT '更新者',
  update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  deleted bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  tenant_id bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (id),
  KEY idx_project_month (project_id, work_month)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='项目月度工时';

CREATE TABLE IF NOT EXISTS biz_contract_stage (
  id bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  contract_id bigint NOT NULL COMMENT '合同编号',
  stage_name varchar(128) NOT NULL COMMENT '阶段名称',
  receivable_amount decimal(18,2) DEFAULT 0 COMMENT '应收金额',
  plan_date date DEFAULT NULL COMMENT '计划日期',
  paid bit(1) DEFAULT b'0' COMMENT '已付款',
  invoiced bit(1) DEFAULT b'0' COMMENT '已开票',
  received bit(1) DEFAULT b'0' COMMENT '已收款',
  paid_amount decimal(18,2) DEFAULT 0 COMMENT '已付款金额',
  invoiced_amount decimal(18,2) DEFAULT 0 COMMENT '已开票金额',
  received_amount decimal(18,2) DEFAULT 0 COMMENT '已收款金额',
  creator varchar(64) DEFAULT '' COMMENT '创建者',
  create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updater varchar(64) DEFAULT '' COMMENT '更新者',
  update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  deleted bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  tenant_id bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (id),
  KEY idx_contract_id (contract_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='合同付款阶段';

CREATE TABLE IF NOT EXISTS biz_opportunity_communication (
  id bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  opportunity_id bigint NOT NULL COMMENT '商机编号',
  communication_date date NOT NULL COMMENT '沟通日期',
  content varchar(1024) NOT NULL COMMENT '沟通过程',
  next_action varchar(512) DEFAULT NULL COMMENT '下一步动作',
  owner_name varchar(64) DEFAULT NULL COMMENT '跟进人',
  creator varchar(64) DEFAULT '' COMMENT '创建者',
  create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updater varchar(64) DEFAULT '' COMMENT '更新者',
  update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  deleted bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  tenant_id bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (id),
  KEY idx_opportunity_id (opportunity_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商机沟通记录';

CREATE TABLE IF NOT EXISTS biz_annual_target (
  id bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  target_year int NOT NULL COMMENT '年度',
  output_target decimal(18,2) DEFAULT 0 COMMENT '年度产值目标',
  contract_target decimal(18,2) DEFAULT 0 COMMENT '年度合同额目标',
  profit_target decimal(18,2) DEFAULT 0 COMMENT '年度利润目标',
  remark varchar(1024) DEFAULT NULL COMMENT '指标说明',
  creator varchar(64) DEFAULT '' COMMENT '创建者',
  create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updater varchar(64) DEFAULT '' COMMENT '更新者',
  update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  deleted bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  tenant_id bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (id),
  UNIQUE KEY uk_target_year_tenant (target_year, tenant_id, deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='年度经营指标';

INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES
  (2026071601, '经营管理', '', 1, 10, 0, '/biz', 'ep:data-line', NULL, NULL, 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
  (2026071602, '月度经营看板', 'biz:dashboard:query', 2, 1, 2026071601, 'dashboard', 'ep:trend-charts', 'biz/dashboard/index', 'BizDashboard', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
  (2026071603, '月度经营数据', 'biz:monthly-data:query', 2, 2, 2026071601, 'monthly-data', 'ep:histogram', 'biz/monthly-data/index', 'BizMonthlyData', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
  (2026071604, '年度经营看板', 'biz:annual-dashboard:query', 2, 3, 2026071601, 'annual-dashboard', 'ep:data-analysis', 'biz/annual-dashboard/index', 'BizAnnualDashboard', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
  (2026071605, '年度指标', 'biz:annual-target:query', 2, 4, 2026071601, 'annual-target', 'ep:aim', 'biz/annual-target/index', 'BizAnnualTarget', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
  (2026071606, '合同列表', 'biz:contract:query', 2, 11, 0, '/biz-contract', 'ep:document', 'biz/contract/index', 'BizContract', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
  (2026071607, '项目列表', 'biz:project:query', 2, 1, 2026071612, 'project', 'ep:folder', 'biz/project/index', 'BizProject', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
  (2026071608, '项目产值明细', 'biz:output-detail:query', 2, 2, 2026071612, 'output-detail', 'ep:list', 'biz/output-detail/index', 'BizOutputDetail', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
  (2026071609, '项目成本明细', 'biz:cost-detail:query', 2, 3, 2026071612, 'cost-detail', 'ep:money', 'biz/cost-detail/index', 'BizCostDetail', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
  (2026071610, '跟踪商机', 'biz:opportunity:query', 2, 1, 2026071613, 'opportunity', 'ep:promotion', 'biz/opportunity/index', 'BizOpportunity', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
  (2026071611, '投标报价', 'biz:bid:query', 2, 2, 2026071613, 'bid', 'ep:tickets', 'biz/bid/index', 'BizBid', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
  (2026071612, '项目管理', '', 1, 12, 0, '/biz-project', 'ep:folder-opened', NULL, NULL, 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
  (2026071613, '商机管理', '', 1, 13, 0, '/biz-opportunity', 'ep:promotion', NULL, NULL, 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0')
ON DUPLICATE KEY UPDATE
  name = VALUES(name),
  permission = VALUES(permission),
  type = VALUES(type),
  sort = VALUES(sort),
  parent_id = VALUES(parent_id),
  path = VALUES(path),
  icon = VALUES(icon),
  component = VALUES(component),
  component_name = VALUES(component_name),
  status = VALUES(status),
  visible = VALUES(visible),
  keep_alive = VALUES(keep_alive),
  always_show = VALUES(always_show),
  update_time = NOW();

INSERT INTO biz_contract (id, contract_no, name, customer_name, project_id, contract_type, amount, sign_date, start_date, end_date, due_settle_amount, settled_amount, invoiced_amount, received_amount, status, attachment_url, creator, updater, tenant_id)
VALUES
  (202607160101, 'HT202607001', '智能客服平台建设合同', '某某科技有限公司', 202607160201, '项目交付', 2600000, '2026-07-03', '2026-07-03', '2026-12-31', 780000, 780000, 780000, 780000, '履约中', 'project-contract.pdf', 'admin', 'admin', 1),
  (202607160102, 'HT202606018', '数据治理平台运维服务合同', '客户数据有限公司', 202607160203, '运维服务', 1800000, '2026-06-05', '2026-06-05', '2027-06-04', 450000, 250000, 250000, 180000, '履约中', 'operation-contract.pdf', 'admin', 'admin', 1),
  (202607160103, 'HT202607008', '数据资产治理咨询合同', '城市运营集团', NULL, '项目交付', 800000, '2026-07-14', '2026-07-20', '2026-10-31', 240000, 0, 0, 0, '已签订', NULL, 'admin', 'admin', 1)
ON DUPLICATE KEY UPDATE name = VALUES(name), amount = VALUES(amount), update_time = NOW();

INSERT INTO biz_project (id, project_no, name, type, owner_name, members, contract_id, output_calc_type, plan_start_date, plan_end_date, actual_start_date, status, completion_rate, accumulated_output, actual_cost, description, creator, updater, tenant_id)
VALUES
  (202607160201, 'PM202607001', '智能客服平台交付项目', '客户交付', '张三', '李四、王五、赵六', 202607160101, '合同金额 × 里程碑完成比例', '2026-07-01', '2026-09-30', '2026-07-03', '研发', 30, 780000, 410000, '为客户建设智能客服平台，覆盖知识库问答、工单分流和后台运营配置。', 'admin', 'admin', 1),
  (202607160202, 'PM202607002', '内部项目台账系统', '内部自研', '李四', '李四、赵六', NULL, '工时 × 标准人工单价', '2026-07-10', '2026-08-20', '2026-07-10', '需求评审', 15, 252000, 240000, '数字化事业部内部项目经营台账。', 'admin', 'admin', 1),
  (202607160203, 'PM202606018', '数据治理平台运维', '运维', '王五', '王五、赵六', 202607160102, '年度服务费按月分摊', '2026-06-05', '2027-06-04', '2026-06-05', '运维', 100, 2100000, 83000, '客户数据治理平台年度运维服务。', 'admin', 'admin', 1)
ON DUPLICATE KEY UPDATE name = VALUES(name), completion_rate = VALUES(completion_rate), update_time = NOW();

INSERT INTO biz_opportunity (id, opportunity_no, name, customer_name, estimated_amount, stage, expected_sign_month, owner_name, converted_to_bid, bid_id, converted_to_contract, contract_id, last_follow_date, follow_record, creator, updater, tenant_id)
VALUES
  (202607160301, 'SJ202607001', '城市运行数据中台建设', '城市运营集团', 6800000, '商务沟通', '2026-09', '张三', b'1', 202607160401, b'0', NULL, '2026-07-12', '完成方案交流，客户要求补充运维服务报价。', 'admin', 'admin', 1),
  (202607160302, 'SJ202607002', '园区智能客服升级', '某某科技有限公司', 1800000, '报价准备', '2026-08', '李四', b'0', NULL, b'0', NULL, '2026-07-10', '等待客户确认预算。', 'admin', 'admin', 1)
ON DUPLICATE KEY UPDATE name = VALUES(name), stage = VALUES(stage), update_time = NOW();

INSERT INTO biz_bid (id, bid_no, name, customer_name, bid_type, amount, submit_date, status, owner_name, opportunity_id, win_status, converted_to_contract, contract_id, expected_result_date, remark, creator, updater, tenant_id)
VALUES
  (202607160401, 'BJ202607001', '城市运行数据中台投标', '城市运营集团', '公开投标', 6800000, '2026-07-12', '已提交', '张三', 202607160301, 'PENDING', b'0', NULL, '2026-08-10', '公开招标项目，等待开标。', 'admin', 'admin', 1),
  (202607160402, 'BJ202607002', '智慧运维平台二期报价', '客户数据有限公司', '商务报价', 2600000, '2026-07-08', '已完成', '李四', NULL, 'WON', b'1', 202607160102, '2026-07-28', '已成交并进入合同流程。', 'admin', 'admin', 1)
ON DUPLICATE KEY UPDATE name = VALUES(name), amount = VALUES(amount), update_time = NOW();

INSERT INTO biz_project_progress (id, project_id, progress_month, completion_rate, remark, creator, updater, tenant_id)
VALUES
  (202607160501, 202607160201, '2026-05', 10, '项目立项和需求初稿完成', 'admin', 'admin', 1),
  (202607160502, 202607160201, '2026-06', 20, '完成核心设计评审', 'admin', 'admin', 1),
  (202607160503, 202607160201, '2026-07', 30, '研发阶段确认', 'admin', 'admin', 1)
ON DUPLICATE KEY UPDATE completion_rate = VALUES(completion_rate), remark = VALUES(remark), update_time = NOW();

INSERT INTO biz_project_worklog (id, project_id, work_month, member_name, hours, creator, updater, tenant_id)
VALUES
  (202607160601, 202607160202, '2026-07', '张三', 160, 'admin', 'admin', 1),
  (202607160602, 202607160202, '2026-07', '李四', 140, 'admin', 'admin', 1),
  (202607160603, 202607160202, '2026-07', '王五', 120, 'admin', 'admin', 1)
ON DUPLICATE KEY UPDATE hours = VALUES(hours), update_time = NOW();

INSERT INTO biz_contract_stage (id, contract_id, stage_name, receivable_amount, plan_date, paid, invoiced, received, paid_amount, invoiced_amount, received_amount, creator, updater, tenant_id)
VALUES
  (202607160701, 202607160101, '合同签订款', 780000, '2026-07-31', b'1', b'1', b'1', 780000, 780000, 780000, 'admin', 'admin', 1),
  (202607160702, 202607160101, '上线试运行款', 1040000, '2026-09-30', b'0', b'0', b'0', 0, 0, 0, 'admin', 'admin', 1),
  (202607160703, 202607160101, '终验款', 780000, '2026-12-31', b'0', b'0', b'0', 0, 0, 0, 'admin', 'admin', 1)
ON DUPLICATE KEY UPDATE receivable_amount = VALUES(receivable_amount), update_time = NOW();

INSERT INTO biz_opportunity_communication (id, opportunity_id, communication_date, content, next_action, owner_name, creator, updater, tenant_id)
VALUES
  (202607160801, 202607160301, '2026-07-03', '与客户确认数据治理范围和预算窗口。', '提交初步方案', '张三', 'admin', 'admin', 1),
  (202607160802, 202607160301, '2026-07-12', '完成方案交流，客户要求补充运维服务报价。', '准备报价', '李四', 'admin', 'admin', 1)
ON DUPLICATE KEY UPDATE content = VALUES(content), next_action = VALUES(next_action), update_time = NOW();

INSERT INTO biz_annual_target (id, target_year, output_target, contract_target, profit_target, remark, creator, updater, tenant_id)
VALUES
  (202607160901, 2026, 27000000, 30000000, 12000000, '用于年度经营看板完成率统计', 'admin', 'admin', 1),
  (202607160902, 2025, 22000000, 26000000, 9000000, '历史年度指标', 'admin', 'admin', 1)
ON DUPLICATE KEY UPDATE output_target = VALUES(output_target), contract_target = VALUES(contract_target), profit_target = VALUES(profit_target), remark = VALUES(remark), update_time = NOW();

INSERT INTO system_role_menu (role_id, menu_id, creator, updater, tenant_id)
SELECT 1, m.id, 'admin', 'admin', 1
FROM system_menu m
WHERE m.id BETWEEN 2026071601 AND 2026071613
  AND NOT EXISTS (
    SELECT 1 FROM system_role_menu rm
    WHERE rm.role_id = 1 AND rm.menu_id = m.id AND rm.deleted = b'0'
  );
