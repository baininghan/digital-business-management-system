package cn.iocoder.yudao.module.biz.controller.admin.dashboard;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DashboardSummaryRespVO {

    private BigDecimal monthOutput;
    private BigDecimal monthActualCost;
    private BigDecimal monthProfit;
    private Long newContractCount;
    private BigDecimal newContractAmount;
    private BigDecimal dueUnsettledAmount;
    private BigDecimal receivableUnreceivedAmount;
    private Long trackingOpportunityCount;
    private BigDecimal trackingOpportunityAmount;
    private Long bidCount;
    private BigDecimal bidAmount;
    private BigDecimal wonBidAmount;
    private BigDecimal convertedContractAmount;

}
