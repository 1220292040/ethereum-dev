package com.ethereum.demo.service.serviceImplent;

import com.ethereum.demo.common.Result.StandardResult;
import com.ethereum.demo.common.Result.TransactionResult;
import com.ethereum.demo.common.utils.EthRpc;
import com.ethereum.demo.common.utils.EthRpcImpl;
import com.ethereum.demo.model.pojo.ChainConfiguration;
import com.ethereum.demo.service.serviceInterface.ExampleService;
import contract.NumTest;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;


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

    @Override
    public StandardResult testContract(String walletpath) throws Exception {
        Credentials credentials = ethRpc.getCredential(walletpath);
        ContractGasProvider contractGasProvider = new DefaultGasProvider();
        RawTransactionManager rawTransactionManager = new RawTransactionManager(ethRpc.getWeb3(),credentials, ChainConfiguration.CHAIN_ID);
        NumTest numTest = NumTest.deploy(ethRpc.getWeb3(),rawTransactionManager, contractGasProvider).send();
        //根据调用函数不同选择不同的gasprice和gaslimit
        //NumTest numTest = new NumTest(...)
//        numTest.setGasProvider(new DefaultGasProvider(){
//            //重写它的getGasPrice和getGasLimit方法
//            @Override
//            public BigInteger getGasPrice(String contractFunc){
//                switch (contractFunc){
//                    case NumTest.FUNC_GETNUM:return ...;
//                    case NumTest.FUNC_STORE:return ...;
//                    default:...;
//                }
//            }
//            //同理getGasLimit
//        });
        HashMap<String,Object> map = new HashMap<>();
        if(numTest.isValid()){
            map.put("contractAddress",numTest.getContractAddress());
            BigInteger var = numTest.getNum().send();
            System.out.println("old:" + var.toString());
            System.out.println();
            TransactionReceipt transactionReceipt = numTest.store(BigInteger.valueOf(123)).send();
//            String receipt = ethRpc.getTransactionReceipt(transactionReceipt.getTransactionHash());
            System.out.println(transactionReceipt.toString());
            BigInteger newvar = numTest.getNum().send();
            System.out.println("new:"+newvar.toString());
//            EventValues eventValues = numTest.processSomeEvent(transactionReceipt);
            return StandardResult.success(map);
        }else{
            return StandardResult.fail();
        }
    }
}
