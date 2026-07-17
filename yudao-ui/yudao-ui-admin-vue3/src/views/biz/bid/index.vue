<template>
  <ContentWrap>
    <el-form ref="queryFormRef" :inline="true" :model="queryParams">
      <el-form-item label="报价名称" prop="name"><el-input v-model="queryParams.name" placeholder="请输入项目或报价名称" clearable class="!w-240px" /></el-form-item>
      <el-form-item label="客户名称" prop="customerName"><el-input v-model="queryParams.customerName" placeholder="请输入客户名称" clearable class="!w-220px" /></el-form-item>
      <el-form-item label="报价类型" prop="bidType">
        <el-select v-model="queryParams.bidType" placeholder="全部" clearable class="!w-160px">
          <el-option v-for="item in bidTypes" :key="item" :label="item" :value="item" />
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
      <div class="text-16px font-bold">投标报价列表</div>
      <div>
        <el-button><Icon icon="ep:download" class="mr-5px" />导出报价</el-button>
        <el-button type="primary" @click="openForm()"><Icon icon="ep:plus" class="mr-5px" />新增报价</el-button>
      </div>
    </div>
    <el-table v-loading="loading" :data="list" stripe>
      <el-table-column label="报价编号" prop="bidNo" min-width="140" />
      <el-table-column label="报价名称" min-width="220">
        <template #default="{ row }">
          <div class="font-bold">{{ row.name }}</div>
          <div class="text-12px color-#909399">{{ row.customerName || '-' }}</div>
        </template>
      </el-table-column>
      <el-table-column label="报价类型" prop="bidType" min-width="120" />
      <el-table-column label="报价金额" prop="amount" min-width="120" />
      <el-table-column label="提交日期" prop="submitDate" min-width="120" />
      <el-table-column label="报价状态" prop="status" min-width="120" />
      <el-table-column label="中标/成交" prop="winStatus" min-width="120" />
      <el-table-column label="关联商机" prop="opportunityId" min-width="100" />
      <el-table-column label="是否转合同" min-width="120"><template #default="{ row }"><el-tag :type="row.convertedToContract ? 'success' : 'info'">{{ row.convertedToContract ? '是' : '否' }}</el-tag></template></el-table-column>
      <el-table-column label="关联合同" prop="contractId" min-width="100" />
      <el-table-column label="负责人" prop="ownerName" min-width="100" />
      <el-table-column label="操作" fixed="right" width="150">
        <template #default="{ row }">
          <el-button link type="primary" @click="openForm(row)">编辑</el-button>
          <el-button link type="danger" @click="handleDelete(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <Pagination :total="total" v-model:page="queryParams.pageNo" v-model:limit="queryParams.pageSize" @pagination="getList" />
  </ContentWrap>

  <el-dialog v-model="formVisible" :title="form.id ? '编辑投标报价' : '新增投标报价'" width="720px">
    <el-form :model="form" label-width="120px">
      <el-row :gutter="16">
        <el-col :span="12"><el-form-item label="报价名称" required><el-input v-model="form.name" /></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="报价编号"><el-input v-model="form.bidNo" /></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="客户名称"><el-input v-model="form.customerName" /></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="关联商机ID"><el-input-number v-model="form.opportunityId" class="w-100%" :min="1" /></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="报价类型"><el-select v-model="form.bidType" class="w-100%"><el-option v-for="item in bidTypes" :key="item" :label="item" :value="item" /></el-select></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="报价金额"><el-input-number v-model="form.amount" class="w-100%" :min="0" /></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="提交日期"><el-date-picker v-model="form.submitDate" value-format="YYYY-MM-DD" type="date" class="w-100%" /></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="报价状态"><el-select v-model="form.status" class="w-100%"><el-option v-for="item in statuses" :key="item" :label="item" :value="item" /></el-select></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="中标/成交"><el-select v-model="form.winStatus" class="w-100%"><el-option label="已中标/成交" value="WON" /><el-option label="待确认" value="PENDING" /><el-option label="未中标/丢单" value="LOST" /></el-select></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="是否转合同"><el-switch v-model="form.convertedToContract" /></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="关联合同ID"><el-input-number v-model="form.contractId" class="w-100%" :min="1" /></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="负责人"><el-input v-model="form.ownerName" /></el-form-item></el-col>
        <el-col :span="24"><el-form-item label="备注"><el-input v-model="form.remark" type="textarea" :rows="3" /></el-form-item></el-col>
      </el-row>
    </el-form>
    <template #footer><el-button @click="formVisible = false">取消</el-button><el-button type="primary" :loading="submitLoading" @click="submitForm">保存</el-button></template>
  </el-dialog>
</template>

<script setup lang="ts">
import { BizBidApi } from '@/api/biz/bid'

defineOptions({ name: 'BizBid' })

const message = useMessage()
const loading = ref(false)
const submitLoading = ref(false)
const list = ref<any[]>([])
const total = ref(0)
const queryFormRef = ref()
const queryParams = reactive({ pageNo: 1, pageSize: 10, name: undefined, customerName: undefined, bidType: undefined })
const bidTypes = ['公开投标', '邀请投标', '商务报价', '续费报价']
const statuses = ['编制中', '已提交', '已撤回', '已完成']
const formVisible = ref(false)
const form = reactive<any>({})
const getList = async () => {
  loading.value = true
  try {
    const data = await BizBidApi.getBidPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}
const handleQuery = () => { queryParams.pageNo = 1; getList() }
const resetQuery = () => { queryFormRef.value?.resetFields(); handleQuery() }
const openForm = (row?: any) => {
  Object.assign(form, row ? { ...row } : { name: '', bidType: '公开投标', status: '编制中', winStatus: 'PENDING', amount: 0, convertedToContract: false })
  formVisible.value = true
}
const submitForm = async () => {
  submitLoading.value = true
  try {
    form.id ? await BizBidApi.updateBid(form) : await BizBidApi.createBid(form)
    message.success('保存成功')
    formVisible.value = false
    getList()
  } finally {
    submitLoading.value = false
  }
}
const handleDelete = async (row: any) => {
  await message.confirm(`确认删除报价「${row.name}」吗？`)
  await BizBidApi.deleteBid(row.id)
  message.success('删除成功')
  getList()
}
onMounted(() => getList())
</script>
