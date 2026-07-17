package cn.iocoder.yudao.module.biz.dal.mysql;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.biz.controller.admin.contract.vo.ContractPageReqVO;
import cn.iocoder.yudao.module.biz.dal.dataobject.ContractDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ContractMapper extends BaseMapperX<ContractDO> {

    default PageResult<ContractDO> selectPage(ContractPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ContractDO>()
                .likeIfPresent(ContractDO::getName, reqVO.getName())
                .likeIfPresent(ContractDO::getCustomerName, reqVO.getCustomerName())
                .eqIfPresent(ContractDO::getStatus, reqVO.getStatus())
                .orderByDesc(ContractDO::getId));
    }

}
