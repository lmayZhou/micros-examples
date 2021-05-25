package com.lmaye.ms.core.context;

/**
 * -- 响应编码
 *
 * @author lmay.Zhou
 * @date 2020/9/27 17:47 星期日
 * @since Email: lmay_zlm@meten.com
 */
public interface IResultCode {
    /**
     * 获取代码
     *
     * @return Integer
     */
    Integer getCode();

    /**
     * 消息键(国际化)
     *
     * @return String
     */
    String getKey();

    /**
     * 获取消息
     *
     * @return String
     */
    String getDesc();
}
