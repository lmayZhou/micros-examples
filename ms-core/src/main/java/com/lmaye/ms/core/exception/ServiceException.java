package com.lmaye.ms.core.exception;

import com.lmaye.ms.core.context.IResultCode;

/**
 * -- 业务自定义异常
 *
 * @author lmay.Zhou
 * @qq 379839355
 * @email lmay@lmaye.com
 * @since 2020/7/1 8:04 星期三
 */
public class ServiceException extends RuntimeException {
    /**
     * 响应编码
     */
    private final IResultCode resultCode;

    public ServiceException(IResultCode resultCode) {
        super(resultCode.getKey());
        this.resultCode = resultCode;
    }

    public ServiceException(IResultCode resultCode, Throwable cause) {
        super(resultCode.getKey(), cause);
        this.resultCode = resultCode;
    }

    /**
     * 获取错误信息
     *
     * @return IResultCode
     */
    public IResultCode getResultCode() {
        return resultCode;
    }
}
