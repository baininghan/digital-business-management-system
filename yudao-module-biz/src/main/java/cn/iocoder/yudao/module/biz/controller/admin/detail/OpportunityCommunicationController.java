package cn.iocoder.yudao.module.biz.controller.admin.detail;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.biz.dal.dataobject.OpportunityCommunicationDO;
import cn.iocoder.yudao.module.biz.dal.mysql.OpportunityCommunicationMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 商机沟通记录")
@RestController
@RequestMapping("/biz/opportunity-communication")
@Validated
public class OpportunityCommunicationController {

    @Resource
    private OpportunityCommunicationMapper opportunityCommunicationMapper;

    @PostMapping("/create")
    @PreAuthorize("@ss.hasPermission('biz:opportunity:update')")
    public CommonResult<Long> create(@RequestBody OpportunityCommunicationDO data) {
        opportunityCommunicationMapper.insert(data);
        return success(data.getId());
    }

    @PutMapping("/update")
    @PreAuthorize("@ss.hasPermission('biz:opportunity:update')")
    public CommonResult<Boolean> update(@RequestBody OpportunityCommunicationDO data) {
        opportunityCommunicationMapper.updateById(data);
        return success(true);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("@ss.hasPermission('biz:opportunity:update')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        opportunityCommunicationMapper.deleteById(id);
        return success(true);
    }

    @GetMapping("/list")
    @PreAuthorize("@ss.hasPermission('biz:opportunity:query')")
    public CommonResult<List<OpportunityCommunicationDO>> list(@RequestParam("opportunityId") Long opportunityId) {
        return success(opportunityCommunicationMapper.selectListByOpportunityId(opportunityId));
    }

}
