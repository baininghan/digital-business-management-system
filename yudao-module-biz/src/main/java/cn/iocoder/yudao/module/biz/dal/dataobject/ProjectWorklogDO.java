package cn.iocoder.yudao.module.biz.dal.dataobject;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;

@TableName("biz_project_worklog")
@KeySequence("biz_project_worklog_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectWorklogDO extends BaseDO {

    @TableId
    private Long id;
    private Long projectId;
    private String workMonth;
    private String memberName;
    private BigDecimal hours;

}
