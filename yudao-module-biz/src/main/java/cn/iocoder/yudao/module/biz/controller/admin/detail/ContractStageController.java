package cn.iocoder.yudao.module.biz.controller.admin.detail;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.biz.dal.dataobject.ContractStageDO;
import cn.iocoder.yudao.module.biz.dal.mysql.ContractStageMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 合同付款阶段")
@RestController
@RequestMapping("/biz/contract-stage")
@Validated
public class ContractStageController {

    @Resource
    private ContractStageMapper contractStageMapper;

    @PostMapping("/create")
    @PreAuthorize("@ss.hasPermission('biz:contract:update')")
    public CommonResult<Long> create(@RequestBody ContractStageDO data) {
        contractStageMapper.insert(data);
        return success(data.getId());
    }

    @PutMapping("/update")
    @PreAuthorize("@ss.hasPermission('biz:contract:update')")
    public CommonResult<Boolean> update(@RequestBody ContractStageDO data) {
        contractStageMapper.updateById(data);
        return success(true);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("@ss.hasPermission('biz:contract:update')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        contractStageMapper.deleteById(id);
        return success(true);
    }

    @GetMapping("/list")
    @PreAuthorize("@ss.hasPermission('biz:contract:query')")
    public CommonResult<List<ContractStageDO>> list(@RequestParam("contractId") Long contractId) {
        return success(contractStageMapper.selectListByContractId(contractId));
    }

}
