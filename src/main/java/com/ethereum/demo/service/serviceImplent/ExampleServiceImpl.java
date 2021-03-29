package com.ethereum.demo.service.serviceImplent;

import com.ethereum.demo.common.Result.StandardResult;
import com.ethereum.demo.common.Result.TransactionResult;
import com.ethereum.demo.common.utils.EthRpc;
import com.ethereum.demo.common.utils.EthRpcImpl;
import com.ethereum.demo.service.serviceInterface.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Hash;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ExampleServiceImpl implements ExampleService {

   EthRpc ethRpc = new EthRpcImpl();


    @Override
    public StandardResult getVersion() throws IOException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("version",ethRpc.getVersion());
        return StandardResult.success(map);
    }

    @Override
    public StandardResult getAccounts() throws IOException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("accounts",ethRpc.getAccounts());
        return StandardResult.success(map);
    }

    @Override
    public StandardResult getBalance(String address) throws IOException {
        HashMap<String,Object> map = new HashMap<>();
        map.put(address,ethRpc.getBlance(address));
        return StandardResult.success(map);
    }

    @Override
    public StandardResult getNonce(String address) throws IOException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("address",address);
        map.put("nonce",ethRpc.getNonce(address));
        return StandardResult.success(map);
    }

    @Override
    public StandardResult getCoinbase() throws IOException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("coinbase",ethRpc.getCoinbase());
        return StandardResult.success(map);
    }

    @Override
    public StandardResult sendTransferTransactionByGeth(String from, String password, String to, BigInteger value) throws IOException {
        TransactionResult transactionResult = ethRpc.sendTransferTransactionByGeth(from,password,to,value);
        if(transactionResult != null){
            return StandardResult.success(transactionResult);
        }else{
            return StandardResult.fail(transactionResult);
        }
    }

    @Override
    public StandardResult sendTransferTransaction(String walletpath, String from, String to, BigInteger value) throws Exception {
        TransactionResult transactionResult = ethRpc.sendTransferTransaction(walletpath,from,to,value);
        if(transactionResult != null){
            return StandardResult.success(transactionResult);
        }else{
            return StandardResult.fail(transactionResult);
        }
    }
}
