package cn.iocoder.yudao.module.biz.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "管理后台 - 项目新增/修改 Request VO")
@Data
public class ProjectSaveReqVO {

    private Long id;
    private String projectNo;
    @NotEmpty(message = "项目名称不能为空")
    private String name;
    @NotEmpty(message = "项目类型不能为空")
    private String type;
    @NotEmpty(message = "项目负责人不能为空")
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

}
