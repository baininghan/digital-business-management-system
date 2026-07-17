package cn.iocoder.yudao.module.biz.controller.admin.detail;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.biz.dal.dataobject.ProjectProgressDO;
import cn.iocoder.yudao.module.biz.dal.mysql.ProjectProgressMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 项目进度")
@RestController
@RequestMapping("/biz/project-progress")
@Validated
public class ProjectProgressController {

    @Resource
    private ProjectProgressMapper projectProgressMapper;

    @PostMapping("/create")
    @PreAuthorize("@ss.hasPermission('biz:project:update')")
    public CommonResult<Long> create(@RequestBody ProjectProgressDO data) {
        projectProgressMapper.insert(data);
        return success(data.getId());
    }

    @PutMapping("/update")
    @PreAuthorize("@ss.hasPermission('biz:project:update')")
    public CommonResult<Boolean> update(@RequestBody ProjectProgressDO data) {
        projectProgressMapper.updateById(data);
        return success(true);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("@ss.hasPermission('biz:project:update')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        projectProgressMapper.deleteById(id);
        return success(true);
    }

    @GetMapping("/list")
    @PreAuthorize("@ss.hasPermission('biz:project:query')")
    public CommonResult<List<ProjectProgressDO>> list(@RequestParam("projectId") Long projectId) {
        return success(projectProgressMapper.selectListByProjectId(projectId));
    }

}
