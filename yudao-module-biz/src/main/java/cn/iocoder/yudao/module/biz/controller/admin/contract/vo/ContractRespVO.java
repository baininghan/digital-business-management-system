package cn.iocoder.yudao.module.biz.controller.admin.contract.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ContractRespVO {

    private Long id;
    private String contractNo;
    private String name;
    private String customerName;
    private Long projectId;
    private String contractType;
    private BigDecimal amount;
    private LocalDate signDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal dueSettleAmount;
    private BigDecimal settledAmount;
    private BigDecimal invoicedAmount;
    private BigDecimal receivedAmount;
    private String status;
    private String attachmentUrl;
    private LocalDateTime createTime;

}
