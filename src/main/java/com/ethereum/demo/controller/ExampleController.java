package com.ethereum.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.ethereum.demo.common.Result.StandardResult;
import com.ethereum.demo.service.serviceInterface.ExampleService;
import contract.NumTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigInteger;

@CrossOrigin
@RestController
@RequestMapping("/example")
public class ExampleController {
    @Autowired
    ExampleService exampleServicel;

    @GetMapping("/version")
    public StandardResult getVersion() throws IOException {
        return exampleServicel.getVersion();
    }
    @GetMapping("/accounts")
    public StandardResult getAccounts() throws IOException {
        return exampleServicel.getAccounts();
    }
    @GetMapping("/balance")
    public StandardResult getBalance(@RequestParam String address) throws IOException {
        return exampleServicel.getBalance(address);
    }
    @GetMapping("/nonce")
    public StandardResult getNonce(@RequestParam String address) throws IOException {
        return exampleServicel.getNonce(address);
    }
    @GetMapping("/coinbase")
    public StandardResult getCoinbase() throws IOException {
        return exampleServicel.getCoinbase();
    }
    @PostMapping("/transferByGeth")
    public StandardResult sendTransferTransactionByGeth(@RequestBody JSONObject params) throws IOException {
        String address = params.get("from").toString();
        String password = params.get("password").toString();
        String to = params.get("to").toString();
        BigInteger value = new BigInteger(params.get("value").toString());
        return exampleServicel.sendTransferTransactionByGeth(address,password,to,value);
    }
    @PostMapping("/transfer")
    public StandardResult sendTransferTransaction(@RequestBody JSONObject params) throws Exception {
        String walletpath = params.get("walletpath").toString();
        String from = params.get("from").toString();
        String to = params.get("to").toString();
        BigInteger value = new BigInteger(params.get("value").toString());
        return exampleServicel.sendTransferTransaction(walletpath,from,to,value);
    }
    @GetMapping("/contract")
    public StandardResult testContract() throws Exception {
        return exampleServicel.testContract("/Users/longbo/IdeaProjects/ethereum-dev/src/main/resources/wallet/UTC--2021-03-27T13-10-32.460046781Z--a8c340835aed60bd4b9736855effc95030850969");
    }
}
