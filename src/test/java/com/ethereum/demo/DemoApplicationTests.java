package com.ethereum.demo;

import com.ethereum.demo.common.Result.TransactionResult;
import com.ethereum.demo.common.utils.EthRpc;
import com.ethereum.demo.common.utils.EthRpcImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.rlp.RlpEncoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;


@SpringBootTest
class DemoApplicationTests {
    EthRpc ethRpc = new EthRpcImpl();
    //单元测试整体配置类rpc
    @Test
    void Test1() throws IOException {
        System.out.println("version: "+ethRpc.getVersion());
        System.out.println("chainId: "+ethRpc.getChainNetId());
        System.out.println("hashrate:"+ethRpc.getHashRate().toString());
        if(ethRpc.isMinning()){
            System.out.println("minning ing~");
            ethRpc.minerStop();
            System.out.println(ethRpc.isMinning());
            ethRpc.minerStart(1);
            System.out.println(ethRpc.isMinning());
            ethRpc.minerStop();
            System.out.println(ethRpc.isMinning());
        }else{
            System.out.println("not minning");
            System.out.println(ethRpc.isMinning());
            ethRpc.minerStart(1);
            System.out.println(ethRpc.isMinning());
            ethRpc.minerStop();
            System.out.println(ethRpc.isMinning());
        }
    }

    //测试账户类
    @Test
    void Test2() throws IOException {
        String address = ethRpc.newAccount("");
        System.out.println("address: "+address);
        System.out.println(ethRpc.unLockAccount(address,""));
        System.out.println(ethRpc.getAccounts());
        System.out.println(ethRpc.getCoinbase());
        System.out.println(ethRpc.getBlance(ethRpc.getCoinbase()));
        System.out.println(ethRpc.getNonce(ethRpc.getCoinbase()));
    }

    //测试区块信息类
    @Test
    void Test3() throws IOException {
        System.out.println(ethRpc.getBlockNumber());
        EthBlock.Block blockByNumber = ethRpc.getBlockByNumber(BigInteger.valueOf(1));
        EthBlock.Block blockByhash = ethRpc.getBlockByHash("0x0a17f68a3e3008761996f4abcf08e2476a73f15e972d9abb9be8c448f2f5e71c");
        System.out.println("getBlockByNumber_extraData: ");
        System.out.print(blockByNumber.getExtraData());
        System.out.println();
        System.out.println("getBlockByHash_number: ");
        System.out.print(blockByhash.getNumber());
        System.out.println();
    }

    //测试钱包类
    @Test
    void Test4() throws IOException, CipherException {
        String walletpath = "/Users/longbo/IdeaProjects/ethereum-dev/src/main/resources/wallet/UTC--2021-03-27T13-10-32.460046781Z--a8c340835aed60bd4b9736855effc95030850969";
        Credentials credentials = ethRpc.getCredential("",walletpath);
        System.out.println("address: "+ethRpc.getCredentialAddress("",walletpath));
        System.out.println("privateKey: "+ethRpc.getPrivateKey("",walletpath));
        System.out.println("publicKey: "+ethRpc.getPublicKey("",walletpath));
    }

    @Test
    //测试交易类
    void Test5() throws Exception {
        String txhash1 = ethRpc.sendTransferTransactionByGeth(
                "0xa8c340835aed60bd4b9736855effc95030850969",
                "",
                "0xdce7798a20cdc5bd2b7445d71a5e2e857ffdcbc5",
                BigInteger.valueOf(10)
        ).getTxhash();
        String txhash2 = ethRpc.sendTransferTransaction(
                "/Users/longbo/IdeaProjects/ethereum-dev/src/main/resources/wallet/UTC--2021-03-27T13-10-32.460046781Z--a8c340835aed60bd4b9736855effc95030850969",
                "",
                "0xa8c340835aed60bd4b9736855effc95030850969",
                "0xdce7798a20cdc5bd2b7445d71a5e2e857ffdcbc5",
                BigInteger.valueOf(10)
        ).getTxhash();
        String receipt1 = ethRpc.getTransactionReceipt(txhash1);
        String txhash3 = ethRpc.sendRawTransaction("/Users/longbo/IdeaProjects/ethereum-dev/src/main/resources/wallet/UTC--2021-03-27T13-10-32.460046781Z--a8c340835aed60bd4b9736855effc95030850969",
                "",
                "0xa8c340835aed60bd4b9736855effc95030850969",
                "0xdce7798a20cdc5bd2b7445d71a5e2e857ffdcbc5",
                BigInteger.valueOf(10),
                "0x1234"
        ).getTxhash();
        Transaction tx = ethRpc.getTransactionByHash(txhash1);
        String txBlockHash = tx.getBlockHash();
        System.out.println("txhash1: "+txhash1);
        System.out.println("txhash2: "+txhash2);
        System.out.println("txhash3: "+txhash3);
        System.out.println("txBlockHash: "+txBlockHash);
        System.out.println("receipt1: "+receipt1);
    }


    @Test
    void Test6() throws Exception {
        TransactionResult transactionResult = ethRpc.createContractTransaction("/Users/longbo/IdeaProjects/ethereum-dev/src/main/resources/wallet/UTC--2021-03-27T13-10-32.460046781Z--a8c340835aed60bd4b9736855effc95030850969",
                "",
                "0xa8c340835aed60bd4b9736855effc95030850969",
                "6080604052348015600f57600080fd5b5060ac8061001e6000396000f3fe6080604052348015600f57600080fd5b506004361060325760003560e01c80636057361d14603757806367e0badb146053575b600080fd5b605160048036036020811015604b57600080fd5b5035606b565b005b60596070565b60408051918252519081900360200190f35b600055565b6000549056fea26469706673582212202cc02e80d916b410d4981342086724b9a4c7756b64b6b715ed91788fe68c079764736f6c63430007050033"
        );
        System.out.println("txhash: "+transactionResult.getTxhash());
        System.out.println("contractAddress: "+transactionResult.getEventmsg());
        System.out.println("Receipt: "+transactionResult.getReceipt());
        System.out.println("contractCode: "+ethRpc.getContractCode(transactionResult.getEventmsg()));
        //测试合约调用
        Function function1 = new Function("getNum",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        BigInteger _num = BigInteger.valueOf(6666);
        Function function2 = new Function(
                "store",
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_num)),
                Collections.<TypeReference<?>>emptyList());
        String txhash = ethRpc.sendContractTransaction("0xa8c340835aed60bd4b9736855effc95030850969",function2,transactionResult.getEventmsg(),BigInteger.valueOf(0));
        System.out.println(txhash);
        Function function = new Function("getNum",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    }
    //测试viewcall方法
    @Test
    void Test7() throws IOException, ExecutionException, InterruptedException {
        Function function = new Function("getNum",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<Type> list = ethRpc.contractCallViewFunction("0xa8c340835aed60bd4b9736855effc95030850969",function,"0x534f4a9eaa9df38ef0ca220f72e021fbdb17b57f");
        for(Type t:list){
            System.out.println("result: "+t.getValue().toString());
        }
    }
    @Test
    void testRLP(){
        byte b = 0x12;
        byte[] bb = {0x1,0x2,0x3};
        System.out.println("test RLP_String");
        String s1 = DatatypeConverter.printHexBinary(RlpEncoder.encode(RlpString.create("hello web3j")));
        System.out.println(s1);
        String s2 = DatatypeConverter.printHexBinary(RlpEncoder.encode(RlpString.create(12345L)));
        System.out.println(s2);
        String s3 = DatatypeConverter.printHexBinary(RlpEncoder.encode(RlpString.create(b)));
        System.out.println(s3);
        String s4 = DatatypeConverter.printHexBinary(RlpEncoder.encode(RlpString.create(bb)));
        System.out.println(s4);
        String s5 = DatatypeConverter.printHexBinary(RlpEncoder.encode(RlpString.create(BigInteger.valueOf(100))));
        System.out.println(s5);
        System.out.println("test RLP_List");
        String l1 = DatatypeConverter.printHexBinary(RlpEncoder.encode(new RlpList(RlpString.create("hello"),new RlpList(RlpString.create(4)))));
        System.out.println(l1);
    }
}
