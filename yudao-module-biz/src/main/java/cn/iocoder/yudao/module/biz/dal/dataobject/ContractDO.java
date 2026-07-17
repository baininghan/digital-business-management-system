package cn.iocoder.yudao.module.biz.dal.dataobject;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@TableName("biz_contract")
@KeySequence("biz_contract_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractDO extends BaseDO {

    @TableId
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

}
