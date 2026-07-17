package cn.iocoder.yudao.module.biz.controller.admin.opportunity.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class OpportunityRespVO {

    private Long id;
    private String opportunityNo;
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
    private LocalDateTime createTime;

}
