package com.lmaye.ms.user.config;

import com.lmaye.cloud.starter.web.context.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * -- Response Advice
 *
 * @author Lmay Zhou
 * @date 2021/3/11 16:32
 * @email lmay@lmaye.com
 */
@Slf4j
@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
    /**
     * Message Source
     */
    private final MessageSource messageSource;

    public ResponseAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public boolean supports(MethodParameter methodParam, Class<? extends HttpMessageConverter<?>> clazz) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParam, MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> clazz, ServerHttpRequest request,
                                  ServerHttpResponse response) {
        try {
            if (MediaType.APPLICATION_JSON.equals(mediaType) && body instanceof ResultVO) {
                ResultVO<Object> rs = (ResultVO<Object>) body;
                rs.setMsg(messageSource.getMessage(rs.getMsg(), null, LocaleContextHolder.getLocale()));
                return rs;
            }
        } catch (Exception e) {
            log.error("国际化失败: ", e);
        }
        return body;
    }
}