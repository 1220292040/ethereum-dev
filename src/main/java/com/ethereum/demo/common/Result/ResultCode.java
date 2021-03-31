package com.ethereum.demo.common.Result;

/**
 * @author longbo
 * @date 2021.03.29
 * @apiNote 枚举类，用来存放返回码以及返回消息
 */
public enum ResultCode {
    SUCCESS(200,"操作成功"),
    FAIL(504,"操作失败");

    private int code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ResultCode{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
