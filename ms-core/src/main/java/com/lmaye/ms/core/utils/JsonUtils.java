package com.lmaye.ms.core.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.lmaye.ms.core.constants.CoreConstants;
import com.lmaye.ms.core.context.ResultCode;
import com.lmaye.ms.core.exception.CoreException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;

/**
 * -- JSON工具
 *
 * @author lmay.Zhou
 * @qq 379839355
 * @email lmay@lmaye.com
 * @since 2020/7/1 8:25 星期三
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonUtils {
    /**
     * ObjectMapper 该类线程安全，可以全局公用一个
     */
    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.setLocale(Locale.CHINA);
        //序列化忽略为空的字段
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //序列化处理
        objectMapper.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
        objectMapper.configure(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER.mappedFeature(), true);
        //去掉默认的时间戳格式
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //失败处理
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //单引号处理
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        //序列化时，日期的统一格式
        objectMapper.setDateFormat(CoreConstants.SIMPLE_DATE_FORMAT);
        //设置为中国上海时区
        objectMapper.setTimeZone(CoreConstants.TIME_ZONE);
        //时间格式化
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(CoreConstants.YYYY_MM_DD_HH_MM_SS));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(CoreConstants.YYYY_MM_DD));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(CoreConstants.HH_MM_SS));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(CoreConstants.YYYY_MM_DD_HH_MM_SS));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(CoreConstants.YYYY_MM_DD));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(CoreConstants.HH_MM_SS));
        objectMapper.registerModule(javaTimeModule);
        //长整型格式化
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
    }

    /**
     * 把Bean转成字符串
     *
     * @param bean 对象
     * @return String
     */
    public static <T> String toStr(T bean) {
        try {
            return objectMapper.writeValueAsString(bean);
        } catch (Exception e) {
            throw new CoreException(ResultCode.JSON_BEAN_TO_STR_FAILED, e);
        }
    }

    /**
     * 把Json字符串转成Bean
     *
     * @param json  字符串
     * @param clazz 要转换的Bean的Class
     * @return T
     */
    public static <T> T toBean(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new CoreException(ResultCode.JSON_STR_TO_BEAN_FAILED, e);
        }
    }

    /**
     * 获取 ObjectMapper
     *
     * @return ObjectMapper
     */
    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
