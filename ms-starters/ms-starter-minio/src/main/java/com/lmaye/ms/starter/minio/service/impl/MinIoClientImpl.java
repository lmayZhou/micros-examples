package com.lmaye.ms.starter.minio.service.impl;

import com.lmaye.ms.starter.minio.service.MinIoClient;
import io.minio.MinioClient;

import java.util.Objects;

/**
 * -- MinIo Client 实现类
 *
 * @author lmay.Zhou
 * @date 2020/10/12 17:46 星期一
 * @since Email: lmay_zlm@meten.com
 */
public class MinIoClientImpl implements MinIoClient {
    private MinioClient minIoClient = null;

    /**
     * 获取 MinIoClient
     *
     * @param endpoint  终端
     * @param accessKey 访问密钥
     * @param secretKey 密钥
     * @return MinioClient
     */
    @Override
    public MinioClient getClient(String endpoint, String accessKey, String secretKey) {
        if(Objects.isNull(minIoClient)) {
            return MinioClient.builder().endpoint(endpoint).credentials(accessKey, secretKey).build();
        }
        return minIoClient;
    }
}
