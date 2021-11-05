package com.lmaye.ms.file;

import com.lmaye.cloud.starter.minio.service.IMinIoClientService;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.http.Method;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@SpringBootTest
class MsServiceFileApplicationTests {
    @Autowired
    private IMinIoClientService minIoClientService;

    @Test
    void contextLoads() throws Exception {
        MinioClient client = minIoClientService.getClient("http://192.168.0.10:9000",
                "minio", "minio123");
        String url = client.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.GET).bucket("ms-example")
                .object("咕泡学院Java架构师VIP课程大纲_V4.1.png").expiry(3, TimeUnit.DAYS).build());
        System.out.println("--> " + url);
    }
}
