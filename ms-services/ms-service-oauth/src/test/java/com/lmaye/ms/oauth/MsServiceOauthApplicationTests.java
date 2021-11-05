package com.lmaye.ms.oauth;

import com.lmaye.ms.oauth.utils.AdminTokenUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class MsServiceOauthApplicationTests {

    @Test
    void contextLoads() {
        Map<String, Object> payload = new HashMap<>(2);
        payload.put("author", "Lmay Zhou");
        payload.put("email", "lmay@lmaye.com");
        System.out.println(AdminTokenUtils.adminToken(payload));
    }

}
