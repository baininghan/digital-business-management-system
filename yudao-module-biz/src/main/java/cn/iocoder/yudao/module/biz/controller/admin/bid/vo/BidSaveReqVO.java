package cn.iocoder.yudao.module.biz.controller.admin.bid.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BidSaveReqVO {

    private Long id;
    private String bidNo;
    @NotEmpty(message = "报价名称不能为空")
    private String name;
    private String customerName;
    private String bidType;
    private BigDecimal amount;
    private LocalDate submitDate;
    private String status;
    private String ownerName;
    private Long opportunityId;
    private String winStatus;
    private Boolean convertedToContract;
    private Long contractId;
    private LocalDate expectedResultDate;
    private String remark;

}
