package com.ethereum.demo.service.serviceImplent;

import com.ethereum.demo.common.Result.StandardResult;
import com.ethereum.demo.common.utils.EthRpc;
import com.ethereum.demo.common.utils.EthRpcImpl;
import com.ethereum.demo.service.serviceInterface.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    public StandardResult unLockAccount(String address ,String password) throws IOException {
        return null;
    }


}
