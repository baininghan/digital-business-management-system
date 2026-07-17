package cn.iocoder.yudao.module.biz.dal.mysql;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.biz.controller.admin.project.vo.ProjectPageReqVO;
import cn.iocoder.yudao.module.biz.dal.dataobject.ProjectDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProjectMapper extends BaseMapperX<ProjectDO> {

    default PageResult<ProjectDO> selectPage(ProjectPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ProjectDO>()
                .likeIfPresent(ProjectDO::getName, reqVO.getName())
                .eqIfPresent(ProjectDO::getType, reqVO.getType())
                .eqIfPresent(ProjectDO::getOwnerName, reqVO.getOwnerName())
                .eqIfPresent(ProjectDO::getStatus, reqVO.getStatus())
                .orderByDesc(ProjectDO::getId));
    }

}
