package com.ethereum.demo.service.serviceInterface;

import com.ethereum.demo.common.Result.StandardResult;

import java.io.IOException;

public interface ExampleService {
    StandardResult getVersion() throws IOException;
    StandardResult getAccounts() throws IOException;
    StandardResult getBalance(String address)throws IOException;
    StandardResult getNonce(String address)throws IOException;
    StandardResult unLockAccount(String address,String password)throws IOException;

}
