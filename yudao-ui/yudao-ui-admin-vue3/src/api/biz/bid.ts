import request from '@/config/axios'

export interface BizBidVO {
  id?: number
  bidNo?: string
  name: string
  customerName?: string
  bidType?: string
  amount?: number
  submitDate?: string
  status?: string
  ownerName?: string
  opportunityId?: number
  winStatus?: string
  convertedToContract?: boolean
  contractId?: number
  expectedResultDate?: string
  remark?: string
}

export const BizBidApi = {
  getBidPage: async (params: any) => {
    return await request.get({ url: '/biz/bid/page', params })
  },
  getBid: async (id: number) => {
    return await request.get({ url: '/biz/bid/get?id=' + id })
  },
  createBid: async (data: BizBidVO) => {
    return await request.post({ url: '/biz/bid/create', data })
  },
  updateBid: async (data: BizBidVO) => {
    return await request.put({ url: '/biz/bid/update', data })
  },
  deleteBid: async (id: number) => {
    return await request.delete({ url: '/biz/bid/delete?id=' + id })
  }
}
