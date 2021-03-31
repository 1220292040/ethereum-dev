package com.ethereum.demo.common.Result;


/**
 * 处理交易类型的返回数据，有哈希，收据，事件数据
 */
public class TransactionResult {
    private String txhash;
    private String eventmsg;
    private String receipt;

    public TransactionResult() {
    }

    public TransactionResult(String txhash) {
        this.txhash = txhash;
    }

    public TransactionResult(String txhash, String eventmsg) {
        this.txhash = txhash;
        this.eventmsg = eventmsg;
    }

    public TransactionResult(String txhash, String eventmsg, String receipt) {
        this.txhash = txhash;
        this.eventmsg = eventmsg;
        this.receipt = receipt;
    }

    public String getTxhash() {
        return txhash;
    }

    public void setTxhash(String txhash) {
        this.txhash = txhash;
    }

    public String getEventmsg() {
        return eventmsg;
    }

    public void setEventmsg(String eventmsg) {
        this.eventmsg = eventmsg;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    @Override
    public String toString() {
        return "TransactionResult{" +
                "txhash='" + txhash + '\'' +
                ", eventmsg='" + eventmsg + '\'' +
                ", receipt='" + receipt + '\'' +
                '}';
    }
}
