<template>
  <ContentWrap>
    <div class="mb-16px text-20px font-bold">年度指标</div>
    <el-form :model="form" label-width="130px">
      <el-row :gutter="16">
        <el-col :span="8"><el-form-item label="年度"><el-date-picker v-model="form.targetYear" type="year" value-format="YYYY" class="w-100%" /></el-form-item></el-col>
        <el-col :span="8"><el-form-item label="年度产值目标"><el-input-number v-model="form.outputTarget" class="w-100%" :min="0" /></el-form-item></el-col>
        <el-col :span="8"><el-form-item label="年度合同额目标"><el-input-number v-model="form.contractTarget" class="w-100%" :min="0" /></el-form-item></el-col>
        <el-col :span="8"><el-form-item label="年度利润目标"><el-input-number v-model="form.profitTarget" class="w-100%" :min="0" /></el-form-item></el-col>
        <el-col :span="16"><el-form-item label="指标说明"><el-input v-model="form.remark" /></el-form-item></el-col>
      </el-row>
      <el-button type="primary" :loading="loading" @click="saveTarget"><Icon icon="ep:check" class="mr-5px" />保存年度指标</el-button>
      <el-button @click="resetForm"><Icon icon="ep:refresh" class="mr-5px" />新增年度</el-button>
    </el-form>
  </ContentWrap>
  <ContentWrap>
    <div class="mb-12px text-16px font-bold">年度指标历史</div>
    <el-table v-loading="loading" :data="rows" stripe>
      <el-table-column label="年度" prop="targetYear" />
      <el-table-column label="产值目标">
        <template #default="{ row }">{{ formatWan(row.outputTarget) }}</template>
      </el-table-column>
      <el-table-column label="合同额目标">
        <template #default="{ row }">{{ formatWan(row.contractTarget) }}</template>
      </el-table-column>
      <el-table-column label="利润目标">
        <template #default="{ row }">{{ formatWan(row.profitTarget) }}</template>
      </el-table-column>
      <el-table-column label="指标说明" prop="remark" min-width="180" />
      <el-table-column label="操作" width="150">
        <template #default="{ row }">
          <el-button link type="primary" @click="editTarget(row)">编辑</el-button>
          <el-button link type="danger" @click="deleteTarget(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </ContentWrap>
</template>

<script setup lang="ts">
import { BizAnnualTargetApi } from '@/api/biz/detail'

defineOptions({ name: 'BizAnnualTarget' })

const message = useMessage()
const loading = ref(false)
const rows = ref<any[]>([])
const form = reactive<any>({ id: undefined, targetYear: '2026', outputTarget: 27000000, contractTarget: 30000000, profitTarget: 12000000, remark: '用于年度经营看板完成率统计' })
const formatWan = (amount?: number) => `¥ ${Number(((amount || 0) / 10000).toFixed(1)).toLocaleString()} 万`
const getList = async () => {
  loading.value = true
  try {
    rows.value = await BizAnnualTargetApi.getList()
  } finally {
    loading.value = false
  }
}
const resetForm = () => Object.assign(form, { id: undefined, targetYear: String(new Date().getFullYear()), outputTarget: 0, contractTarget: 0, profitTarget: 0, remark: '' })
const editTarget = (row: any) => Object.assign(form, { ...row, targetYear: String(row.targetYear) })
const saveTarget = async () => {
  loading.value = true
  try {
    const data = { ...form, targetYear: Number(form.targetYear) }
    form.id ? await BizAnnualTargetApi.update(data) : await BizAnnualTargetApi.create(data)
    message.success('保存成功')
    await getList()
  } finally {
    loading.value = false
  }
}
const deleteTarget = async (row: any) => {
  await message.confirm(`确认删除 ${row.targetYear} 年指标吗？`)
  await BizAnnualTargetApi.delete(row.id)
  message.success('删除成功')
  await getList()
}
onMounted(() => getList())
</script>
