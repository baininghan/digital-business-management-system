package cn.iocoder.yudao.module.biz.dal.mysql;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.biz.dal.dataobject.ContractStageDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ContractStageMapper extends BaseMapperX<ContractStageDO> {

    default List<ContractStageDO> selectListByContractId(Long contractId) {
        return selectList(new LambdaQueryWrapperX<ContractStageDO>()
                .eq(ContractStageDO::getContractId, contractId)
                .orderByAsc(ContractStageDO::getPlanDate)
                .orderByAsc(ContractStageDO::getId));
    }

}
