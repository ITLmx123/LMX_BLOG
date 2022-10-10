package com.limengxiang.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.limengxiang.enums.ResponseCodeEnum;
import lombok.Data;

import java.io.Serializable;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseResult implements Serializable {
    private String msg;
    private Integer code;
    private Object data;

    public ResponseResult() {
        this.code = ResponseCodeEnum.SUCCESS.getCode();
        this.msg = ResponseCodeEnum.SUCCESS.getMsg();
    }

    public ResponseResult(Integer code, Object data) {
        this.code = code;
        this.data = data;
    }

    public ResponseResult(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public static ResponseResult errorResult(int code, String msg) {
        ResponseResult result = new ResponseResult();
        return result.error(code, msg);
    }
    public static ResponseResult okResult() {
        return new ResponseResult();
    }
    public static ResponseResult okResult(int code, String msg) {
        ResponseResult result = new ResponseResult();
        return result.ok(code, null, msg);
    }

    public static ResponseResult okResult(Object data) {
        ResponseResult result = setResponseCodeEnum(ResponseCodeEnum.SUCCESS, ResponseCodeEnum.SUCCESS.getMsg());
        if(data!=null) {
            result.setData(data);
        }
        return result;
    }

    public static ResponseResult errorResult(ResponseCodeEnum enums){
        return setResponseCodeEnum(enums,enums.getMsg());
    }

    public static ResponseResult errorResult(ResponseCodeEnum enums, String msg){
        return setResponseCodeEnum(enums,msg);
    }

    public static ResponseResult setResponseCodeEnum(ResponseCodeEnum enums){
        return okResult(enums.getCode(),enums.getMsg());
    }

    private static ResponseResult setResponseCodeEnum(ResponseCodeEnum enums, String msg){
        return okResult(enums.getCode(),msg);
    }

    public ResponseResult error(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        return this;
    }

    public ResponseResult ok(Integer code, Object data) {
        this.code = code;
        this.data = data;
        return this;
    }

    public ResponseResult ok(Integer code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        return this;
    }

    public ResponseResult ok(Object data) {
        this.data = data;
        return this;
    }
}
