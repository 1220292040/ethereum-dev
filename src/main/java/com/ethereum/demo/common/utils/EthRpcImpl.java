package com.ethereum.demo.common.utils;

import com.ethereum.demo.common.Result.TransactionResult;
import com.ethereum.demo.model.pojo.ChainConfiguration;
import org.web3j.crypto.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public class EthRpcImpl implements EthRpc{
    private Web3j web3j;
    private Admin admin;


    public EthRpcImpl(){
        this.web3j = Web3j.build(new HttpService(ChainConfiguration.RPC_URL));
        this.admin = Admin.build(new HttpService(ChainConfiguration.RPC_URL));
    }

    public EthRpcImpl(String url) {
        this.web3j = Web3j.build(new HttpService(url));
        this.admin = Admin.build(new HttpService(url));
    }

    public Web3j getWeb3j() {
        return web3j;
    }

    public Admin getAdmin() {
        return admin;
    }

    @Override
    public Web3j getWeb3() {
        return this.web3j;
    }

    @Override
    /**
     *
     * @return  返回连接的以太坊的链ID，string类型
     * @throws IOException 抛出io异常
     */
    public String getChainNetId() throws IOException {
        return web3j.netVersion().send().getNetVersion();
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
     * @return 获取当前矿工节点，供交易使用
     * @throws IOException send函数出错
     */
    @Override
    public String getCoinbase() throws IOException {
        return this.web3j.ethCoinbase().send().getAddress();
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

    /**
     *
     * @param from 源账户
     * @param password 源账户密码，解锁用
     * @param to 目的账户
     * @param value 转账金额，单位为ether
     * @return 交易哈希和event
     * @throws IOException io异常
     */
    @Override
    public TransactionResult sendTransferTransactionByGeth(String from,
                                                        String password,
                                                        String to,
                                                        BigInteger value) throws IOException {
        //首先解锁发起交易的账户
        Boolean personalUnlockAccount = admin.personalUnlockAccount(from,password,BigInteger.valueOf(0)).send().accountUnlocked();
        BigInteger etherValue = new BigDecimal(value).multiply(new BigDecimal("1e18")).toBigIntegerExact();
        if(personalUnlockAccount){
            //解锁成功，发送交易
            BigInteger gasPrice = this.web3j.ethGasPrice().send().getGasPrice();
            BigInteger nonce = this.web3j.ethGetTransactionCount(from,DefaultBlockParameterName.PENDING).send().getTransactionCount();
            BigInteger gasLimit = Transfer.GAS_LIMIT;
            Transaction transaction = Transaction.createEtherTransaction(from,
                    nonce,gasPrice,gasLimit,to, etherValue);
            org.web3j.protocol.core.methods.response.EthSendTransaction ethSendTransaction = web3j.ethSendTransaction(transaction).send();
            String txhash = ethSendTransaction.getTransactionHash();
            return new TransactionResult(txhash);
        }
        return null;
    }

    /**
     *
     * @param walletpath 钱包路径
     * @param from 源账户
     * @param to 目的账户
     * @param value 转账金额，单位为ether
     * @return
     * @throws IOException
     * @throws CipherException
     */
    @Override
    public TransactionResult sendTransferTransaction(String walletpath,String from, String to, BigInteger value) throws Exception {
        //解析钱包
        Credentials credentials = WalletUtils.loadCredentials("",walletpath);
        //获取nonce
        BigInteger nonce = this.web3j.ethGetTransactionCount(from,DefaultBlockParameterName.PENDING).send().getTransactionCount();
        BigInteger gasLimit = Transfer.GAS_LIMIT;
        BigInteger gasPrice = this.web3j.ethGasPrice().send().getGasPrice();
        BigInteger etherValue = new BigDecimal(value).multiply(new BigDecimal("1e18")).toBigIntegerExact();
        //创建rawTransaction对象
        RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce,gasPrice,gasLimit,to,etherValue);
        //对交易进行签名
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction,ChainConfiguration.CHAIN_ID,credentials);
        //编码
        String hexValue = Numeric.toHexString(signedMessage);
        //发送交易
        EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();
        String txhash = ethSendTransaction.getTransactionHash();
        if(txhash == null){
            System.out.println("txhash null");
            return null;
        }
        String txreceipt = getTransactionReceipt(txhash);
        System.out.println(txreceipt);
        return new TransactionResult(txhash,"",txreceipt);
    }

    private Optional<TransactionReceipt> sendTransactionReceiptRequest(String transactionHash)
            throws Exception {
        EthGetTransactionReceipt transactionReceipt =
                web3j.ethGetTransactionReceipt(transactionHash).sendAsync().get();

        return transactionReceipt.getTransactionReceipt();
    }

    private Optional<TransactionReceipt> getTransactionReceipt(
            String transactionHash, int sleepDuration, int attempts) throws Exception {
        Optional<TransactionReceipt> receiptOptional =
                sendTransactionReceiptRequest(transactionHash);
        for (int i = 0; i < attempts; i++) {
            if (!receiptOptional.isPresent()) {
                Thread.sleep(sleepDuration);
                receiptOptional = sendTransactionReceiptRequest(transactionHash);
            } else {
                break;
            }
        }
        return receiptOptional;
    }

    TransactionReceipt waitForTransactionReceipt(String transactionHash) throws Exception {

        Optional<TransactionReceipt> transactionReceiptOptional =
                getTransactionReceipt(transactionHash, ChainConfiguration.SLEEP_DURATION, ChainConfiguration.ATTEMPTS);

        if (!transactionReceiptOptional.isPresent()) {
            System.out.println("Transaction receipt not generated after " + ChainConfiguration.ATTEMPTS + " attempts");
        }

        return transactionReceiptOptional.get();
    }

    /**
     *
     * @param txhash 交易hash
     * @return  通过交易哈希拿到交易收据
     * @throws Exception 异常处理
     */
    @Override
    public String getTransactionReceipt(String txhash) throws Exception {
        return waitForTransactionReceipt(txhash).toString();
    }

    /**
     *
     * @param password 密码
     * @param walletpath 钱包文件的path
     * @return 钱包对象
     * @throws IOException 异常
     * @throws CipherException 异常
     */
    @Override
    public Credentials getCredential(String password,String walletpath) throws IOException, CipherException {
        return WalletUtils.loadCredentials(password,walletpath);
    }

    @Override
    public Credentials getCredential(String walletpath) throws IOException, CipherException {
        return WalletUtils.loadCredentials("",walletpath);
    }

    @Override
    public BigInteger getBlockNumber() throws IOException {
        return web3j.ethBlockNumber().send().getBlockNumber();
    }

    @Override
    public EthBlock.Block getBlockByHash(String hash) throws IOException {
        return web3j.ethGetBlockByHash(hash,true).send().getBlock();
    }

    @Override
    public EthBlock.Block getBlockByNumber(BigInteger number) throws IOException {
        DefaultBlockParameter defaultBlockParameter = new DefaultBlockParameterNumber(number);
        return web3j.ethGetBlockByNumber(defaultBlockParameter,true).send().getBlock();
    }

    @Override
    public org.web3j.protocol.core.methods.response.Transaction getTransactionByHash(String txhash) throws IOException {
        return web3j.ethGetTransactionByHash(txhash).send().getTransaction().get();
    }

    @Override
    public BigInteger getHashRate() throws IOException {
        return web3j.ethHashrate().send().getHashrate();
    }

    @Override
    public Boolean isMinning() throws IOException {
        return web3j.ethMining().send().isMining();
    }

    @Override
    public String getContractCode(String address) throws IOException {
        return web3j.ethGetCode(address,DefaultBlockParameterName.LATEST).send().getCode();
    }

    @Override
    public String newAccount(String password) throws IOException {
        return this.admin.personalNewAccount(password).send().getAccountId();
    }

    @Override
    public BigInteger getPrivateKey(String password, String walletpath) throws IOException, CipherException {
        Credentials credentials = WalletUtils.loadCredentials(password,walletpath);
        return credentials.getEcKeyPair().getPrivateKey();
    }

    @Override
    public BigInteger getPrivateKey(String walletpath) throws IOException, CipherException {
        Credentials credentials = WalletUtils.loadCredentials("",walletpath);
        return credentials.getEcKeyPair().getPrivateKey();
    }

    @Override
    public BigInteger getPublicKey(String password, String walletpath) throws IOException, CipherException {
        Credentials credentials = WalletUtils.loadCredentials(password,walletpath);
        return credentials.getEcKeyPair().getPublicKey();
    }

    @Override
    public BigInteger getPublicKey(String walletpath) throws IOException, CipherException {
        Credentials credentials = WalletUtils.loadCredentials("",walletpath);
        return credentials.getEcKeyPair().getPublicKey();
    }

    @Override
    public String getCredentialAddress(String password, String walletpath) throws IOException, CipherException {
        Credentials credentials = WalletUtils.loadCredentials(password,walletpath);
        return credentials.getAddress();
    }

    @Override
    public String getCredentialAddress(String walletpath) throws IOException, CipherException {
        Credentials credentials = WalletUtils.loadCredentials("",walletpath);
        return credentials.getAddress();
    }
}
