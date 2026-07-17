package cn.iocoder.yudao.module.biz.controller.admin.bid;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.biz.controller.admin.bid.vo.BidPageReqVO;
import cn.iocoder.yudao.module.biz.controller.admin.bid.vo.BidRespVO;
import cn.iocoder.yudao.module.biz.controller.admin.bid.vo.BidSaveReqVO;
import cn.iocoder.yudao.module.biz.dal.dataobject.BidDO;
import cn.iocoder.yudao.module.biz.service.bid.BidService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 投标报价")
@RestController
@RequestMapping("/biz/bid")
@Validated
public class BidController {

    @Resource
    private BidService bidService;

    @PostMapping("/create")
    @Operation(summary = "创建投标报价")
    @PreAuthorize("@ss.hasPermission('biz:bid:create')")
    public CommonResult<Long> createBid(@Valid @RequestBody BidSaveReqVO createReqVO) {
        return success(bidService.createBid(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新投标报价")
    @PreAuthorize("@ss.hasPermission('biz:bid:update')")
    public CommonResult<Boolean> updateBid(@Valid @RequestBody BidSaveReqVO updateReqVO) {
        bidService.updateBid(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除投标报价")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('biz:bid:delete')")
    public CommonResult<Boolean> deleteBid(@RequestParam("id") Long id) {
        bidService.deleteBid(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得投标报价")
    @PreAuthorize("@ss.hasPermission('biz:bid:query')")
    public CommonResult<BidRespVO> getBid(@RequestParam("id") Long id) {
        BidDO bid = bidService.getBid(id);
        return success(BeanUtils.toBean(bid, BidRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得投标报价分页")
    @PreAuthorize("@ss.hasPermission('biz:bid:query')")
    public CommonResult<PageResult<BidRespVO>> getBidPage(@Valid BidPageReqVO pageReqVO) {
        PageResult<BidDO> pageResult = bidService.getBidPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, BidRespVO.class));
    }

}
