package cn.iocoder.yudao.module.biz.dal.mysql;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.biz.dal.dataobject.AnnualTargetDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AnnualTargetMapper extends BaseMapperX<AnnualTargetDO> {

    default List<AnnualTargetDO> selectListOrderByYear() {
        return selectList(new LambdaQueryWrapperX<AnnualTargetDO>()
                .orderByDesc(AnnualTargetDO::getTargetYear));
    }

}
