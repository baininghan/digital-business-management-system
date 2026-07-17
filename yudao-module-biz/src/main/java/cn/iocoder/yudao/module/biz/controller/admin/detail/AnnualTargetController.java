package cn.iocoder.yudao.module.biz.controller.admin.detail;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.biz.dal.dataobject.AnnualTargetDO;
import cn.iocoder.yudao.module.biz.dal.mysql.AnnualTargetMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 年度指标")
@RestController
@RequestMapping("/biz/annual-target")
@Validated
public class AnnualTargetController {

    @Resource
    private AnnualTargetMapper annualTargetMapper;

    @PostMapping("/create")
    @PreAuthorize("@ss.hasPermission('biz:annual-target:query')")
    public CommonResult<Long> create(@RequestBody AnnualTargetDO data) {
        annualTargetMapper.insert(data);
        return success(data.getId());
    }

    @PutMapping("/update")
    @PreAuthorize("@ss.hasPermission('biz:annual-target:query')")
    public CommonResult<Boolean> update(@RequestBody AnnualTargetDO data) {
        annualTargetMapper.updateById(data);
        return success(true);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("@ss.hasPermission('biz:annual-target:query')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        annualTargetMapper.deleteById(id);
        return success(true);
    }

    @GetMapping("/list")
    @PreAuthorize("@ss.hasPermission('biz:annual-target:query')")
    public CommonResult<List<AnnualTargetDO>> list() {
        return success(annualTargetMapper.selectListOrderByYear());
    }

}
