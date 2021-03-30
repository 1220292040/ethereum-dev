package com.ethereum.demo.service.serviceInterface;

import com.ethereum.demo.common.Result.StandardResult;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;

import java.io.IOException;
import java.math.BigInteger;

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
    StandardResult testContract(String walletpath) throws Exception;
}
