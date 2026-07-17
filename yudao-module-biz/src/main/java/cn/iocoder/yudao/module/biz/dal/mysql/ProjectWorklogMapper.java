package cn.iocoder.yudao.module.biz.dal.mysql;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.biz.dal.dataobject.ProjectWorklogDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjectWorklogMapper extends BaseMapperX<ProjectWorklogDO> {

    default List<ProjectWorklogDO> selectListByProjectId(Long projectId) {
        return selectList(new LambdaQueryWrapperX<ProjectWorklogDO>()
                .eq(ProjectWorklogDO::getProjectId, projectId)
                .orderByDesc(ProjectWorklogDO::getWorkMonth)
                .orderByDesc(ProjectWorklogDO::getId));
    }

}
