package cn.iocoder.yudao.module.biz.service.contract;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.biz.controller.admin.contract.vo.ContractPageReqVO;
import cn.iocoder.yudao.module.biz.controller.admin.contract.vo.ContractSaveReqVO;
import cn.iocoder.yudao.module.biz.dal.dataobject.ContractDO;

public interface ContractService {

    Long createContract(ContractSaveReqVO createReqVO);

    void updateContract(ContractSaveReqVO updateReqVO);

    void deleteContract(Long id);

    ContractDO getContract(Long id);

    PageResult<ContractDO> getContractPage(ContractPageReqVO pageReqVO);

}
