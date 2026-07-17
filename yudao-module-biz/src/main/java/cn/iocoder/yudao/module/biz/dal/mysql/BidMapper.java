package cn.iocoder.yudao.module.biz.dal.mysql;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.biz.controller.admin.bid.vo.BidPageReqVO;
import cn.iocoder.yudao.module.biz.dal.dataobject.BidDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BidMapper extends BaseMapperX<BidDO> {

    default PageResult<BidDO> selectPage(BidPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<BidDO>()
                .likeIfPresent(BidDO::getName, reqVO.getName())
                .likeIfPresent(BidDO::getCustomerName, reqVO.getCustomerName())
                .eqIfPresent(BidDO::getBidType, reqVO.getBidType())
                .eqIfPresent(BidDO::getStatus, reqVO.getStatus())
                .orderByDesc(BidDO::getId));
    }

}
