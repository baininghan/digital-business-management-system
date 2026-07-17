package cn.iocoder.yudao.module.biz.service.opportunity;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.biz.controller.admin.opportunity.vo.OpportunityPageReqVO;
import cn.iocoder.yudao.module.biz.controller.admin.opportunity.vo.OpportunitySaveReqVO;
import cn.iocoder.yudao.module.biz.dal.dataobject.OpportunityDO;

public interface OpportunityService {

    Long createOpportunity(OpportunitySaveReqVO createReqVO);

    void updateOpportunity(OpportunitySaveReqVO updateReqVO);

    void deleteOpportunity(Long id);

    OpportunityDO getOpportunity(Long id);

    PageResult<OpportunityDO> getOpportunityPage(OpportunityPageReqVO pageReqVO);

}
