package cn.iocoder.yudao.module.biz.dal.dataobject;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@TableName("biz_opportunity")
@KeySequence("biz_opportunity_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpportunityDO extends BaseDO {

    @TableId
    private Long id;
    private String opportunityNo;
    private String name;
    private String customerName;
    private BigDecimal estimatedAmount;
    private String stage;
    /**
     * 预计签约月份，格式 yyyy-MM。
     */
    private String expectedSignMonth;
    private String ownerName;
    private Boolean convertedToBid;
    private Long bidId;
    private Boolean convertedToContract;
    private Long contractId;
    private LocalDate lastFollowDate;
    private String followRecord;

}
