import request from '@/config/axios'

export interface BizOpportunityVO {
  id?: number
  opportunityNo?: string
  name: string
  customerName?: string
  estimatedAmount?: number
  stage?: string
  expectedSignMonth?: string
  ownerName?: string
  convertedToBid?: boolean
  bidId?: number
  convertedToContract?: boolean
  contractId?: number
  lastFollowDate?: string
  followRecord?: string
}

export const BizOpportunityApi = {
  getOpportunityPage: async (params: any) => {
    return await request.get({ url: '/biz/opportunity/page', params })
  },
  getOpportunity: async (id: number) => {
    return await request.get({ url: '/biz/opportunity/get?id=' + id })
  },
  createOpportunity: async (data: BizOpportunityVO) => {
    return await request.post({ url: '/biz/opportunity/create', data })
  },
  updateOpportunity: async (data: BizOpportunityVO) => {
    return await request.put({ url: '/biz/opportunity/update', data })
  },
  deleteOpportunity: async (id: number) => {
    return await request.delete({ url: '/biz/opportunity/delete?id=' + id })
  }
}
