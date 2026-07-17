package cn.iocoder.yudao.module.biz.controller.admin.project.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import lombok.Data;

@Data
public class ProjectPageReqVO extends PageParam {

    private String name;
    private String type;
    private String ownerName;
    private String status;

}
