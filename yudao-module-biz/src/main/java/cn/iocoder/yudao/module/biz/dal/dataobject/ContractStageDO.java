package cn.iocoder.yudao.module.biz.dal.dataobject;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@TableName("biz_contract_stage")
@KeySequence("biz_contract_stage_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractStageDO extends BaseDO {

    @TableId
    private Long id;
    private Long contractId;
    private String stageName;
    private BigDecimal receivableAmount;
    private LocalDate planDate;
    private Boolean paid;
    private Boolean invoiced;
    private Boolean received;
    private BigDecimal paidAmount;
    private BigDecimal invoicedAmount;
    private BigDecimal receivedAmount;

}
