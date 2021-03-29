package com.ethereum.demo.common.utils;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public interface EthRpc {
    String getVersion() throws IOException;
    List<String> getAccounts() throws IOException;
    BigDecimal getBlance(String address) throws IOException;
    BigInteger getNonce(String address) throws IOException;
    Boolean unLockAccount(String address,String password) throws IOException;
}
