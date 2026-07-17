package cn.iocoder.yudao.module.biz.dal.mysql;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.biz.dal.dataobject.ProjectProgressDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjectProgressMapper extends BaseMapperX<ProjectProgressDO> {

    default List<ProjectProgressDO> selectListByProjectId(Long projectId) {
        return selectList(new LambdaQueryWrapperX<ProjectProgressDO>()
                .eq(ProjectProgressDO::getProjectId, projectId)
                .orderByDesc(ProjectProgressDO::getProgressMonth)
                .orderByDesc(ProjectProgressDO::getId));
    }

}
