package com.example.xing.common;

import com.google.common.collect.Maps;

import org.springframework.validation.BindingResult;

import java.io.Serializable;
import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 返回数据
 */
@Getter
@Setter
@ToString
@ApiModel("响应对象")
public class R implements Serializable {

    @ApiModelProperty(value = "响应码", example = "0", required = true)
    protected int code = 0;

    @ApiModelProperty(value = "响应消息", example = "success", required = true)
    protected String msg = "success";

    @ApiModelProperty(value = "响应数据", example = "{}")
    private Map data;

    public static R error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static R error(String msg) {
        return error(500, msg);
    }

    public static R error(BindingResult result) {

        String msg;
        if (null != result && !result.getAllErrors().isEmpty()) {
            msg = result.getAllErrors().get(0).getDefaultMessage();
        } else {
            msg = "未知异常，请联系管理员";
        }

        return error(500, msg);
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

    public static R ok(String msg) {
        R r = new R();
        r.setMsg(msg);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.data = map;
        return r;
    }

    public static R ok() {
        return new R();
    }

    public R put(String key, Object value) {
        if (null == data) {
            data = Maps.newHashMap();
        }
        data.put(key, value);
        return this;
    }
}
