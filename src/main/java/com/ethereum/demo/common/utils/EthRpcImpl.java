package com.ethereum.demo.common.utils;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class EthRpcImpl implements EthRpc{
    private Web3j web3j;
    private Admin admin;

    public EthRpcImpl(){
        this.web3j = Web3j.build(new HttpService("http://82.157.5.7:8545"));
        this.admin = Admin.build(new HttpService("http://82.157.5.7:8545"));
    }

    public EthRpcImpl(String url) {
        this.web3j = Web3j.build(new HttpService(url));
        this.admin = Admin.build(new HttpService(url));
    }
    /**
     *
     * @return 返回web3的信息
     * @throws IOException send函数出错
     */
    @Override
    public String getVersion() throws IOException {
        Web3ClientVersion web3ClientVersion = this.web3j.web3ClientVersion().send();
        return web3ClientVersion.getWeb3ClientVersion();
    }

    /**
     *
     * @return 返回以太坊账户列表
     * @throws IOException send函数出错
     */
    @Override
    public List<String> getAccounts() throws IOException {
        return this.web3j.ethAccounts().send().getAccounts();
    }

    /**
     *
     * @param address 需要查询的账户地址
     * @return 账户余额
     * @throws IOException send函数出错
     */
    @Override
    public BigDecimal getBlance(String address) throws IOException {
        BigInteger balance = this.web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send().getBalance();
        return Convert.fromWei(new BigDecimal(balance),Convert.Unit.ETHER);
    }

    /**
     *
     * @param address 账户地址
     * @return 获取nonce值，供交易使用
     * @throws IOException send函数出错
     */
    @Override
    public BigInteger getNonce(String address) throws IOException {
        return this.web3j.ethGetTransactionCount(address,DefaultBlockParameterName.PENDING).send().getTransactionCount();
    }

    /**
     *
     * @param address 待解锁账户
     * @param password 账户密码
     * @return 解锁结果 true为成功
     * @throws IOException send函数出错
     */
    public Boolean unLockAccount(String address,String password) throws IOException {
        return admin.personalUnlockAccount(address,password,BigInteger.valueOf(0)).send().accountUnlocked();
    }
}
