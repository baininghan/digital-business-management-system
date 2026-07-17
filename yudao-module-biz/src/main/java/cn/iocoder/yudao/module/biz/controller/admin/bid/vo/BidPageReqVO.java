package cn.iocoder.yudao.module.biz.controller.admin.bid.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import lombok.Data;

@Data
public class BidPageReqVO extends PageParam {

    private String name;
    private String customerName;
    private String bidType;
    private String status;

}
