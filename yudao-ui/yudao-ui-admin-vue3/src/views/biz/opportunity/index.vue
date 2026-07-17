<template>
  <ContentWrap>
    <el-form ref="queryFormRef" :inline="true" :model="queryParams">
      <el-form-item label="商机名称" prop="name"><el-input v-model="queryParams.name" placeholder="请输入商机名称或客户" clearable class="!w-240px" /></el-form-item>
      <el-form-item label="客户名称" prop="customerName"><el-input v-model="queryParams.customerName" placeholder="请输入客户名称" clearable class="!w-220px" /></el-form-item>
      <el-form-item label="商机阶段" prop="stage">
        <el-select v-model="queryParams.stage" placeholder="全部" clearable class="!w-160px">
          <el-option v-for="item in stages" :key="item" :label="item" :value="item" />
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
      <div class="text-16px font-bold">商机列表</div>
      <div>
        <el-button><Icon icon="ep:download" class="mr-5px" />导出商机</el-button>
        <el-button type="primary" @click="openForm()"><Icon icon="ep:plus" class="mr-5px" />新增商机</el-button>
      </div>
    </div>
    <el-table v-loading="loading" :data="list" stripe>
      <el-table-column label="商机编号" prop="opportunityNo" min-width="140" />
      <el-table-column label="商机名称" min-width="220">
        <template #default="{ row }">
          <div class="font-bold">{{ row.name }}</div>
          <div class="text-12px color-#909399">{{ row.customerName || '-' }}</div>
        </template>
      </el-table-column>
      <el-table-column label="预计金额" prop="estimatedAmount" min-width="120" />
      <el-table-column label="商机阶段" prop="stage" min-width="120" />
      <el-table-column label="预计签约月份" prop="expectedSignMonth" min-width="130" />
      <el-table-column label="是否转投标" min-width="120"><template #default="{ row }"><el-tag :type="row.convertedToBid ? 'success' : 'info'">{{ row.convertedToBid ? '是' : '否' }}</el-tag></template></el-table-column>
      <el-table-column label="是否转合同" min-width="120"><template #default="{ row }"><el-tag :type="row.convertedToContract ? 'success' : 'info'">{{ row.convertedToContract ? '是' : '否' }}</el-tag></template></el-table-column>
      <el-table-column label="负责人" prop="ownerName" min-width="100" />
      <el-table-column label="操作" fixed="right" width="220">
        <template #default="{ row }">
          <el-button link type="primary" @click="openForm(row)">编辑</el-button>
          <el-button link type="primary" @click="openCommunication(row)">沟通记录</el-button>
          <el-button link type="danger" @click="handleDelete(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <Pagination :total="total" v-model:page="queryParams.pageNo" v-model:limit="queryParams.pageSize" @pagination="getList" />
  </ContentWrap>

  <el-dialog v-model="formVisible" :title="form.id ? '编辑商机' : '新增商机'" width="720px">
    <el-form :model="form" label-width="120px">
      <el-row :gutter="16">
        <el-col :span="12"><el-form-item label="商机名称" required><el-input v-model="form.name" /></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="商机编号"><el-input v-model="form.opportunityNo" /></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="客户名称"><el-input v-model="form.customerName" /></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="预计金额"><el-input-number v-model="form.estimatedAmount" class="w-100%" :min="0" /></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="商机阶段"><el-select v-model="form.stage" class="w-100%"><el-option v-for="item in stages" :key="item" :label="item" :value="item" /></el-select></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="预计签约月份"><el-date-picker v-model="form.expectedSignMonth" type="month" value-format="YYYY-MM" class="w-100%" /></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="负责人"><el-input v-model="form.ownerName" /></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="是否转投标"><el-switch v-model="form.convertedToBid" /></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="关联报价ID"><el-input-number v-model="form.bidId" class="w-100%" :min="1" /></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="是否转合同"><el-switch v-model="form.convertedToContract" /></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="关联合同ID"><el-input-number v-model="form.contractId" class="w-100%" :min="1" /></el-form-item></el-col>
        <el-col :span="24"><el-form-item label="跟进记录"><el-input v-model="form.followRecord" type="textarea" :rows="3" /></el-form-item></el-col>
      </el-row>
    </el-form>
    <template #footer><el-button @click="formVisible = false">取消</el-button><el-button type="primary" :loading="submitLoading" @click="submitForm">保存</el-button></template>
  </el-dialog>

  <el-dialog v-model="communicationVisible" title="商机沟通记录" width="760px">
    <el-form :model="communicationForm" :inline="true" class="mb-12px">
      <el-form-item label="日期"><el-date-picker v-model="communicationForm.communicationDate" type="date" value-format="YYYY-MM-DD" class="!w-150px" /></el-form-item>
      <el-form-item label="沟通过程"><el-input v-model="communicationForm.content" class="!w-220px" placeholder="请输入沟通过程" /></el-form-item>
      <el-form-item label="下一步"><el-input v-model="communicationForm.nextAction" class="!w-180px" placeholder="下一步动作" /></el-form-item>
      <el-form-item label="跟进人"><el-input v-model="communicationForm.ownerName" class="!w-110px" /></el-form-item>
      <el-form-item><el-button type="primary" @click="createCommunication">新增记录</el-button></el-form-item>
    </el-form>
    <el-table :data="communicationRows" border>
      <el-table-column label="日期" prop="communicationDate" width="130" />
      <el-table-column label="沟通过程" prop="content" />
      <el-table-column label="下一步动作" prop="nextAction" />
      <el-table-column label="跟进人" prop="ownerName" width="100" />
      <el-table-column label="操作" width="90">
        <template #default="{ row }"><el-button link type="danger" @click="deleteCommunication(row)">删除</el-button></template>
      </el-table-column>
    </el-table>
  </el-dialog>
</template>

<script setup lang="ts">
import { BizOpportunityApi } from '@/api/biz/opportunity'
import { BizOpportunityCommunicationApi } from '@/api/biz/detail'

defineOptions({ name: 'BizOpportunity' })

const message = useMessage()
const loading = ref(false)
const submitLoading = ref(false)
const list = ref<any[]>([])
const total = ref(0)
const queryFormRef = ref()
const queryParams = reactive({ pageNo: 1, pageSize: 10, name: undefined, customerName: undefined, stage: undefined })
const stages = ['初步接触', '方案交流', '商务沟通', '报价准备', '已转投标', '已关闭']
const formVisible = ref(false)
const communicationVisible = ref(false)
const form = reactive<any>({})
const currentOpportunity = ref<any>()
const communicationRows = ref<any[]>([])
const communicationForm = reactive<any>({ communicationDate: '', content: '', nextAction: '', ownerName: '' })
const getList = async () => {
  loading.value = true
  try {
    const data = await BizOpportunityApi.getOpportunityPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}
const handleQuery = () => { queryParams.pageNo = 1; getList() }
const resetQuery = () => { queryFormRef.value?.resetFields(); handleQuery() }
const openForm = (row?: any) => {
  Object.assign(form, row ? { ...row } : { name: '', stage: '初步接触', estimatedAmount: 0, convertedToBid: false, convertedToContract: false })
  formVisible.value = true
}
const resetCommunicationForm = () => Object.assign(communicationForm, { communicationDate: '', content: '', nextAction: '', ownerName: '' })
const loadCommunications = async (opportunityId?: number) => {
  communicationRows.value = opportunityId ? await BizOpportunityCommunicationApi.getList(opportunityId) : []
}
const openCommunication = async (row: any) => {
  currentOpportunity.value = row
  resetCommunicationForm()
  await loadCommunications(row.id)
  communicationVisible.value = true
}
const createCommunication = async () => {
  if (!currentOpportunity.value?.id) return
  await BizOpportunityCommunicationApi.create({ ...communicationForm, opportunityId: currentOpportunity.value.id })
  message.success('新增成功')
  resetCommunicationForm()
  await loadCommunications(currentOpportunity.value.id)
}
const deleteCommunication = async (row: any) => {
  await message.confirm('确认删除该沟通记录吗？')
  await BizOpportunityCommunicationApi.delete(row.id)
  message.success('删除成功')
  await loadCommunications(currentOpportunity.value?.id)
}
const submitForm = async () => {
  submitLoading.value = true
  try {
    form.id ? await BizOpportunityApi.updateOpportunity(form) : await BizOpportunityApi.createOpportunity(form)
    message.success('保存成功')
    formVisible.value = false
    getList()
  } finally {
    submitLoading.value = false
  }
}
const handleDelete = async (row: any) => {
  await message.confirm(`确认删除商机「${row.name}」吗？`)
  await BizOpportunityApi.deleteOpportunity(row.id)
  message.success('删除成功')
  getList()
}
onMounted(() => getList())
</script>
