import request from '@/config/axios'

export interface BizProjectVO {
  id?: number
  projectNo?: string
  name: string
  type: string
  ownerName: string
  members?: string
  contractId?: number
  outputCalcType?: string
  planStartDate?: string
  planEndDate?: string
  actualStartDate?: string
  actualEndDate?: string
  status?: string
  completionRate?: number
  accumulatedOutput?: number
  actualCost?: number
  description?: string
}

export const BizProjectApi = {
  getProjectPage: async (params: any) => {
    return await request.get({ url: '/biz/project/page', params })
  },
  getProject: async (id: number) => {
    return await request.get({ url: '/biz/project/get?id=' + id })
  },
  createProject: async (data: BizProjectVO) => {
    return await request.post({ url: '/biz/project/create', data })
  },
  updateProject: async (data: BizProjectVO) => {
    return await request.put({ url: '/biz/project/update', data })
  },
  deleteProject: async (id: number) => {
    return await request.delete({ url: '/biz/project/delete?id=' + id })
  }
}
