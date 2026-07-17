package cn.iocoder.yudao.module.biz.dal.dataobject;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@TableName("biz_bid")
@KeySequence("biz_bid_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BidDO extends BaseDO {

    @TableId
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

}
