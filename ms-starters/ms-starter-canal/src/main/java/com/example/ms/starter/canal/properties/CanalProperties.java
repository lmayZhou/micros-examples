package com.example.ms.starter.canal.properties;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * --
 *
 * @author lmay.Zhou
 * @qq 379839355
 * @email lmay@lmaye.com
 * @date 2021/4/9 下午3:15
 * @since 1.8
 */
@Data
public class CanalProperties {
    public static final String CANAL_PREFIX = "canal";

    public static final String CANAL_ASYNC = CANAL_PREFIX + "." + "async";

    public static final String CANAL_MODE = CANAL_PREFIX + "." + "mode";

    /**
     * simple,cluster,zookeeper,kafka,rocketMQ
     */
    private String mode;

    private Boolean async;

    private String server;

    private String destination;

    private String filter = StringUtils.EMPTY;

    private Integer batchSize = 1;

    private Long timeout = 1L;

    private TimeUnit unit = TimeUnit.SECONDS;
}
