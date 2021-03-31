package com.ethereum.demo;

import com.ethereum.demo.common.utils.EthRpc;
import com.ethereum.demo.common.utils.EthRpcImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.web3j.crypto.CipherException;
import org.web3j.rlp.RlpEncoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.math.BigInteger;


@SpringBootTest
class DemoApplicationTests {
    EthRpc ethRpc = new EthRpcImpl();

    @Test
    void contextLoads() {


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

    @Test
    void testSendRawTransaction() throws IOException, CipherException {
        ethRpc.sendRawTransaction("/Users/longbo/IdeaProjects/ethereum-dev/src/main/resources/wallet/UTC--2021-03-27T13-10-32.460046781Z--a8c340835aed60bd4b9736855effc95030850969",
                "",
                "0xa8c340835aed60bd4b9736855effc95030850969",
                "0xdce7798a20cdc5bd2b7445d71a5e2e857ffdcbc5",
                BigInteger.valueOf(10),
                "0x1234").getReceipt();
    }

    @Test
    void testcreateContractTransaction() throws IOException, CipherException {
        ethRpc.createContractTransaction("/Users/longbo/IdeaProjects/ethereum-dev/src/main/resources/wallet/UTC--2021-03-27T13-10-32.460046781Z--a8c340835aed60bd4b9736855effc95030850969",
                "",
                "0xa8c340835aed60bd4b9736855effc95030850969",
                "6080604052348015600f57600080fd5b5060ac8061001e6000396000f3fe6080604052348015600f57600080fd5b506004361060325760003560e01c80636057361d14603757806367e0badb146053575b600080fd5b605160048036036020811015604b57600080fd5b5035606b565b005b60596070565b60408051918252519081900360200190f35b600055565b6000549056fea26469706673582212202cc02e80d916b410d4981342086724b9a4c7756b64b6b715ed91788fe68c079764736f6c63430007050033"
        ).getReceipt();
    }
}
