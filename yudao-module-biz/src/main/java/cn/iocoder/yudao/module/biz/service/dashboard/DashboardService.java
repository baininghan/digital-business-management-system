package cn.iocoder.yudao.module.biz.service.dashboard;

import cn.iocoder.yudao.module.biz.controller.admin.dashboard.DashboardSummaryRespVO;

public interface DashboardService {

    DashboardSummaryRespVO getSummary(String month);

}
