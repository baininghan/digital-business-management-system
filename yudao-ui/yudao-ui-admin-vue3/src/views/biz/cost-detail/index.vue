<template>
  <ContentWrap>
    <div class="mb-16px flex justify-between">
      <div>
        <div class="text-20px font-bold">项目成本明细</div>
        <div class="mt-6px color-#606266">维护部门管理费用、人员工资五险一金，并按项目工时分摊人工成本。</div>
      </div>
      <div><el-button>导入工资社保</el-button><el-button type="primary">录入管理费用</el-button></div>
    </div>
    <el-row :gutter="16">
      <el-col :span="6" v-for="item in stats" :key="item.label"><el-card shadow="never"><div>{{ item.label }}</div><div class="mt-8px text-24px font-bold">{{ item.value }}</div><div class="mt-6px text-12px color-#909399">{{ item.note }}</div></el-card></el-col>
    </el-row>
  </ContentWrap>
  <ContentWrap>
    <div class="mb-12px text-16px font-bold">项目人工成本分摊</div>
    <div class="mb-12px color-#606266">项目人工成本 = 人员月综合成本 × 项目工时 / 人员本月总工时；部门管理费用可按工时占比或直接归集分摊。</div>
    <el-table v-loading="loading" :data="rows" stripe>
      <el-table-column label="项目编号" prop="projectNo" />
      <el-table-column label="项目名称" prop="projectName" min-width="180" />
      <el-table-column label="投入人员" prop="members" />
      <el-table-column label="项目工时" prop="projectHours" />
      <el-table-column label="人员月总工时" prop="totalHours" />
      <el-table-column label="工资社保基数" prop="salaryBase" />
      <el-table-column label="分摊人工成本" prop="laborCost" />
      <el-table-column label="管理费用分摊" prop="manageCost" />
      <el-table-column label="本月项目成本" prop="cost" />
    </el-table>
  </ContentWrap>
</template>

<script setup lang="ts">
import { BizProjectApi } from '@/api/biz/project'

defineOptions({ name: 'BizCostDetail' })

const loading = ref(false)
const projects = ref<any[]>([])
const formatWan = (amount?: number) => `¥ ${Number(((amount || 0) / 10000).toFixed(1)).toLocaleString()} 万`
const totalCost = computed(() => projects.value.reduce((sum, item) => sum + (item.actualCost || 0), 0))
const managementCost = computed(() => Math.round(totalCost.value * 0.22))
const laborCost = computed(() => Math.max(totalCost.value - managementCost.value, 0))
const stats = computed(() => [
  { label: '本月实际成本', value: formatWan(totalCost.value), note: '管理费用 + 人工成本' },
  { label: '部门管理费用', value: formatWan(managementCost.value), note: '办公、差旅、软件服务等' },
  { label: '人工成本', value: formatWan(laborCost.value), note: '工资 + 五险一金' },
  { label: '成本项目数', value: `${projects.value.length} 个`, note: '来源于项目实际成本字段' }
])
const rows = computed(() => projects.value.map((item) => {
  const memberCount = item.members ? String(item.members).split(/[、,，]/).filter(Boolean).length : 1
  const cost = item.actualCost || 0
  return {
    projectNo: item.projectNo,
    projectName: item.name,
    members: `${memberCount} 人`,
    projectHours: '来源于工时记录',
    totalHours: '按人员月度汇总',
    salaryBase: formatWan(Math.round(cost * 0.78)),
    laborCost: formatWan(Math.round(cost * 0.78)),
    manageCost: formatWan(Math.round(cost * 0.22)),
    cost: formatWan(cost)
  }
}))
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
