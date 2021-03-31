# ethereum-dev
web3j是一个轻量级的java类库，用于处理以太坊智能合约及与以太坊网络上节点进行集成。

本项目采用Springboot框架+web3j，对一些常用的以太坊私链操作进行了封装，希望能提供一套比较完备的常用的以太坊开发RPC，
以供后续开发。

使用web3j需要在pom.xml文件中添加如下依赖
```xml
<dependency>
     <groupId>org.web3j</groupId>
     <artifactId>core</artifactId>
     <version>4.5.0</version>
 </dependency>
 <dependency>
     <groupId>org.web3j</groupId>
     <artifactId>geth</artifactId>
     <version>4.5.0</version>
 </dependency>
```
其中版本号很重要，4.6.0及以上在现有的okhttp版本下会发生request冲突，这个bug一直没被解决，是因为4.6.0版本以上的web3j库适配的是okhttp4
而低版本的web3j在sendTransaction方法中对于chainid的处理有问题，经实验发现4.5.0是目前比较合适用来做开发的版本

对于智能合约，需要添加web3j-maven-plugin插件以便于在idea中直接编译.sol文件产生java类，部署调用都更加方便。
```xml
<plugin>
    <groupId>org.web3j</groupId>
    <artifactId>web3j-maven-plugin</artifactId>
    <version>4.6.5</version>
    <configuration>
        <packageName>contract</packageName>
        <sourceDestination>src/main/java/</sourceDestination>
        <nativeJavaType>true</nativeJavaType>
        <outputFormat>java,bin</outputFormat>
        <soliditySourceFiles>
            <directory>src/main/resources</directory>
            <includes>
                <include>**/*.sol</include>
            </includes>
        </soliditySourceFiles>
        <outputDirectory>
            <java>src/main/java/</java>
            <bin>src/main/java/</bin>
            <abi>src/main/java/</abi>
        </outputDirectory>
    </configuration>
</plugin>
```
封装的RPC主要在src/main/java/com.ethereum.demo/common/utils的EthRpc接口文件里，并且给出了这些接口的实现

src/main/java/com.ethereum.demo/common中主要定义了返回报文的字段以及错误成功码之类的信息

controller-service是标准的MVC模式，里面有个example文件展示了如何去使用springboot搭建一个方便简单的后端服务器

src/main/java/com.ethereum.demo/model/pojo/下的ChainConfiguration文件保存了全局变量，定义了连接的节点信息，链的信息等全局变量。

src/main/resource下的contract文件夹里面存放的是.sol文件，合约的源文件可以放在这里，然后使用插件编译

src/main/wallet文件夹保存的是钱包信息

最后在src/test中有一个测试类，里面对EthRpc各个接口都分类进行了单元测试，但是偷懒把结果都是用sout的方式输出了hhh

希望这个能越做越大～

by long