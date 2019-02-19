package com.hydinin.base_module.api;

/**
 * Created by Android Studio
 * Project：ZhiChuang
 * Author：httvc
 * Email：jfjie2013@163.com
 * Date：2018/3/20.
 */

public class HttpResult<T> {
    private String msg;    //提示
    private T result;        //数据a
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
