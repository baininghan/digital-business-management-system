package cn.iocoder.yudao.module.biz.controller.admin.opportunity;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.biz.controller.admin.opportunity.vo.OpportunityPageReqVO;
import cn.iocoder.yudao.module.biz.controller.admin.opportunity.vo.OpportunityRespVO;
import cn.iocoder.yudao.module.biz.controller.admin.opportunity.vo.OpportunitySaveReqVO;
import cn.iocoder.yudao.module.biz.dal.dataobject.OpportunityDO;
import cn.iocoder.yudao.module.biz.service.opportunity.OpportunityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 商机")
@RestController
@RequestMapping("/biz/opportunity")
@Validated
public class OpportunityController {

    @Resource
    private OpportunityService opportunityService;

    @PostMapping("/create")
    @Operation(summary = "创建商机")
    @PreAuthorize("@ss.hasPermission('biz:opportunity:create')")
    public CommonResult<Long> createOpportunity(@Valid @RequestBody OpportunitySaveReqVO createReqVO) {
        return success(opportunityService.createOpportunity(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新商机")
    @PreAuthorize("@ss.hasPermission('biz:opportunity:update')")
    public CommonResult<Boolean> updateOpportunity(@Valid @RequestBody OpportunitySaveReqVO updateReqVO) {
        opportunityService.updateOpportunity(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除商机")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('biz:opportunity:delete')")
    public CommonResult<Boolean> deleteOpportunity(@RequestParam("id") Long id) {
        opportunityService.deleteOpportunity(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得商机")
    @PreAuthorize("@ss.hasPermission('biz:opportunity:query')")
    public CommonResult<OpportunityRespVO> getOpportunity(@RequestParam("id") Long id) {
        OpportunityDO opportunity = opportunityService.getOpportunity(id);
        return success(BeanUtils.toBean(opportunity, OpportunityRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得商机分页")
    @PreAuthorize("@ss.hasPermission('biz:opportunity:query')")
    public CommonResult<PageResult<OpportunityRespVO>> getOpportunityPage(@Valid OpportunityPageReqVO pageReqVO) {
        PageResult<OpportunityDO> pageResult = opportunityService.getOpportunityPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OpportunityRespVO.class));
    }

}
