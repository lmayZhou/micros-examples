package com.lmaye.ms.core.context;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Objects;

/**
 * -- 通用响应结果实体
 *
 * @author lmay.Zhou
 * @qq 379839355
 * @email lmay@lmaye.com
 * @since 2020/7/1 8:01 星期三
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "ResponseResult", description = "响应结果")
public class ResultVO<T> implements Serializable {
    /**
     * 响应代码
     */
    @ApiModelProperty("响应代码")
    private Integer code;

    /**
     * 响应消息
     */
    @ApiModelProperty("响应消息")
    private String msg;

    /**
     * 响应数据
     */
    @ApiModelProperty("响应数据")
    private T data;

    public ResultVO() {
    }

    public ResultVO(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultVO(IResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getDesc();
    }

    public ResultVO(IResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getDesc();
        this.data = data;
    }

    /**
     * 处理成功
     *
     * @param data 响应数据
     * @param <T>  泛型
     * @return ResponseResult<T>
     */
    public static <T> ResultVO<T> success(T data) {
        return new ResultVO<>(ResultCode.SUCCESS, data);
    }

    /**
     * 处理失败
     *
     * @param <T> 泛型
     * @return ResponseResult<T>
     */
    public static <T> ResultVO<T> failed() {
        return new ResultVO<>(ResultCode.FAILURE, null);
    }

    /**
     * 响应结果
     *
     * @param resultCode 响应编码
     * @param data       响应数据
     * @param <T>        泛型
     * @return ResponseResult<T>
     */
    public static <T> ResultVO<T> response(IResultCode resultCode, T data) {
        return new ResultVO<>(resultCode, data);
    }

    /**
     * 响应结果是否成功
     *
     * @return boolean
     */
    public boolean isSuccess() {
        if (Objects.isNull(code)) {
            return false;
        }
        return Objects.equals(ResultCode.SUCCESS.getCode(), code);
    }
}
