<template>
  <ContentWrap>
    <div class="mb-16px flex justify-between">
      <div>
        <div class="text-20px font-bold">项目产值明细</div>
        <div class="mt-6px color-#606266">按合同项目、内部研发项目、运维项目分别计算并汇总本月产值。</div>
      </div>
      <div><el-button>重新计算产值</el-button><el-button type="primary">确认本月产值</el-button></div>
    </div>
    <el-row :gutter="16">
      <el-col :span="6" v-for="item in stats" :key="item.label"><el-card shadow="never"><div>{{ item.label }}</div><div class="mt-8px text-24px font-bold">{{ item.value }}</div><div class="mt-6px text-12px color-#909399">{{ item.note }}</div></el-card></el-col>
    </el-row>
  </ContentWrap>
  <ContentWrap>
    <div class="mb-12px text-16px font-bold">产值明细列表</div>
    <el-table v-loading="loading" :data="rows" stripe>
      <el-table-column label="项目编号" prop="projectNo" />
      <el-table-column label="项目名称" prop="projectName" min-width="180" />
      <el-table-column label="项目类型" prop="projectType" />
      <el-table-column label="计算方式" prop="calcType" min-width="190" />
      <el-table-column label="计算基础" prop="base" />
      <el-table-column label="本月完成比例/工时" prop="input" />
      <el-table-column label="本月产值" prop="output" />
      <el-table-column label="填报状态" prop="status"><template #default="{ row }"><el-tag :type="row.status === '已确认' ? 'success' : 'danger'">{{ row.status }}</el-tag></template></el-table-column>
      <el-table-column label="操作" width="120"><template #default><el-button link type="primary">查看来源</el-button></template></el-table-column>
    </el-table>
  </ContentWrap>
</template>

<script setup lang="ts">
import { BizProjectApi } from '@/api/biz/project'

defineOptions({ name: 'BizOutputDetail' })

const loading = ref(false)
const projects = ref<any[]>([])
const formatWan = (amount?: number) => `¥ ${Number(((amount || 0) / 10000).toFixed(1)).toLocaleString()} 万`
const calcType = (type?: string) => {
  if (type === '内部自研') return '工时 × 标准人工单价'
  if (type === '运维') return '年度服务费按月分摊'
  return '合同金额 × 经确认里程碑完成比例'
}
const rows = computed(() => projects.value.map((item) => ({
  projectNo: item.projectNo,
  projectName: item.name,
  projectType: item.type,
  calcType: calcType(item.type),
  base: item.type === '内部自研' ? '按项目工时' : item.contractId ? `合同ID ${item.contractId}` : '无合同',
  input: item.type === '内部自研' ? '来源于工时记录' : `${item.completionRate || 0}%`,
  output: formatWan(item.accumulatedOutput),
  status: item.completionRate ? '已确认' : '待确认'
})))
const sumByType = (type: string) => projects.value.filter((item) => item.type === type).reduce((sum, item) => sum + (item.accumulatedOutput || 0), 0)
const stats = computed(() => [
  { label: '合同项目产值', value: formatWan(sumByType('客户交付')), note: '合同金额 × 里程碑完成比例' },
  { label: '内部研发产值', value: formatWan(sumByType('内部自研')), note: '工时 × 标准人工单价' },
  { label: '运维项目产值', value: formatWan(sumByType('运维')), note: '年度服务费按月分摊' },
  { label: '待确认项目', value: `${projects.value.filter((item) => !item.completionRate).length} 个`, note: '本月完成比例未填报' }
])
const getList = async () => {
  loading.value = true
  try {
    const data = await BizProjectApi.getProjectPage({ pageNo: 1, pageSize: 100 })
    projects.value = data.list || []
  } finally {
    loading.value = false
  }
}
onMounted(() => getList())
</script>
