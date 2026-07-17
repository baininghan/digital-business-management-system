package cn.iocoder.yudao.module.biz.dal.mysql;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.biz.controller.admin.opportunity.vo.OpportunityPageReqVO;
import cn.iocoder.yudao.module.biz.dal.dataobject.OpportunityDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OpportunityMapper extends BaseMapperX<OpportunityDO> {

    default PageResult<OpportunityDO> selectPage(OpportunityPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OpportunityDO>()
                .likeIfPresent(OpportunityDO::getName, reqVO.getName())
                .likeIfPresent(OpportunityDO::getCustomerName, reqVO.getCustomerName())
                .eqIfPresent(OpportunityDO::getStage, reqVO.getStage())
                .eqIfPresent(OpportunityDO::getOwnerName, reqVO.getOwnerName())
                .orderByDesc(OpportunityDO::getId));
    }

}
