package cn.iocoder.yudao.module.biz.service.project;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.biz.controller.admin.project.vo.ProjectPageReqVO;
import cn.iocoder.yudao.module.biz.controller.admin.project.vo.ProjectSaveReqVO;
import cn.iocoder.yudao.module.biz.dal.dataobject.ProjectDO;

public interface ProjectService {

    Long createProject(ProjectSaveReqVO createReqVO);

    void updateProject(ProjectSaveReqVO updateReqVO);

    void deleteProject(Long id);

    ProjectDO getProject(Long id);

    PageResult<ProjectDO> getProjectPage(ProjectPageReqVO pageReqVO);

}
