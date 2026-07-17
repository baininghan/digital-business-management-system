package cn.iocoder.yudao.module.biz.controller.admin.dashboard;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.biz.service.dashboard.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 经营看板")
@RestController
@RequestMapping("/biz/dashboard")
@Validated
public class DashboardController {

    @Resource
    private DashboardService dashboardService;

    @GetMapping("/summary")
    @Operation(summary = "获得经营看板汇总")
    @PreAuthorize("@ss.hasPermission('biz:dashboard:query')")
    public CommonResult<DashboardSummaryRespVO> getSummary(@RequestParam(value = "month", required = false) String month) {
        return success(dashboardService.getSummary(month));
    }

}
