package com.ethereum.demo.controller;

import com.ethereum.demo.common.Result.StandardResult;
import com.ethereum.demo.service.serviceInterface.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
}
