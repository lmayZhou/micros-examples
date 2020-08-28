package com.lmaye.ms.core.utils;

import cn.hutool.core.lang.Snowflake;
import com.lmaye.ms.core.constants.CoreConstants;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.lang.management.ManagementFactory;

/**
 * -- ID 工具类
 *
 * @author lmay.Zhou
 * @qq 379839355
 * @email lmay@lmaye.com
 * @since 2020/7/5 23:28 星期日
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IdUtils {
    private static final Snowflake SNOW_FLAKE;

    static {
        long dataCenterId = CoreUtils.getDataCenterId();
        long workerId = getWorkerId(dataCenterId);
        SNOW_FLAKE = new Snowflake(dataCenterId, workerId, true);
    }

    /**
     * 生成ID
     *
     * @return long
     */
    public static long nextId() {
        return SNOW_FLAKE.nextId();
    }

    /**
     * 生成ID
     *
     * @return long
     */
    public static String nextStrId() {
        return String.valueOf(SNOW_FLAKE.nextId());
    }

    /**
     * 获取 maxWorkerId
     */
    private static long getWorkerId(long dataCenterId) {
        StringBuilder id = new StringBuilder();
        id.append(dataCenterId);
        String name = ManagementFactory.getRuntimeMXBean().getName();
        if (!StringUtils.isEmpty(name)) {
            /*
             * GET jvmPid
             */
            id.append(name.split(CoreConstants.AT)[0]);
        }
        /*
         * MAC + PID 的 hashcode 获取16个低位
         */
        return (id.toString().hashCode() & 0xffff) % (31 + 1);
    }
}
