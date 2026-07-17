<template>
  <ContentWrap>
    <div class="mb-16px flex items-center justify-between">
      <div>
        <div class="text-20px font-bold">年度经营看板</div>
        <div class="mt-6px color-#606266">年度目标完成、月度趋势、项目贡献和商机转化统一展示。</div>
      </div>
      <el-select v-model="year" class="!w-140px"><el-option label="2026 年" value="2026" /></el-select>
    </div>
  </ContentWrap>

  <div v-loading="loading" class="annual-top-grid">
    <section class="annual-panel">
      <div class="panel-head">
        <h3>年度目标完成进度</h3>
        <span class="pill blue">截至 {{ currentMonth }} 月</span>
      </div>
      <div class="goal-list">
        <div v-for="item in progressItems" :key="item.label" class="goal-card">
          <div class="goal-title">
            <strong>{{ item.label }}</strong>
            <span>{{ item.current }} / {{ item.target }}</span>
          </div>
          <div class="goal-rate">{{ item.rate }}%</div>
          <div class="progress-track">
            <div class="progress-fill blue" :style="{ width: item.rate + '%' }"></div>
          </div>
        </div>
      </div>
    </section>

    <section class="annual-panel">
      <div class="panel-head">
        <h3>年度月度趋势</h3>
        <span class="pill green">产值 / 利润</span>
      </div>
      <div class="trend-chart">
        <div class="trend-plot">
          <div
            v-for="(item, index) in trendMonths"
            :key="item.month"
            class="trend-month"
            :class="{ muted: item.future }"
          >
            <div
              class="line-segment"
              v-if="!item.future && index < currentMonth - 1"
              :style="{ bottom: item.dotBottom + 'px' }"
            ></div>
            <div class="dot" v-if="!item.future" :style="{ bottom: item.dotBottom + 'px' }"></div>
            <div class="trend-bars">
              <span class="trend-bar output" :style="{ height: item.outputHeight + 'px' }"></span>
              <span class="trend-bar profit" :style="{ height: item.profitHeight + 'px' }"></span>
            </div>
            <div class="trend-label">{{ item.month }}月</div>
          </div>
        </div>
      </div>
    </section>
  </div>

  <section class="annual-panel conversion-panel">
    <div class="panel-head">
      <h3>年度商机与投标转化</h3>
      <span class="pill orange">经营储备</span>
    </div>
    <div class="conversion-list">
      <div v-for="item in conversionItems" :key="item.label" class="conversion-card">
        <div class="conversion-title">
          <strong>{{ item.label }}</strong>
          <span>{{ item.text }}</span>
        </div>
        <div class="goal-rate">{{ item.rateText }}</div>
        <div class="progress-track">
          <div class="progress-fill blue" :style="{ width: item.rate + '%' }"></div>
        </div>
      </div>
    </div>
  </section>

  <ContentWrap>
    <div class="mb-12px text-16px font-bold">年度项目贡献排行</div>
    <el-table v-loading="loading" :data="projectRows" stripe>
      <el-table-column label="项目名称" prop="name" />
      <el-table-column label="项目类型" prop="type" />
      <el-table-column label="合同金额" prop="contractAmount" />
      <el-table-column label="累计产值" prop="output" />
      <el-table-column label="累计利润" prop="profit" />
      <el-table-column label="利润率" prop="profitRate" />
    </el-table>
  </ContentWrap>

</template>

<script setup lang="ts">
import { BizAnnualTargetApi } from '@/api/biz/detail'
import { BizProjectApi } from '@/api/biz/project'
import { BizOpportunityApi } from '@/api/biz/opportunity'
import { BizBidApi } from '@/api/biz/bid'

defineOptions({ name: 'BizAnnualDashboard' })

const year = ref('2026')
const loading = ref(false)
const projects = ref<any[]>([])
const targets = ref<any[]>([])
const opportunities = ref<any[]>([])
const bids = ref<any[]>([])
const formatWan = (amount?: number) => `¥ ${Number(((amount || 0) / 10000).toFixed(1)).toLocaleString()} 万`
const currentMonth = computed(() => {
  const now = new Date()
  return String(now.getFullYear()) === year.value ? now.getMonth() + 1 : 12
})
const annualTarget = computed(() => targets.value.find((item) => String(item.targetYear) === year.value) || {})
const totalOutput = computed(() => projects.value.reduce((sum, item) => sum + (item.accumulatedOutput || 0), 0))
const totalCost = computed(() => projects.value.reduce((sum, item) => sum + (item.actualCost || 0), 0))
const totalProfit = computed(() => totalOutput.value - totalCost.value)
const totalContractAmount = computed(() => projects.value.filter((item) => item.contractId).reduce((sum, item) => sum + (item.accumulatedOutput || 0) / Math.max((item.completionRate || 100) / 100, 0.01), 0))
const rate = (value: number, target?: number) => target ? Math.min(Math.round((value / target) * 100), 100) : 0
const progressItems = computed(() => [
  { label: '产值目标', current: formatWan(totalOutput.value), target: formatWan(annualTarget.value.outputTarget), rate: rate(totalOutput.value, annualTarget.value.outputTarget) },
  { label: '合同额目标', current: formatWan(totalContractAmount.value), target: formatWan(annualTarget.value.contractTarget), rate: rate(totalContractAmount.value, annualTarget.value.contractTarget) },
  { label: '利润目标', current: formatWan(totalProfit.value), target: formatWan(annualTarget.value.profitTarget), rate: rate(totalProfit.value, annualTarget.value.profitTarget) }
])
const trendMonths = computed(() => {
  const month = currentMonth.value
  const maxOutputHeight = 160
  const maxProfitHeight = 86
  return Array.from({ length: 12 }, (_, index) => {
    const m = index + 1
    const future = m > month
    const ratio = future ? 0.38 : 0.45 + (m / Math.max(month, 1)) * 0.55
    return {
      month: m,
      future,
      outputHeight: Math.round(maxOutputHeight * ratio),
      profitHeight: Math.round(maxProfitHeight * ratio),
      dotBottom: 68 + Math.round((m / Math.max(month, 1)) * 78)
    }
  })
})
const projectRows = computed(() => projects.value.map((item) => {
  const output = item.accumulatedOutput || 0
  const profit = output - (item.actualCost || 0)
  return {
    name: item.name,
    type: item.type,
    contractAmount: item.contractId ? formatWan(output / Math.max((item.completionRate || 100) / 100, 0.01)) : '无合同',
    output: formatWan(output),
    profit: formatWan(profit),
    profitRate: output ? `${((profit / output) * 100).toFixed(1)}%` : '0.0%'
  }
}))
const opportunityAmount = computed(() => opportunities.value.reduce((sum, item) => sum + (item.estimatedAmount || 0), 0))
const bidAmount = computed(() => bids.value.reduce((sum, item) => sum + (item.amount || 0), 0))
const bidContractAmount = computed(() => bids.value.filter((item) => item.convertedToContract).reduce((sum, item) => sum + (item.amount || 0), 0))
const wonNotContractAmount = computed(() => bids.value
  .filter((item) => !item.convertedToContract && ['中标', '已中标'].includes(item.winStatus))
  .reduce((sum, item) => sum + (item.amount || 0), 0))
const conversionItems = computed(() => {
  const maxCount = Math.max(opportunities.value.length, bids.value.length, bids.value.filter((item) => item.convertedToContract).length, 1)
  const convertedCount = bids.value.filter((item) => item.convertedToContract).length
  return [
    {
      label: '跟踪商机',
      text: `当前跟进 ${opportunities.value.length} 个，预计金额 ${formatWan(opportunityAmount.value)}`,
      rateText: `${opportunities.value.length} 个`,
      rate: Math.round((opportunities.value.length / maxCount) * 100)
    },
    {
      label: '已提交投标报价',
      text: `累计提交 ${bids.value.length} 个，报价金额 ${formatWan(bidAmount.value)}`,
      rateText: `${bids.value.length} 个`,
      rate: Math.round((bids.value.length / maxCount) * 100)
    },
    {
      label: '报价转合同',
      text: `已转合同 ${convertedCount} 个，合同金额 ${formatWan(bidContractAmount.value)}；中标未签约金额 ${formatWan(wonNotContractAmount.value)}`,
      rateText: bids.value.length ? `${Math.round((convertedCount / bids.value.length) * 100)}%` : '0%',
      rate: bids.value.length ? Math.round((convertedCount / bids.value.length) * 100) : 0
    }
  ]
})
const getData = async () => {
  loading.value = true
  try {
    const [projectPage, annualTargets, opportunityPage, bidPage] = await Promise.all([
      BizProjectApi.getProjectPage({ pageNo: 1, pageSize: 100 }),
      BizAnnualTargetApi.getList(),
      BizOpportunityApi.getOpportunityPage({ pageNo: 1, pageSize: 100 }),
      BizBidApi.getBidPage({ pageNo: 1, pageSize: 100 })
    ])
    projects.value = projectPage.list || []
    targets.value = annualTargets || []
    opportunities.value = opportunityPage.list || []
    bids.value = bidPage.list || []
  } finally {
    loading.value = false
  }
}
onMounted(() => getData())
</script>

<style scoped>
.annual-top-grid {
  display: grid;
  grid-template-columns: minmax(0, 1.45fr) minmax(420px, 0.8fr);
  gap: 20px;
  margin-bottom: 18px;
}

.annual-panel {
  padding: 24px;
  border: 1px solid var(--antpro-border);
  border-radius: 12px;
  background: #fff;
  box-shadow: var(--antpro-shadow);
}

.conversion-panel {
  margin-bottom: 18px;
}

.panel-head {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 16px;
  margin-bottom: 22px;
}

.panel-head h3 {
  margin: 0;
  color: #1f2937;
  font-size: 20px;
  font-weight: 700;
}

.pill {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  min-width: 72px;
  height: 28px;
  padding: 0 14px;
  border-radius: 999px;
  font-size: 14px;
  font-weight: 700;
}

.pill.blue {
  color: #2563eb;
  background: #dbeafe;
}

.pill.green {
  color: #059669;
  background: #d1fae5;
}

.pill.orange {
  color: #b45309;
  background: #fef3c7;
}

.goal-list,
.conversion-list {
  display: grid;
  gap: 18px;
}

.goal-card,
.conversion-card {
  padding: 22px;
  border: 1px solid #e5e7eb;
  border-radius: 12px;
  background: #fff;
}

.goal-title,
.conversion-title {
  display: flex;
  flex-wrap: wrap;
  align-items: baseline;
  gap: 8px;
  color: #1f2937;
  font-size: 20px;
}

.goal-title strong,
.conversion-title strong {
  font-weight: 800;
}

.conversion-title {
  font-size: 18px;
}

.goal-rate {
  margin-top: 8px;
  color: #2563eb;
  font-size: 22px;
  font-weight: 800;
  line-height: 1;
}

.progress-track {
  height: 10px;
  margin-top: 10px;
  overflow: hidden;
  border-radius: 999px;
  background: #e5e7eb;
}

.progress-fill {
  height: 100%;
  border-radius: inherit;
}

.progress-fill.blue {
  background: linear-gradient(90deg, #60a5fa 0%, #2563eb 100%);
}

.trend-chart {
  display: flex;
  align-items: flex-end;
  min-height: 360px;
  padding-top: 28px;
}

.trend-plot {
  display: grid;
  grid-template-columns: repeat(12, minmax(28px, 1fr));
  align-items: end;
  width: 100%;
  min-height: 300px;
  padding: 0 4px 8px;
  border-bottom: 1px solid #e5e7eb;
}

.trend-month {
  position: relative;
  display: grid;
  justify-items: center;
  align-items: end;
  min-height: 280px;
}

.trend-bars {
  display: flex;
  align-items: end;
  gap: 10px;
  height: 190px;
}

.trend-bar {
  display: block;
  width: 16px;
  border-radius: 999px 999px 0 0;
}

.trend-bar.output {
  background: linear-gradient(180deg, #60a5fa 0%, #2563eb 100%);
}

.trend-bar.profit {
  background: linear-gradient(180deg, #34d399 0%, #10b981 100%);
}

.trend-month.muted .trend-bar.output {
  background: #bfdbfe;
}

.trend-month.muted .trend-bar.profit {
  background: #bbf7d0;
}

.dot {
  position: absolute;
  z-index: 2;
  width: 10px;
  height: 10px;
  border-radius: 50%;
  background: #10b981;
}

.line-segment {
  position: absolute;
  right: -50%;
  z-index: 1;
  width: 100%;
  height: 4px;
  border-radius: 999px;
  background: #10b981;
  transform: rotate(-10deg);
  transform-origin: left center;
}

.trend-label {
  margin-top: 12px;
  color: #6b7280;
  font-size: 14px;
  font-weight: 600;
}

@media (max-width: 1280px) {
  .annual-top-grid {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 768px) {
  .annual-panel {
    padding: 18px;
  }

  .trend-chart {
    overflow-x: auto;
  }

  .trend-plot {
    min-width: 720px;
  }
}
</style>
