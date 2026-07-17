package cn.iocoder.yudao.module.biz.controller.admin.contract.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ContractSaveReqVO {

    private Long id;
    private String contractNo;
    @NotEmpty(message = "合同名称不能为空")
    private String name;
    @NotEmpty(message = "客户名称不能为空")
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

}
