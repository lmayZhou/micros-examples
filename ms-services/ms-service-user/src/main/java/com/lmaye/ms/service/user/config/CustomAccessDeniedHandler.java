package com.lmaye.ms.service.user.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmaye.ms.core.context.ResultCode;
import com.lmaye.ms.core.context.ResultVO;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * -- Custom Access Denied Handler
 *
 * @author lmay.Zhou
 * @date 2020/12/24 15:01
 * @email lmay@lmaye.com
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        // 权限不足处理
        response.setContentType("application/json;charset=UTF-8");
        ObjectMapper mapper = new ObjectMapper();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(mapper.writeValueAsString(ResultVO.response(ResultCode.UNAUTHORIZED,
                accessDeniedException.getMessage())));
    }
}
