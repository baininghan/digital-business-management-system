package cn.iocoder.yudao.module.biz.controller.admin.project.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ProjectRespVO {

    private Long id;
    private String projectNo;
    private String name;
    private String type;
    private String ownerName;
    private String members;
    private Long contractId;
    private String outputCalcType;
    private LocalDate planStartDate;
    private LocalDate planEndDate;
    private LocalDate actualStartDate;
    private LocalDate actualEndDate;
    private String status;
    private BigDecimal completionRate;
    private BigDecimal accumulatedOutput;
    private BigDecimal actualCost;
    private String description;
    private LocalDateTime createTime;

}
