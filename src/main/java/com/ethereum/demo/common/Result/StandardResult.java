package com.ethereum.demo.common.Result;

import java.util.HashMap;

/**
 * @author longbo
 * @date 2021.03.29
 * @param <T> map<String,Object>的映射，作为json对象返回
 * @apiNote 用于定义标准返回格式
 */
public class StandardResult<T> {
    private int code;
    private String msg;
    private T data;

    public StandardResult(){}

    public StandardResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public StandardResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "StandardResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    /**
     * @apiNote 返回成功的结果
     * @param data 返回的json数据
     */
    public static <T> StandardResult<T> success(T data){
        return new StandardResult<>(ResultCode.SUCCESS.getCode(),ResultCode.SUCCESS.getMsg(),data);
    }

    /**
     *
     * @param data 自定义的json数据格式
     * @param msg 成功的消息，自定义的
     * @apiNote 返回成功结果
     */
    public static <T> StandardResult<T> success(T data,String msg){
        return new StandardResult<>(ResultCode.SUCCESS.getCode(), msg, data);
    }

    /**
     * @apiNote 返回失败结果
     * @param transactionResult 交易hash和eventmsg
     */
    public static <T> StandardResult success(TransactionResult transactionResult){
        //成功情况下的标准返回构造
        HashMap<String,Object> map = new HashMap<>();
        map.put("txhash",transactionResult.getTxhash());
        if(transactionResult.getReceipt()!=null){
            map.put("txreceipt",transactionResult.getReceipt());
        }
        if(transactionResult.getEventmsg() == null){
            return StandardResult.success(map);
        }else{
            map.put("eventMsg",transactionResult.getEventmsg());
            return StandardResult.success(map);
        }
    }

    /**
     * @apiNote 返回失败结果
     */
    public static <T> StandardResult<T> fail(){
        return new StandardResult<>(ResultCode.FAIL.getCode(), ResultCode.FAIL.getMsg());
    }

    /**
     * @apiNote 返回失败结果
     * @param msg 自定义消息
     */
    public static <T> StandardResult<T> fail(String msg){
        return new StandardResult<>(ResultCode.FAIL.getCode(), msg);
    }

    /**
     * @apiNote 返回失败结果
     * @param transactionResult 自定义交易消息
     */
    public static <T> StandardResult fail(TransactionResult transactionResult){
        if(transactionResult == null) return StandardResult.fail();
        return StandardResult.fail(transactionResult.getEventmsg());
    }
}
