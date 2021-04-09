package com.lmaye.ms.core.exception;

import com.lmaye.ms.core.context.IResultCode;

/**
 * -- Auth Exception
 *
 * @author lmay.Zhou
 * @date 2020/9/27 17:44 星期日
 * @since Email: lmay_zlm@meten.com
 */
public class AuthException extends Exception {
    /**
     * 响应编码
     */
    private final IResultCode resultCode;

    public AuthException(IResultCode resultCode) {
        super(resultCode.getKey());
        this.resultCode = resultCode;
    }

    public AuthException(IResultCode resultCode, Throwable cause) {
        super(resultCode.getKey(), cause);
        this.resultCode = resultCode;
    }

    public IResultCode getResultCode() {
        return resultCode;
    }
}
