package cn.iocoder.yudao.module.biz.service.opportunity;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.biz.controller.admin.opportunity.vo.OpportunityPageReqVO;
import cn.iocoder.yudao.module.biz.controller.admin.opportunity.vo.OpportunitySaveReqVO;
import cn.iocoder.yudao.module.biz.dal.dataobject.OpportunityDO;
import cn.iocoder.yudao.module.biz.dal.mysql.OpportunityMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.biz.enums.ErrorCodeConstants.OPPORTUNITY_NOT_EXISTS;

@Service
@Validated
public class OpportunityServiceImpl implements OpportunityService {

    @Resource
    private OpportunityMapper opportunityMapper;

    @Override
    public Long createOpportunity(OpportunitySaveReqVO createReqVO) {
        OpportunityDO opportunity = BeanUtils.toBean(createReqVO, OpportunityDO.class);
        opportunityMapper.insert(opportunity);
        return opportunity.getId();
    }

    @Override
    public void updateOpportunity(OpportunitySaveReqVO updateReqVO) {
        validateOpportunityExists(updateReqVO.getId());
        opportunityMapper.updateById(BeanUtils.toBean(updateReqVO, OpportunityDO.class));
    }

    @Override
    public void deleteOpportunity(Long id) {
        validateOpportunityExists(id);
        opportunityMapper.deleteById(id);
    }

    private void validateOpportunityExists(Long id) {
        if (id == null || opportunityMapper.selectById(id) == null) {
            throw exception(OPPORTUNITY_NOT_EXISTS);
        }
    }

    @Override
    public OpportunityDO getOpportunity(Long id) {
        return opportunityMapper.selectById(id);
    }

    @Override
    public PageResult<OpportunityDO> getOpportunityPage(OpportunityPageReqVO pageReqVO) {
        return opportunityMapper.selectPage(pageReqVO);
    }

}
