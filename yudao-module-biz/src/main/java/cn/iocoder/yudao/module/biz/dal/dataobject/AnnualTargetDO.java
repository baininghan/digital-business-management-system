package cn.iocoder.yudao.module.biz.dal.dataobject;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;

@TableName("biz_annual_target")
@KeySequence("biz_annual_target_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnnualTargetDO extends BaseDO {

    @TableId
    private Long id;
    private Integer targetYear;
    private BigDecimal outputTarget;
    private BigDecimal contractTarget;
    private BigDecimal profitTarget;
    private String remark;

}
