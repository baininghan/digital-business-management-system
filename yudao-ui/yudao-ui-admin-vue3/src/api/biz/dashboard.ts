import request from '@/config/axios'

export const BizDashboardApi = {
  getSummary: async (params: { month?: string }) => {
    return await request.get({ url: '/biz/dashboard/summary', params })
  }
}
