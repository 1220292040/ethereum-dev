package com.ethereum.demo.common.utils;

import com.ethereum.demo.common.Result.TransactionResult;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Transaction;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * @author longbo
 * @date 2021.03.30
 * @apiNote 部分以太坊常用的API*
 * @TODO: 补文档
 * 之后的计划：
 * @TODO: sendContractTransaction(...)
 * @TODO: contractCallViewFunction(...)
 * @TODO: ens技术了解
 * @TODO: geth区块filters，订阅区块链的某些事件
 *
 */
public interface EthRpc {
    /**
     * 整体配置类
     */
    //返回当前连接的web3对象
    Web3j getWeb3();
    //返回当前连接的geth或者区块链信息
    String getVersion() throws IOException;
    //返回当前区块链的链ID
    String getChainNetId() throws IOException;
    //获取当前hashrate
    BigInteger getHashRate() throws IOException;
    //节点是否在挖矿
    Boolean isMinning() throws IOException;
    //启动geth挖矿
    Boolean minerStart(int threadCount) throws IOException;
    //结束geth挖矿
    Boolean minerStop() throws IOException;

    //--------------------------------------------------------------------//

    /**
     * 账户类
     */
    //创建新账户,返回账户地址
    String newAccount(String password) throws IOException;
    //解锁当前账户
    Boolean unLockAccount(String address,String password) throws IOException;
    //返回当前rpc节点上的账户信息
    List<String> getAccounts() throws IOException;
    //获取当前矿工信息
    String getCoinbase() throws IOException;
    //获取对应账户里的余额信息，单位为ether
    BigDecimal getBlance(String address) throws IOException;
    //获取对应账户的Nonce值，用于下一次交易
    BigInteger getNonce(String address) throws IOException;


    //--------------------------------------------------------------------//

    /**
     * 区块信息类
     */
    //获取最新区块高度
    BigInteger getBlockNumber() throws IOException;
    //根据哈希值获取区块
    EthBlock.Block getBlockByHash(String hash) throws IOException;
    //根据区块号获取区块信息
    EthBlock.Block getBlockByNumber(BigInteger number) throws IOException;

    //--------------------------------------------------------------------//

    /**
     * 钱包类
     */
    //根据给定的钱包文件路径获取钱包信息
    Credentials getCredential(String password,String walletpath) throws IOException, CipherException;
    Credentials getCredential(String walletpath) throws IOException, CipherException;
    //根据给定的钱包信息解出私钥
    BigInteger getPrivateKey(String password,String walletpath) throws IOException, CipherException;
    BigInteger getPrivateKey(String walletpath) throws IOException, CipherException;
    //根据给定的钱包信息解出公钥
    BigInteger getPublicKey(String password,String walletpath) throws IOException, CipherException;
    BigInteger getPublicKey(String walletpath) throws IOException, CipherException;
    //根据钱包导出地址
    String getCredentialAddress(String password,String walletpath) throws IOException, CipherException;
    String getCredentialAddress(String walletpath) throws IOException, CipherException;
    //--------------------------------------------------------------------//

    /**
     *交易类
     */
    //线上通过Geth发起交易
    TransactionResult sendTransferTransactionByGeth(String from,
                                                 String password,
                                                 String to,
                                                 BigInteger value) throws IOException;
    //通过私钥发送交易，离线交易
    TransactionResult sendTransferTransaction(String walletpath,
                                              String from,
                                              String to,
                                              BigInteger value) throws Exception;
    TransactionResult sendTransferTransaction(String walletpath,
                                              String password,
                                              String from,
                                              String to,
                                              BigInteger value) throws Exception;
    //通过交易获取交易Receipt信息
    String getTransactionReceipt(String txhash) throws Exception;
    //通过hash获取交易信息
    Transaction getTransactionByHash(String txhash) throws IOException;
    //发送带有data的rawTransaction，value单位为ether
    TransactionResult sendRawTransaction(String walletpath,String password,String from,String to,BigInteger value,String data) throws IOException, CipherException;
    //--------------------------------------------------------------------//

    /**
     * 合约类
     */
    String getContractCode(String address) throws IOException;
    //发送合约创建交易
    TransactionResult createContractTransaction(String walletpath,String password,String from,String data) throws IOException, CipherException;
    TransactionResult createContractTransaction(String walletpath,String password,String from,BigInteger value,String data) throws IOException, CipherException;
}
