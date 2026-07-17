package cn.iocoder.yudao.module.biz.controller.admin.bid.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BidRespVO {

    private Long id;
    private String bidNo;
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
    private LocalDateTime createTime;

}
