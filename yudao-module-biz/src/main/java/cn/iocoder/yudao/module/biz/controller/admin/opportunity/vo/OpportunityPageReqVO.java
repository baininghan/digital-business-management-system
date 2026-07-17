package cn.iocoder.yudao.module.biz.controller.admin.opportunity.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import lombok.Data;

@Data
public class OpportunityPageReqVO extends PageParam {

    private String name;
    private String customerName;
    private String stage;
    private String ownerName;

}
