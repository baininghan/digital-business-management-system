import request from '@/config/axios'

export const BizProjectProgressApi = {
  getList: async (projectId: number) => {
    return await request.get({ url: '/biz/project-progress/list', params: { projectId } })
  },
  create: async (data: any) => {
    return await request.post({ url: '/biz/project-progress/create', data })
  },
  delete: async (id: number) => {
    return await request.delete({ url: '/biz/project-progress/delete?id=' + id })
  }
}

export const BizProjectWorklogApi = {
  getList: async (projectId: number) => {
    return await request.get({ url: '/biz/project-worklog/list', params: { projectId } })
  },
  create: async (data: any) => {
    return await request.post({ url: '/biz/project-worklog/create', data })
  },
  delete: async (id: number) => {
    return await request.delete({ url: '/biz/project-worklog/delete?id=' + id })
  }
}

export const BizContractStageApi = {
  getList: async (contractId: number) => {
    return await request.get({ url: '/biz/contract-stage/list', params: { contractId } })
  },
  create: async (data: any) => {
    return await request.post({ url: '/biz/contract-stage/create', data })
  },
  delete: async (id: number) => {
    return await request.delete({ url: '/biz/contract-stage/delete?id=' + id })
  }
}

export const BizOpportunityCommunicationApi = {
  getList: async (opportunityId: number) => {
    return await request.get({ url: '/biz/opportunity-communication/list', params: { opportunityId } })
  },
  create: async (data: any) => {
    return await request.post({ url: '/biz/opportunity-communication/create', data })
  },
  delete: async (id: number) => {
    return await request.delete({ url: '/biz/opportunity-communication/delete?id=' + id })
  }
}

export const BizAnnualTargetApi = {
  getList: async () => {
    return await request.get({ url: '/biz/annual-target/list' })
  },
  create: async (data: any) => {
    return await request.post({ url: '/biz/annual-target/create', data })
  },
  update: async (data: any) => {
    return await request.put({ url: '/biz/annual-target/update', data })
  },
  delete: async (id: number) => {
    return await request.delete({ url: '/biz/annual-target/delete?id=' + id })
  }
}
