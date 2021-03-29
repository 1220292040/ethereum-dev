package com.ethereum.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.web3j.protocol.Web3j;

import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;


@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args)  {
        SpringApplication.run(DemoApplication.class, args);
    }
}
