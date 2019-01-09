package com.chrisyoung.huajiangapp.network;

import com.chrisyoung.huajiangapp.constant.ResultCode;

import java.io.Serializable;

/**
 * @program: appserver
 * @author: Chris Young
 * @create: 2018-11-19 15:47
 * @description: RestfulAPI统一返回格式
 **/



public class HttpResult<T> implements Serializable {

    private static final long serialVersionUID = -3948389268046368059L;

    private Integer code;

    private String msg;


    private  T data;


    public HttpResult() {}

    public HttpResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public void setResultCode(ResultCode code) {
        this.code = code.code();
        this.msg = code.message();
    }
}

