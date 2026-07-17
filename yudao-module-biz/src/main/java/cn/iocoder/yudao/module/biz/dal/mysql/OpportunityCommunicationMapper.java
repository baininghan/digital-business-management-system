package cn.iocoder.yudao.module.biz.dal.mysql;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.biz.dal.dataobject.OpportunityCommunicationDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OpportunityCommunicationMapper extends BaseMapperX<OpportunityCommunicationDO> {

    default List<OpportunityCommunicationDO> selectListByOpportunityId(Long opportunityId) {
        return selectList(new LambdaQueryWrapperX<OpportunityCommunicationDO>()
                .eq(OpportunityCommunicationDO::getOpportunityId, opportunityId)
                .orderByDesc(OpportunityCommunicationDO::getCommunicationDate)
                .orderByDesc(OpportunityCommunicationDO::getId));
    }

}
