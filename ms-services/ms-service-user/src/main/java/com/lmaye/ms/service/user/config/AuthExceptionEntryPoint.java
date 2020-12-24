package com.lmaye.ms.service.user.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmaye.ms.core.context.ResultCode;
import com.lmaye.ms.core.context.ResultVO;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * -- Auth Exception Entry Point
 *
 * @author lmay.Zhou
 * @date 2020/12/24 14:51
 * @email lmay@lmaye.com
 */
public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        // 无效Token
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), ResultVO.response(ResultCode.UNAUTHORIZED, authException.getMessage()));
    }
}
