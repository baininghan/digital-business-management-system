package cn.iocoder.yudao.module.biz.service.bid;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.biz.controller.admin.bid.vo.BidPageReqVO;
import cn.iocoder.yudao.module.biz.controller.admin.bid.vo.BidSaveReqVO;
import cn.iocoder.yudao.module.biz.dal.dataobject.BidDO;
import cn.iocoder.yudao.module.biz.dal.mysql.BidMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.biz.enums.ErrorCodeConstants.BID_NOT_EXISTS;

@Service
@Validated
public class BidServiceImpl implements BidService {

    @Resource
    private BidMapper bidMapper;

    @Override
    public Long createBid(BidSaveReqVO createReqVO) {
        BidDO bid = BeanUtils.toBean(createReqVO, BidDO.class);
        bidMapper.insert(bid);
        return bid.getId();
    }

    @Override
    public void updateBid(BidSaveReqVO updateReqVO) {
        validateBidExists(updateReqVO.getId());
        bidMapper.updateById(BeanUtils.toBean(updateReqVO, BidDO.class));
    }

    @Override
    public void deleteBid(Long id) {
        validateBidExists(id);
        bidMapper.deleteById(id);
    }

    private void validateBidExists(Long id) {
        if (id == null || bidMapper.selectById(id) == null) {
            throw exception(BID_NOT_EXISTS);
        }
    }

    @Override
    public BidDO getBid(Long id) {
        return bidMapper.selectById(id);
    }

    @Override
    public PageResult<BidDO> getBidPage(BidPageReqVO pageReqVO) {
        return bidMapper.selectPage(pageReqVO);
    }

}
