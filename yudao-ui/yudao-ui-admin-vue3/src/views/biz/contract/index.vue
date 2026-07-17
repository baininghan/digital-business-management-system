<template>
  <ContentWrap>
    <el-form ref="queryFormRef" :inline="true" :model="queryParams">
      <el-form-item label="合同名称" prop="name"><el-input v-model="queryParams.name" placeholder="请输入合同名称或编号" clearable class="!w-240px" /></el-form-item>
      <el-form-item label="客户名称" prop="customerName"><el-input v-model="queryParams.customerName" placeholder="请输入客户名称" clearable class="!w-220px" /></el-form-item>
      <el-form-item label="合同状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="全部" clearable class="!w-160px">
          <el-option v-for="item in statuses" :key="item" :label="item" :value="item" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" />搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" />重置</el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <ContentWrap>
    <div class="mb-16px flex justify-between">
      <div class="text-16px font-bold">合同台账</div>
      <div>
        <el-button><Icon icon="ep:download" class="mr-5px" />导出合同</el-button>
        <el-button type="primary" @click="openForm()"><Icon icon="ep:plus" class="mr-5px" />新增合同</el-button>
      </div>
    </div>
    <el-table v-loading="loading" :data="list" stripe>
      <el-table-column label="合同编号" prop="contractNo" min-width="140" />
      <el-table-column label="合同名称" min-width="220">
        <template #default="{ row }">
          <div class="font-bold">{{ row.name }}</div>
          <div class="text-12px color-#909399">{{ row.contractType || '-' }} · {{ row.customerName }}</div>
        </template>
      </el-table-column>
      <el-table-column label="合同金额" prop="amount" min-width="120" />
      <el-table-column label="签订日期" prop="signDate" min-width="120" />
      <el-table-column label="应结未结" min-width="120">
        <template #default="{ row }">{{ (row.dueSettleAmount || 0) - (row.settledAmount || 0) }}</template>
      </el-table-column>
      <el-table-column label="已开票" prop="invoicedAmount" min-width="120" />
      <el-table-column label="已收款" prop="receivedAmount" min-width="120" />
      <el-table-column label="应收未收" min-width="120">
        <template #default="{ row }">{{ (row.invoicedAmount || 0) - (row.receivedAmount || 0) }}</template>
      </el-table-column>
      <el-table-column label="状态" prop="status" min-width="100" />
      <el-table-column label="操作" fixed="right" width="190">
        <template #default="{ row }">
          <el-button link type="primary" @click="openForm(row)">编辑</el-button>
          <el-button link type="primary" @click="openStages(row)">付款阶段</el-button>
          <el-button link type="danger" @click="handleDelete(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <Pagination :total="total" v-model:page="queryParams.pageNo" v-model:limit="queryParams.pageSize" @pagination="getList" />
  </ContentWrap>

  <el-dialog v-model="formVisible" :title="form.id ? '编辑合同' : '新增合同'" width="760px">
    <el-form :model="form" label-width="110px">
      <el-row :gutter="16">
        <el-col :span="12"><el-form-item label="合同名称" required><el-input v-model="form.name" /></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="合同编号"><el-input v-model="form.contractNo" /></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="客户名称" required><el-input v-model="form.customerName" /></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="关联项目ID"><el-input-number v-model="form.projectId" class="w-100%" :min="1" /></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="合同类型"><el-select v-model="form.contractType" class="w-100%"><el-option v-for="item in types" :key="item" :label="item" :value="item" /></el-select></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="合同金额"><el-input-number v-model="form.amount" class="w-100%" :min="0" /></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="签订日期"><el-date-picker v-model="form.signDate" value-format="YYYY-MM-DD" type="date" class="w-100%" /></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="合同状态"><el-select v-model="form.status" class="w-100%"><el-option v-for="item in statuses" :key="item" :label="item" :value="item" /></el-select></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="合同开始"><el-date-picker v-model="form.startDate" value-format="YYYY-MM-DD" type="date" class="w-100%" /></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="合同结束"><el-date-picker v-model="form.endDate" value-format="YYYY-MM-DD" type="date" class="w-100%" /></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="应结金额"><el-input-number v-model="form.dueSettleAmount" class="w-100%" :min="0" /></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="已结算"><el-input-number v-model="form.settledAmount" class="w-100%" :min="0" /></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="已开票"><el-input-number v-model="form.invoicedAmount" class="w-100%" :min="0" /></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="已收款"><el-input-number v-model="form.receivedAmount" class="w-100%" :min="0" /></el-form-item></el-col>
        <el-col :span="24"><el-form-item label="合同附件"><el-input v-model="form.attachmentUrl" placeholder="附件 URL 或文件标识" /></el-form-item></el-col>
      </el-row>
    </el-form>
    <el-divider v-if="form.id">收付款阶段、开票、收款</el-divider>
    <el-table v-if="form.id" :data="stageRows" border>
      <el-table-column label="阶段" prop="stageName" />
      <el-table-column label="应收金额" prop="receivableAmount" />
      <el-table-column label="计划日期" prop="planDate" />
      <el-table-column label="已付款">
        <template #default="{ row }">{{ row.paid ? '是' : '否' }}</template>
      </el-table-column>
      <el-table-column label="已开票">
        <template #default="{ row }">{{ row.invoiced ? '是' : '否' }}</template>
      </el-table-column>
      <el-table-column label="已收款">
        <template #default="{ row }">{{ row.received ? '是' : '否' }}</template>
      </el-table-column>
    </el-table>
    <template #footer>
      <el-button @click="formVisible = false">取消</el-button>
      <el-button type="primary" :loading="submitLoading" @click="submitForm">保存</el-button>
    </template>
  </el-dialog>

  <el-dialog v-model="stageVisible" title="合同付款阶段" width="760px">
    <el-form :model="stageForm" :inline="true" class="mb-12px">
      <el-form-item label="阶段"><el-input v-model="stageForm.stageName" class="!w-140px" placeholder="阶段名称" /></el-form-item>
      <el-form-item label="应收金额"><el-input-number v-model="stageForm.receivableAmount" :min="0" class="!w-150px" /></el-form-item>
      <el-form-item label="计划日期"><el-date-picker v-model="stageForm.planDate" type="date" value-format="YYYY-MM-DD" class="!w-150px" /></el-form-item>
      <el-form-item><el-checkbox v-model="stageForm.paid">已付款</el-checkbox></el-form-item>
      <el-form-item><el-checkbox v-model="stageForm.invoiced">已开票</el-checkbox></el-form-item>
      <el-form-item><el-checkbox v-model="stageForm.received">已收款</el-checkbox></el-form-item>
      <el-form-item><el-button type="primary" @click="createStage">新增阶段</el-button></el-form-item>
    </el-form>
    <el-table :data="stageRows" border>
      <el-table-column label="阶段" prop="stageName" />
      <el-table-column label="应收金额" prop="receivableAmount" />
      <el-table-column label="计划日期" prop="planDate" />
      <el-table-column label="已付款">
        <template #default="{ row }">{{ row.paid ? '是' : '否' }}</template>
      </el-table-column>
      <el-table-column label="已开票">
        <template #default="{ row }">{{ row.invoiced ? '是' : '否' }}</template>
      </el-table-column>
      <el-table-column label="已收款">
        <template #default="{ row }">{{ row.received ? '是' : '否' }}</template>
      </el-table-column>
      <el-table-column label="操作" width="90">
        <template #default="{ row }"><el-button link type="danger" @click="deleteStage(row)">删除</el-button></template>
      </el-table-column>
    </el-table>
  </el-dialog>
</template>

<script setup lang="ts">
import { BizContractApi } from '@/api/biz/contract'
import { BizContractStageApi } from '@/api/biz/detail'

defineOptions({ name: 'BizContract' })

const message = useMessage()
const loading = ref(false)
const submitLoading = ref(false)
const list = ref<any[]>([])
const total = ref(0)
const queryFormRef = ref()
const queryParams = reactive({ pageNo: 1, pageSize: 10, name: undefined, customerName: undefined, status: undefined })
const types = ['项目交付', '运维服务', '框架协议', '其他']
const statuses = ['已签订', '履约中', '已完成', '已作废']
const formVisible = ref(false)
const stageVisible = ref(false)
const form = reactive<any>({})
const currentContract = ref<any>()
const stageRows = ref<any[]>([])
const stageForm = reactive<any>({ stageName: '', receivableAmount: 0, planDate: '', paid: false, invoiced: false, received: false })
const getList = async () => {
  loading.value = true
  try {
    const data = await BizContractApi.getContractPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}
const handleQuery = () => { queryParams.pageNo = 1; getList() }
const resetQuery = () => { queryFormRef.value?.resetFields(); handleQuery() }
const resetStageForm = () => Object.assign(stageForm, { stageName: '', receivableAmount: 0, planDate: '', paid: false, invoiced: false, received: false })
const loadStages = async (contractId?: number) => {
  stageRows.value = contractId ? await BizContractStageApi.getList(contractId) : []
}
const openForm = async (row?: any) => {
  Object.assign(form, row ? { ...row } : { name: '', customerName: '', contractType: '项目交付', status: '已签订', amount: 0, dueSettleAmount: 0, settledAmount: 0, invoicedAmount: 0, receivedAmount: 0 })
  await loadStages(row?.id)
  formVisible.value = true
}
const openStages = async (row: any) => {
  currentContract.value = row
  resetStageForm()
  await loadStages(row.id)
  stageVisible.value = true
}
const createStage = async () => {
  if (!currentContract.value?.id) return
  await BizContractStageApi.create({ ...stageForm, contractId: currentContract.value.id })
  message.success('新增成功')
  resetStageForm()
  await loadStages(currentContract.value.id)
}
const deleteStage = async (row: any) => {
  await message.confirm(`确认删除阶段「${row.stageName}」吗？`)
  await BizContractStageApi.delete(row.id)
  message.success('删除成功')
  await loadStages(currentContract.value?.id)
}
const submitForm = async () => {
  submitLoading.value = true
  try {
    form.id ? await BizContractApi.updateContract(form) : await BizContractApi.createContract(form)
    message.success('保存成功')
    formVisible.value = false
    getList()
  } finally {
    submitLoading.value = false
  }
}
const handleDelete = async (row: any) => {
  await message.confirm(`确认删除合同「${row.name}」吗？`)
  await BizContractApi.deleteContract(row.id)
  message.success('删除成功')
  getList()
}
onMounted(() => getList())
</script>
