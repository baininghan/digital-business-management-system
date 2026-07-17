package cn.iocoder.yudao.module.biz.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

/**
 * 经营管理错误码。
 *
 * biz 模块使用 1-020-000-000 段。
 */
public interface ErrorCodeConstants {

    ErrorCode PROJECT_NOT_EXISTS = new ErrorCode(1_020_000_001, "项目不存在");
    ErrorCode CONTRACT_NOT_EXISTS = new ErrorCode(1_020_000_002, "合同不存在");
    ErrorCode OPPORTUNITY_NOT_EXISTS = new ErrorCode(1_020_000_003, "商机不存在");
    ErrorCode BID_NOT_EXISTS = new ErrorCode(1_020_000_004, "投标报价不存在");

}
