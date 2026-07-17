<template>
  <ContentWrap>
    <el-form ref="queryFormRef" :inline="true" :model="queryParams">
      <el-form-item label="项目名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入项目名称" clearable class="!w-240px" />
      </el-form-item>
      <el-form-item label="项目类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="全部" clearable class="!w-160px">
          <el-option v-for="item in projectTypes" :key="item" :label="item" :value="item" />
        </el-select>
      </el-form-item>
      <el-form-item label="负责人" prop="ownerName">
        <el-input v-model="queryParams.ownerName" placeholder="请输入负责人" clearable class="!w-160px" />
      </el-form-item>
      <el-form-item label="项目状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="全部" clearable class="!w-160px">
          <el-option v-for="item in projectStatuses" :key="item" :label="item" :value="item" />
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
      <div class="text-16px font-bold">项目台账</div>
      <div>
        <el-button><Icon icon="ep:download" class="mr-5px" />导出项目</el-button>
        <el-button type="primary" @click="openForm()"><Icon icon="ep:plus" class="mr-5px" />新增项目</el-button>
      </div>
    </div>
    <el-table v-loading="loading" :data="list" stripe>
      <el-table-column label="项目编号" prop="projectNo" min-width="140" />
      <el-table-column label="项目名称" min-width="220">
        <template #default="{ row }">
          <div class="font-bold">{{ row.name }}</div>
          <div class="text-12px color-#909399">{{ row.description || '-' }}</div>
        </template>
      </el-table-column>
      <el-table-column label="项目类型" prop="type" min-width="100" />
      <el-table-column label="负责人" prop="ownerName" min-width="90" />
      <el-table-column label="关联合同" min-width="100">
        <template #default="{ row }">
          <el-tag v-if="row.contractId" type="success">已关联</el-tag>
          <el-tag v-else type="info">无合同</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="产值计算类型" prop="outputCalcType" min-width="190" />
      <el-table-column label="完成比例" min-width="100">
        <template #default="{ row }">{{ row.completionRate || 0 }}%</template>
      </el-table-column>
      <el-table-column label="累计产值" prop="accumulatedOutput" min-width="120" />
      <el-table-column label="实际成本" prop="actualCost" min-width="120" />
      <el-table-column label="项目状态" prop="status" min-width="110" />
      <el-table-column label="操作" fixed="right" width="250">
        <template #default="{ row }">
          <el-button link type="primary" @click="openForm(row)">编辑</el-button>
          <el-button link type="primary" @click="openProgress(row)">进度更新</el-button>
          <el-button link type="primary" @click="openWorklog(row)">记录工时</el-button>
          <el-button link type="danger" @click="handleDelete(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <Pagination :total="total" v-model:page="queryParams.pageNo" v-model:limit="queryParams.pageSize" @pagination="getList" />
  </ContentWrap>

  <el-dialog v-model="formVisible" :title="form.id ? '编辑项目' : '新增项目'" width="760px">
    <el-form :model="form" label-width="120px">
      <el-row :gutter="16">
        <el-col :span="12"><el-form-item label="项目名称" required><el-input v-model="form.name" /></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="项目编号"><el-input v-model="form.projectNo" placeholder="不填则后端保留为空" /></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="项目类型" required><el-select v-model="form.type" class="w-100%"><el-option v-for="item in projectTypes" :key="item" :label="item" :value="item" /></el-select></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="产值计算类型"><el-select v-model="form.outputCalcType" class="w-100%"><el-option v-for="item in outputCalcTypes" :key="item" :label="item" :value="item" /></el-select></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="项目负责人" required><el-input v-model="form.ownerName" /></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="项目成员"><el-input v-model="form.members" /></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="项目状态"><el-select v-model="form.status" class="w-100%"><el-option v-for="item in projectStatuses" :key="item" :label="item" :value="item" /></el-select></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="关联合同ID"><el-input-number v-model="form.contractId" class="w-100%" :min="1" /></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="计划开始"><el-date-picker v-model="form.planStartDate" value-format="YYYY-MM-DD" type="date" class="w-100%" /></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="计划结束"><el-date-picker v-model="form.planEndDate" value-format="YYYY-MM-DD" type="date" class="w-100%" /></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="当前完成比例"><el-input-number v-model="form.completionRate" class="w-100%" :min="0" :max="100" /></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="累计产值"><el-input-number v-model="form.accumulatedOutput" class="w-100%" :min="0" /></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="实际成本"><el-input-number v-model="form.actualCost" class="w-100%" :min="0" /></el-form-item></el-col>
        <el-col :span="24"><el-form-item label="项目描述"><el-input v-model="form.description" type="textarea" :rows="3" /></el-form-item></el-col>
      </el-row>
    </el-form>
    <template #footer>
      <el-button @click="formVisible = false">取消</el-button>
      <el-button type="primary" :loading="submitLoading" @click="submitForm">保存</el-button>
    </template>
  </el-dialog>

  <el-dialog v-model="progressVisible" title="月度完成比例" width="720px">
    <div class="mb-12px color-#606266">未结束项目每个月维护当月完成比例，用于合同里程碑产值确认。</div>
    <el-form :inline="true" :model="progressForm" class="mb-12px">
      <el-form-item label="月份"><el-date-picker v-model="progressForm.progressMonth" type="month" value-format="YYYY-MM" class="!w-140px" /></el-form-item>
      <el-form-item label="完成比例"><el-input-number v-model="progressForm.completionRate" :min="0" :max="100" class="!w-120px" /></el-form-item>
      <el-form-item label="说明"><el-input v-model="progressForm.remark" placeholder="请输入确认说明" class="!w-220px" /></el-form-item>
      <el-form-item><el-button type="primary" @click="createProgress">新增</el-button></el-form-item>
    </el-form>
    <el-table :data="progressRows" border>
      <el-table-column label="月份" prop="progressMonth" width="140" />
      <el-table-column label="当月完成比例" width="140"><template #default="{ row }">{{ row.completionRate || 0 }}%</template></el-table-column>
      <el-table-column label="确认说明" prop="remark" />
      <el-table-column label="操作" width="90"><template #default="{ row }"><el-button link type="danger" @click="deleteProgress(row.id)">删除</el-button></template></el-table-column>
    </el-table>
  </el-dialog>

  <el-dialog v-model="worklogVisible" title="记录工时" width="760px">
    <div class="mb-12px color-#606266">成本计算说明：内部研发产值 = 工时 × 标准人工单价；项目人工成本按人员月综合成本和项目投入工时占比分摊。</div>
    <el-form :inline="true" :model="worklogForm" class="mb-12px">
      <el-form-item label="月份"><el-date-picker v-model="worklogForm.workMonth" type="month" value-format="YYYY-MM" class="!w-140px" /></el-form-item>
      <el-form-item label="成员"><el-input v-model="worklogForm.memberName" class="!w-160px" /></el-form-item>
      <el-form-item label="工时"><el-input-number v-model="worklogForm.hours" :min="0" class="!w-120px" /></el-form-item>
      <el-form-item><el-button type="primary" @click="createWorklog">新增</el-button></el-form-item>
    </el-form>
    <el-table :data="worklogRows" border>
      <el-table-column label="月份" prop="workMonth" width="130" />
      <el-table-column label="项目成员名称" prop="memberName" />
      <el-table-column label="本月投入工时" prop="hours" width="140" />
      <el-table-column label="操作" width="90"><template #default="{ row }"><el-button link type="danger" @click="deleteWorklog(row.id)">删除</el-button></template></el-table-column>
    </el-table>
  </el-dialog>
</template>

<script setup lang="ts">
import { BizProjectApi } from '@/api/biz/project'
import { BizProjectProgressApi, BizProjectWorklogApi } from '@/api/biz/detail'

defineOptions({ name: 'BizProject' })

const message = useMessage()
const loading = ref(false)
const submitLoading = ref(false)
const list = ref<any[]>([])
const total = ref(0)
const queryFormRef = ref()
const queryParams = reactive({ pageNo: 1, pageSize: 10, name: undefined, type: undefined, ownerName: undefined, status: undefined })
const projectTypes = ['客户交付', '内部自研', '运维', '其他']
const projectStatuses = ['项目立项', '需求评审', '研发', '交付试运行', '运维', '暂停', '已结束']
const outputCalcTypes = ['合同金额 × 里程碑完成比例', '工时 × 标准人工单价', '年度服务费按月分摊']
const formVisible = ref(false)
const progressVisible = ref(false)
const worklogVisible = ref(false)
const form = reactive<any>({})
const currentProject = ref<any>()
const progressRows = ref<any[]>([])
const worklogRows = ref<any[]>([])
const progressForm = reactive<any>({ progressMonth: '2026-07', completionRate: 0, remark: '' })
const worklogForm = reactive<any>({ workMonth: '2026-07', memberName: '', hours: 0 })

const getList = async () => {
  loading.value = true
  try {
    const data = await BizProjectApi.getProjectPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}
const handleQuery = () => { queryParams.pageNo = 1; getList() }
const resetQuery = () => { queryFormRef.value?.resetFields(); handleQuery() }
const openForm = (row?: any) => {
  Object.assign(form, row ? { ...row } : { name: '', type: '客户交付', ownerName: '', status: '项目立项', completionRate: 0, accumulatedOutput: 0, actualCost: 0 })
  formVisible.value = true
}
const submitForm = async () => {
  submitLoading.value = true
  try {
    form.id ? await BizProjectApi.updateProject(form) : await BizProjectApi.createProject(form)
    message.success('保存成功')
    formVisible.value = false
    getList()
  } finally {
    submitLoading.value = false
  }
}
const handleDelete = async (row: any) => {
  await message.confirm(`确认删除项目「${row.name}」吗？`)
  await BizProjectApi.deleteProject(row.id)
  message.success('删除成功')
  getList()
}
const openProgress = async (row: any) => {
  currentProject.value = row
  progressVisible.value = true
  progressRows.value = await BizProjectProgressApi.getList(row.id)
}
const createProgress = async () => {
  await BizProjectProgressApi.create({ ...progressForm, projectId: currentProject.value.id })
  message.success('新增成功')
  progressRows.value = await BizProjectProgressApi.getList(currentProject.value.id)
}
const deleteProgress = async (id: number) => {
  await BizProjectProgressApi.delete(id)
  message.success('删除成功')
  progressRows.value = await BizProjectProgressApi.getList(currentProject.value.id)
}
const openWorklog = async (row: any) => {
  currentProject.value = row
  worklogVisible.value = true
  worklogRows.value = await BizProjectWorklogApi.getList(row.id)
}
const createWorklog = async () => {
  await BizProjectWorklogApi.create({ ...worklogForm, projectId: currentProject.value.id })
  message.success('新增成功')
  worklogRows.value = await BizProjectWorklogApi.getList(currentProject.value.id)
}
const deleteWorklog = async (id: number) => {
  await BizProjectWorklogApi.delete(id)
  message.success('删除成功')
  worklogRows.value = await BizProjectWorklogApi.getList(currentProject.value.id)
}
onMounted(() => getList())
</script>
