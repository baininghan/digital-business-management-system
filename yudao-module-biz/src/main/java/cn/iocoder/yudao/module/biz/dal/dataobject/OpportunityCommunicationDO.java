package cn.iocoder.yudao.module.biz.dal.dataobject;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDate;

@TableName("biz_opportunity_communication")
@KeySequence("biz_opportunity_communication_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpportunityCommunicationDO extends BaseDO {

    @TableId
    private Long id;
    private Long opportunityId;
    private LocalDate communicationDate;
    private String content;
    private String nextAction;
    private String ownerName;

}
