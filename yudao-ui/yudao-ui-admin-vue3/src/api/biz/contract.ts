import request from '@/config/axios'

export interface BizContractVO {
  id?: number
  contractNo?: string
  name: string
  customerName: string
  projectId?: number
  contractType?: string
  amount?: number
  signDate?: string
  startDate?: string
  endDate?: string
  dueSettleAmount?: number
  settledAmount?: number
  invoicedAmount?: number
  receivedAmount?: number
  status?: string
  attachmentUrl?: string
}

export const BizContractApi = {
  getContractPage: async (params: any) => {
    return await request.get({ url: '/biz/contract/page', params })
  },
  getContract: async (id: number) => {
    return await request.get({ url: '/biz/contract/get?id=' + id })
  },
  createContract: async (data: BizContractVO) => {
    return await request.post({ url: '/biz/contract/create', data })
  },
  updateContract: async (data: BizContractVO) => {
    return await request.put({ url: '/biz/contract/update', data })
  },
  deleteContract: async (id: number) => {
    return await request.delete({ url: '/biz/contract/delete?id=' + id })
  }
}
