package cn.iocoder.yudao.module.biz.service.bid;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.biz.controller.admin.bid.vo.BidPageReqVO;
import cn.iocoder.yudao.module.biz.controller.admin.bid.vo.BidSaveReqVO;
import cn.iocoder.yudao.module.biz.dal.dataobject.BidDO;

public interface BidService {

    Long createBid(BidSaveReqVO createReqVO);

    void updateBid(BidSaveReqVO updateReqVO);

    void deleteBid(Long id);

    BidDO getBid(Long id);

    PageResult<BidDO> getBidPage(BidPageReqVO pageReqVO);

}
