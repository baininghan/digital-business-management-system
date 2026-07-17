package cn.iocoder.yudao.module.biz.service.contract;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.biz.controller.admin.contract.vo.ContractPageReqVO;
import cn.iocoder.yudao.module.biz.controller.admin.contract.vo.ContractSaveReqVO;
import cn.iocoder.yudao.module.biz.dal.dataobject.ContractDO;
import cn.iocoder.yudao.module.biz.dal.mysql.ContractMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.biz.enums.ErrorCodeConstants.CONTRACT_NOT_EXISTS;

@Service
@Validated
public class ContractServiceImpl implements ContractService {

    @Resource
    private ContractMapper contractMapper;

    @Override
    public Long createContract(ContractSaveReqVO createReqVO) {
        ContractDO contract = BeanUtils.toBean(createReqVO, ContractDO.class);
        contractMapper.insert(contract);
        return contract.getId();
    }

    @Override
    public void updateContract(ContractSaveReqVO updateReqVO) {
        validateContractExists(updateReqVO.getId());
        contractMapper.updateById(BeanUtils.toBean(updateReqVO, ContractDO.class));
    }

    @Override
    public void deleteContract(Long id) {
        validateContractExists(id);
        contractMapper.deleteById(id);
    }

    private void validateContractExists(Long id) {
        if (id == null || contractMapper.selectById(id) == null) {
            throw exception(CONTRACT_NOT_EXISTS);
        }
    }

    @Override
    public ContractDO getContract(Long id) {
        return contractMapper.selectById(id);
    }

    @Override
    public PageResult<ContractDO> getContractPage(ContractPageReqVO pageReqVO) {
        return contractMapper.selectPage(pageReqVO);
    }

}
