package com.lmaye.ms.core.context;

/**
 * -- 响应编码
 * - 枚举
 *
 * @author lmay.Zhou
 * @qq 379839355
 * @email lmay@lmaye.com
 * @since 2020/7/1 7:59 星期三
 */
public enum ResultCode implements IResultCode {
    /**
     * 枚举值
     */
    SUCCESS(200, "success", "处理成功"),
    FAILURE(-100, "failure", "处理失败"),
    UNAUTHORIZED(401, "unauthorized", "请求授权失败"),
    FORBIDDEN(403, "forbidden", "请求不允许"),
    NOT_FOUND(404, "not.found", "无法找到资源"),
    METHOD_NOT_ALLOWED(405, "method.not.allowed", "请求方法被禁止"),
    INTERNAL_SERVER_ERROR(500, "internal.server.error", "服务器内部错误"),
    JSON_BEAN_TO_STR_FAILED(500, "json.bean.to.str.failed", "JsonBean转字符串失败"),
    JSON_STR_TO_BEAN_FAILED(500, "json.str.to.bean.failed", "Json字符串转Bean失败"),
    ARGUMENT_BIND_FAILED(400, "argument.bind.failed", "参数绑定失败"),
    ANTISAMY_DATA_INIT_FAILED(500, "antisamy.data.init.failed", "antisamy数据初始化失败"),
    IP_DATA_INIT_FAILED(500, "ip.data.init.failed", "IP数据初始化失败"),
    GET_IP_ADDRESS_FAILED(500, "get.ip.address.failed", "获取IP地址失败"),
    OPERATION_FAILED(500, "operation.failed", "操作失败"),
    SERVICE_UNAVAILABLE(503, "Service Unavailable", "服务器出错"),
    USER_VERIFICATION_FAILURE(401, "user.verification.failure", "用户名或密码错误");

    /**
     * 枚举编码
     */
    private final Integer code;

    /**
     * 枚举属性键(国际化)
     */
    private final String propKey;

    /**
     * 枚举描述
     */
    private final String desc;

    ResultCode(Integer code, String propKey, String desc) {
        this.code = code;
        this.propKey = propKey;
        this.desc = desc;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getPropKey() {
        return propKey;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
