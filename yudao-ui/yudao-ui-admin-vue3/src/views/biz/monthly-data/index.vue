<template>
  <ContentWrap>
    <div class="mb-16px flex items-center justify-between">
      <div>
        <div class="text-20px font-bold">月度经营数据</div>
        <div class="mt-6px color-#606266">按月核对产值、成本、合同、应收、商机和投标报价数据来源。</div>
      </div>
      <el-date-picker v-model="month" type="month" value-format="YYYY-MM" />
    </div>
    <el-row :gutter="18" class="metric-grid">
      <el-col v-for="item in stats" :key="item.label" :span="6">
        <div class="metric-card" :class="item.color">
          <div class="metric-label">{{ item.label }}</div>
          <div class="metric-value">{{ item.value }}</div>
          <div class="metric-note">{{ item.note }}</div>
        </div>
      </el-col>
    </el-row>
  </ContentWrap>

  <ContentWrap>
    <div class="mb-12px text-16px font-bold">月度经营数据明细</div>
    <el-table :data="rows" stripe>
      <el-table-column label="指标" prop="name" />
      <el-table-column label="金额/数量" prop="value" />
      <el-table-column label="数据来源" prop="source" />
      <el-table-column label="计算口径" prop="rule" min-width="260" />
      <el-table-column label="状态" prop="status" width="100"><template #default="{ row }"><el-tag type="success">{{ row.status }}</el-tag></template></el-table-column>
      <el-table-column label="操作" width="160"><template #default><el-button link type="primary">查看来源</el-button><el-button link type="primary">核对</el-button></template></el-table-column>
    </el-table>
  </ContentWrap>
</template>

<script setup lang="ts">
defineOptions({ name: 'BizMonthlyData' })

const month = ref('2026-07')
const stats = [
  { label: '本月产值', value: '¥ 326.8 万', note: '合同里程碑 + 工时 + 运维分摊', color: 'blue-card' },
  { label: '本月实际成本', value: '¥ 184.2 万', note: '管理费用 + 人工成本', color: 'red-card' },
  { label: '新签合同', value: '¥ 520 万', note: '3 份合同', color: 'amber-card' },
  { label: '利润总额', value: '¥ 142.6 万', note: '产值 - 成本', color: 'green-card' }
]
const rows = [
  { name: '本月产值', value: '¥ 326.8 万', source: '项目产值明细', rule: '按项目类型自动计算后汇总', status: '已核对' },
  { name: '本月实际成本', value: '¥ 184.2 万', source: '项目成本明细', rule: '部门管理费用 + 人工成本', status: '已核对' },
  { name: '新签合同', value: '¥ 520 万', source: '合同列表', rule: '签订日期在本月且未作废', status: '已填报' },
  { name: '应结未结', value: '¥ 160 万', source: '合同付款阶段', rule: '应结金额 - 已结算金额', status: '已核对' },
  { name: '应收未收', value: '¥ 96 万', source: '合同开票收款', rule: '已开票金额 - 已收款金额', status: '已核对' },
  { name: '跟踪商机', value: '¥ 980 万', source: '跟踪商机', rule: '当前跟进中商机金额合计', status: '已填报' },
  { name: '投标报价', value: '¥ 760 万', source: '投标报价', rule: '本月已提交报价金额合计', status: '已填报' }
]
</script>

<style scoped>
.metric-grid {
  row-gap: 18px;
}

.metric-card {
  min-height: 118px;
  padding: 18px 20px;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  background: #fff;
  box-shadow: 0 1px 2px rgb(16 24 40 / 4%);
}

.metric-card.blue-card {
  border-left: 4px solid #2563eb;
}

.metric-card.red-card {
  border-left: 4px solid #ef4444;
}

.metric-card.amber-card {
  border-left: 4px solid #f59e0b;
}

.metric-card.green-card {
  border-left: 4px solid #10b981;
}

.metric-label {
  color: #667085;
}

.metric-value {
  margin-top: 10px;
  color: #1f2937;
  font-size: 26px;
  font-weight: 800;
}

.metric-note {
  margin-top: 8px;
  color: #667085;
  font-size: 12px;
}
</style>
