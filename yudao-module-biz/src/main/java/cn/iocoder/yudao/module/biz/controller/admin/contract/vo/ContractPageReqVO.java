package cn.iocoder.yudao.module.biz.controller.admin.contract.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import lombok.Data;

@Data
public class ContractPageReqVO extends PageParam {

    private String name;
    private String customerName;
    private String status;

}
