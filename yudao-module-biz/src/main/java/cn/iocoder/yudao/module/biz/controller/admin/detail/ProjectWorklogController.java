package cn.iocoder.yudao.module.biz.controller.admin.detail;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.biz.dal.dataobject.ProjectWorklogDO;
import cn.iocoder.yudao.module.biz.dal.mysql.ProjectWorklogMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 项目工时")
@RestController
@RequestMapping("/biz/project-worklog")
@Validated
public class ProjectWorklogController {

    @Resource
    private ProjectWorklogMapper projectWorklogMapper;

    @PostMapping("/create")
    @PreAuthorize("@ss.hasPermission('biz:project:update')")
    public CommonResult<Long> create(@RequestBody ProjectWorklogDO data) {
        projectWorklogMapper.insert(data);
        return success(data.getId());
    }

    @PutMapping("/update")
    @PreAuthorize("@ss.hasPermission('biz:project:update')")
    public CommonResult<Boolean> update(@RequestBody ProjectWorklogDO data) {
        projectWorklogMapper.updateById(data);
        return success(true);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("@ss.hasPermission('biz:project:update')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        projectWorklogMapper.deleteById(id);
        return success(true);
    }

    @GetMapping("/list")
    @PreAuthorize("@ss.hasPermission('biz:project:query')")
    public CommonResult<List<ProjectWorklogDO>> list(@RequestParam("projectId") Long projectId) {
        return success(projectWorklogMapper.selectListByProjectId(projectId));
    }

}
