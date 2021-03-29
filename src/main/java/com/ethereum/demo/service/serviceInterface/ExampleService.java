package com.ethereum.demo.service.serviceInterface;

import com.ethereum.demo.common.Result.StandardResult;
import com.ethereum.demo.common.Result.TransactionResult;
import org.web3j.crypto.CipherException;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

public interface ExampleService {
    StandardResult getVersion() throws IOException;
    StandardResult getAccounts() throws IOException;
    StandardResult getBalance(String address)throws IOException;
    StandardResult getNonce(String address)throws IOException;
    StandardResult getCoinbase() throws IOException;
    StandardResult sendTransferTransactionByGeth(String from,
                                           String password,
                                           String to,
                                           BigInteger value) throws IOException;
    StandardResult sendTransferTransaction(String walletpath,
                                           String from,
                                           String to,
                                           BigInteger value) throws Exception;
}
