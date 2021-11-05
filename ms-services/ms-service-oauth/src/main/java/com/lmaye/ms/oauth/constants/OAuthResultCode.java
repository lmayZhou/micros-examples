package com.lmaye.ms.oauth.constants;

import com.lmaye.cloud.core.context.IResultCode;

/**
 * -- 响应编码
 * - 枚举
 *
 * @author lmay.Zhou
 * @qq 379839355
 * @email lmay@lmaye.com
 * @since 2020/7/1 7:59 星期三
 */
public enum OAuthResultCode implements IResultCode {
    /**
     * 枚举值
     */
    CAPTCHA_ERROR(-1001, "msg.captcha.error", "验证码错误"),
    CAPTCHA_EMPTY(-1002, "msg.captcha.empty", "请输入验证码"),
    USER_VERIFICATION_FAILURE(-1003, "msg.user.verification.error", "用户名或密码错误");

    /**
     * 枚举编码
     */
    private final Integer code;

    /**
     * 消息键(国际化)
     */
    private final String key;

    /**
     * 枚举描述
     */
    private final String desc;

    OAuthResultCode(Integer code, String key, String desc) {
        this.code = code;
        this.key = key;
        this.desc = desc;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
