package com.example.web3jstudy.w0;

import java.io.IOException;
import java.math.BigInteger;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.http.HttpService;

/**
 * W0.1：第一次通过 Web3j 查询 BSC 最新区块号。
 */
public class FirstRpcDemo {

    public static void main(String[] args) throws IOException {

        String rpcUrl =
                "https://rpc.ankr.com/bsc/替换为你的_API_KEY";

        Web3j web3j =
                Web3j.build(new HttpService(rpcUrl));

        try {
            EthBlockNumber response =
                    web3j.ethBlockNumber().send();

            if (response.hasError()) {
                throw new IllegalStateException(
                        "查询失败："
                                + response.getError().getMessage());
            }

            String rawResult = response.getResult();
            BigInteger blockNumber =
                    response.getBlockNumber();

            System.out.println(
                    "原始结果：" + rawResult);
            System.out.println(
                    "最新区块号：" + blockNumber);

        } finally {
            web3j.shutdown();
        }
    }
}
