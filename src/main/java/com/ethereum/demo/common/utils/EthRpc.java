package com.ethereum.demo.common.utils;

import com.ethereum.demo.common.Result.StandardResult;
import com.ethereum.demo.common.Result.TransactionResult;
import org.web3j.crypto.CipherException;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface EthRpc {
    String getVersion() throws IOException;
    List<String> getAccounts() throws IOException;
    BigDecimal getBlance(String address) throws IOException;
    BigInteger getNonce(String address) throws IOException;
    Boolean unLockAccount(String address,String password) throws IOException;
    String getCoinbase() throws IOException;
    TransactionResult sendTransferTransactionByGeth(String from,
                                                 String password,
                                                 String to,
                                                 BigInteger value) throws IOException;
    TransactionResult sendTransferTransaction(String walletpath,
                                              String from,
                                              String to,
                                              BigInteger value) throws Exception;
    String getTransactionReceipt(String txhash) throws Exception;
}
