<template>
  <ContentWrap>
    <div class="dashboard-head">
      <div>
        <div class="page-title">月度经营看板</div>
        <div class="page-subtitle">产值、利润、合同、成本、应收和商机统一统计，金额单位：万元</div>
      </div>
      <div class="head-actions">
        <el-date-picker v-model="queryParams.month" type="month" value-format="YYYY-MM" class="!w-160px" />
        <el-button @click="getSummary"><Icon icon="ep:refresh" class="mr-5px" />刷新数据</el-button>
      </div>
    </div>
  </ContentWrap>

  <ContentWrap>
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

  <div class="analysis-grid">
    <ContentWrap>
      <div class="chart-head">
        <div class="section-title">月度产值 / 利润趋势</div>
        <div class="legend">
          <span><i class="dot blue"></i>产值</span>
          <span><i class="dot green"></i>利润</span>
          <span>单位：万元</span>
        </div>
      </div>
      <div class="combo-chart">
        <svg class="trend-line" viewBox="0 0 700 250" preserveAspectRatio="none" aria-hidden="true">
          <polyline :points="profitLinePoints" fill="none" stroke="#10b981" stroke-width="5" stroke-linecap="round" stroke-linejoin="round" />
          <circle v-for="point in profitPoints" :key="point.x" :cx="point.x" :cy="point.y" r="7" fill="#10b981" />
        </svg>
        <div v-for="item in trends" :key="item.month" class="month-group">
          <div class="bars">
            <div class="bar output" :style="{ height: item.outputHeight + 'px' }"></div>
            <div class="bar profit" :style="{ height: item.profitHeight + 'px' }"></div>
          </div>
          <div class="month-label">{{ item.month }}</div>
        </div>
      </div>
    </ContentWrap>

    <ContentWrap>
      <div class="annual-head">
        <div class="section-title">年度累计统计</div>
        <span>截至 {{ currentMonthText }}</span>
      </div>
      <div class="annual-list">
        <div v-for="item in annualStats" :key="item.label" class="annual-card">
          <div class="annual-row">
            <strong>{{ item.label }}</strong>
            <span>{{ item.value }}</span>
          </div>
          <div class="progress-track">
            <i :class="item.color" :style="{ width: item.rate + '%' }"></i>
          </div>
          <div class="annual-note">年度目标 {{ item.target }}，完成 {{ item.rate }}%</div>
        </div>
      </div>
    </ContentWrap>
  </div>

  <ContentWrap>
    <div class="chart-head">
      <div class="section-title">项目合同金额 / 累计产值 / 累计利润对比</div>
      <div class="legend">
        <span><i class="dot blue"></i>合同金额</span>
        <span><i class="dot cyan"></i>累计产值</span>
        <span><i class="dot green"></i>累计利润</span>
        <span>单位：万元</span>
      </div>
    </div>
    <div class="project-compare">
      <div v-for="project in projectCompare" :key="project.name" class="project-row">
        <div class="project-info">
          <strong>{{ project.name }}</strong>
          <span>{{ project.type }} · {{ project.owner }}</span>
        </div>
        <div class="project-bars">
          <div v-for="bar in project.items" :key="bar.label" class="compare-line">
            <span class="compare-label">{{ bar.label }}</span>
            <div class="compare-track"><i :class="bar.color" :style="{ width: bar.rate + '%' }"></i></div>
            <strong>{{ bar.value }}</strong>
          </div>
        </div>
      </div>
    </div>
  </ContentWrap>

  <ContentWrap>
    <div class="mb-12px text-16px font-bold">产值与成本计算口径</div>
    <el-descriptions :column="1" border>
      <el-descriptions-item label="合同项目产值">合同金额 × 经确认里程碑完成比例。</el-descriptions-item>
      <el-descriptions-item label="内部研发项目产值">项目投入工时 × 标准人工单价。</el-descriptions-item>
      <el-descriptions-item label="运维项目产值">年度服务费按履约月份分摊。</el-descriptions-item>
      <el-descriptions-item label="本月实际成本">部门管理费用 + 人工成本；人工成本 = 人员工资 + 五险一金费用。</el-descriptions-item>
      <el-descriptions-item label="应结未结 / 应收未收">来自合同付款阶段、开票和收款数据。</el-descriptions-item>
    </el-descriptions>
  </ContentWrap>
</template>

<script setup lang="ts">
import { BizDashboardApi } from '@/api/biz/dashboard'

defineOptions({ name: 'BizDashboardContent' })

const getCurrentMonth = () => {
  const now = new Date()
  const year = now.getFullYear()
  const month = `${now.getMonth() + 1}`.padStart(2, '0')
  return `${year}-${month}`
}
const queryParams = reactive({ month: getCurrentMonth() })
const summary = ref<any>({})
const currentMonthText = computed(() => {
  const [year, month] = queryParams.month.split('-')
  return `${year} 年 ${Number(month)} 月`
})
const formatWan = (value?: number | string) => {
  const amount = Number(value || 0)
  const wan = amount / 10000
  return `¥ ${wan.toLocaleString('zh-CN', {
    minimumFractionDigits: 0,
    maximumFractionDigits: 2
  })} 万`
}
const stats = computed(() => [
  { label: '本月产值', value: formatWan(summary.value.monthOutput), note: '合同里程碑 + 工时 + 运维分摊', color: 'blue-card' },
  { label: '本月实际成本', value: formatWan(summary.value.monthActualCost), note: '管理费用 + 人工成本', color: 'red-card' },
  { label: '新签合同金额', value: formatWan(summary.value.newContractAmount), note: '按签订日期统计', color: 'amber-card' },
  { label: '利润总额', value: formatWan(summary.value.monthProfit), note: '本月产值 - 实际成本', color: 'green-card' },
  { label: '应结未结', value: formatWan(summary.value.dueUnsettledAmount), note: '应结金额 - 已结算', color: 'violet-card' },
  { label: '应收未收', value: formatWan(summary.value.receivableUnreceivedAmount), note: '已开票 - 已收款', color: 'cyan-card' },
  { label: '跟踪商机', value: formatWan(summary.value.trackingOpportunityAmount), note: '跟进中商机金额', color: 'blue-card' },
  { label: '已转合同金额', value: formatWan(summary.value.convertedContractAmount), note: '商机/报价转合同', color: 'green-card' }
])
const trends = [
  { month: '1月', outputHeight: 110, profitHeight: 58 },
  { month: '2月', outputHeight: 126, profitHeight: 65 },
  { month: '3月', outputHeight: 148, profitHeight: 78 },
  { month: '4月', outputHeight: 164, profitHeight: 88 },
  { month: '5月', outputHeight: 185, profitHeight: 98 },
  { month: '6月', outputHeight: 202, profitHeight: 108 },
  { month: '7月', outputHeight: 218, profitHeight: 126 }
]
const profitPoints = [
  { x: 50, y: 154 },
  { x: 150, y: 137 },
  { x: 250, y: 113 },
  { x: 350, y: 96 },
  { x: 450, y: 72 },
  { x: 550, y: 54 },
  { x: 650, y: 24 }
]
const profitLinePoints = computed(() => profitPoints.map((point) => `${point.x},${point.y}`).join(' '))
const annualStats = [
  { label: '累计产值', value: '¥ 1,846 万', target: '2,700 万', rate: 68, color: 'blue' },
  { label: '累计利润', value: '¥ 738 万', target: '1,200 万', rate: 61, color: 'green' },
  { label: '累计新签合同', value: '¥ 2,180 万', target: '3,000 万', rate: 73, color: 'amber' }
]
const projectCompare = [
  {
    name: '智能客服平台交付项目',
    type: '客户交付',
    owner: '张三',
    items: [
      { label: '合同金额', value: 260, rate: 100, color: 'blue' },
      { label: '累计产值', value: 78, rate: 30, color: 'cyan' },
      { label: '累计利润', value: 46, rate: 18, color: 'green' }
    ]
  },
  {
    name: '数据治理平台运维',
    type: '运维服务',
    owner: '王五',
    items: [
      { label: '合同金额', value: 180, rate: 69, color: 'blue' },
      { label: '累计产值', value: 60, rate: 33, color: 'cyan' },
      { label: '累计利润', value: 36, rate: 20, color: 'green' }
    ]
  },
  {
    name: '移动端门户升级',
    type: '补充协议',
    owner: '赵六',
    items: [
      { label: '合同金额', value: 80, rate: 31, color: 'blue' },
      { label: '累计产值', value: 20, rate: 25, color: 'cyan' },
      { label: '累计利润', value: 10, rate: 13, color: 'green' }
    ]
  },
  {
    name: '内部项目台账系统',
    type: '内部自研',
    owner: '李四',
    items: [
      { label: '合同金额', value: 0, rate: 0, color: 'blue' },
      { label: '累计产值', value: 25, rate: 32, color: 'cyan' },
      { label: '累计利润', value: 6, rate: 8, color: 'green' }
    ]
  }
]
const getSummary = async () => {
  summary.value = await BizDashboardApi.getSummary(queryParams)
}
onMounted(() => getSummary())
</script>

<style scoped>
.dashboard-head {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 16px;
}
.page-title,
.section-title {
  color: #1f2937;
  font-size: 20px;
  font-weight: 700;
}
.page-subtitle {
  margin-top: 8px;
  color: #667085;
}
.head-actions,
.chart-head,
.annual-head {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 12px;
}
.metric-card {
  min-height: 118px;
  padding: 18px 20px;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  background: #fff;
  box-shadow: 0 1px 2px rgb(16 24 40 / 4%);
}
.metric-grid {
  row-gap: 18px;
}
.metric-card.blue-card { border-left: 4px solid #2563eb; }
.metric-card.red-card { border-left: 4px solid #ef4444; }
.metric-card.amber-card { border-left: 4px solid #f59e0b; }
.metric-card.green-card { border-left: 4px solid #10b981; }
.metric-card.violet-card { border-left: 4px solid #8b5cf6; }
.metric-card.cyan-card { border-left: 4px solid #06b6d4; }
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
.analysis-grid {
  display: grid;
  grid-template-columns: minmax(0, 1.7fr) minmax(360px, .95fr);
  gap: 16px;
}
.legend {
  display: flex;
  align-items: center;
  gap: 18px;
  color: #667085;
  font-weight: 600;
}
.dot {
  display: inline-block;
  width: 10px;
  height: 10px;
  margin-right: 6px;
  border-radius: 50%;
}
.blue { background: #2563eb; }
.green { background: #10b981; }
.cyan { background: #06b6d4; }
.amber { background: #f59e0b; }
.combo-chart {
  position: relative;
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  align-items: end;
  height: 330px;
  margin-top: 24px;
  padding: 28px 20px 0;
  border-bottom: 1px solid #e5e7eb;
}
.trend-line {
  position: absolute;
  right: 20px;
  bottom: 38px;
  left: 20px;
  width: calc(100% - 40px);
  height: 250px;
  pointer-events: none;
}
.month-group {
  position: relative;
  z-index: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: end;
  min-width: 72px;
}
.bars {
  display: flex;
  align-items: end;
  height: 250px;
  gap: 8px;
}
.bar {
  width: 18px;
  border-radius: 9px 9px 0 0;
}
.bar.output {
  background: linear-gradient(180deg, #60a5fa, #2563eb);
}
.bar.profit {
  background: linear-gradient(180deg, #34d399, #10b981);
}
.month-label {
  margin-top: 12px;
  color: #667085;
  font-weight: 700;
}
.annual-head span {
  color: #667085;
  font-weight: 600;
}
.annual-list {
  display: grid;
  gap: 18px;
  margin-top: 28px;
}
.annual-card {
  padding: 20px;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
}
.annual-row {
  display: flex;
  align-items: center;
  justify-content: space-between;
  color: #1f2937;
  font-size: 20px;
}
.annual-row span {
  color: #2563eb;
  font-weight: 800;
}
.progress-track,
.compare-track {
  overflow: hidden;
  height: 10px;
  border-radius: 999px;
  background: #e5e7eb;
}
.progress-track {
  margin-top: 16px;
}
.progress-track i,
.compare-track i {
  display: block;
  height: 100%;
  border-radius: 999px;
}
.annual-note {
  margin-top: 14px;
  color: #667085;
  font-weight: 600;
}
.project-compare {
  display: grid;
  gap: 24px;
  margin-top: 24px;
}
.project-row {
  display: grid;
  grid-template-columns: 280px minmax(0, 1fr);
  gap: 28px;
}
.project-info {
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.project-info strong {
  color: #1f2937;
  font-size: 20px;
}
.project-info span {
  margin-top: 8px;
  color: #667085;
}
.project-bars {
  display: grid;
  gap: 12px;
}
.compare-line {
  display: grid;
  grid-template-columns: 96px minmax(0, 1fr) 44px;
  align-items: center;
  gap: 14px;
}
.compare-label {
  color: #667085;
  font-weight: 600;
}
.compare-line strong {
  color: #475467;
}
@media (max-width: 1200px) {
  .analysis-grid {
    grid-template-columns: 1fr;
  }
  .project-row {
    grid-template-columns: 1fr;
    gap: 12px;
  }
}
</style>
