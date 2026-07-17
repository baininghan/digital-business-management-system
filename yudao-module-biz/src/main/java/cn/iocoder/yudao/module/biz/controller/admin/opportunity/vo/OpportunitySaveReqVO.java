package cn.iocoder.yudao.module.biz.controller.admin.opportunity.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class OpportunitySaveReqVO {

    private Long id;
    private String opportunityNo;
    @NotEmpty(message = "商机名称不能为空")
    private String name;
    private String customerName;
    private BigDecimal estimatedAmount;
    private String stage;
    private String expectedSignMonth;
    private String ownerName;
    private Boolean convertedToBid;
    private Long bidId;
    private Boolean convertedToContract;
    private Long contractId;
    private LocalDate lastFollowDate;
    private String followRecord;

}
