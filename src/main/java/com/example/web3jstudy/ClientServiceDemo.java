package com.example.web3jstudy;

import java.io.IOException;
import java.math.BigInteger;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthChainId;
import org.web3j.protocol.http.HttpService;

/**
 * W1.2：理解 Web3j 与 HttpService 的分工，
 * 并复用同一个客户端执行多次独立 RPC 查询。
 */
public class ClientServiceDemo {

    public static void main(String[] args) throws IOException {

        String rpcUrl =
                "https://rpc.ankr.com/bsc/替换为你的_API_KEY";

        HttpService httpService =
                new HttpService(rpcUrl);

        Web3j web3j =
                Web3j.build(httpService);

        try {
            EthChainId chainResponse =
                    web3j.ethChainId().send();

            if (chainResponse == null
                    || chainResponse.hasError()) {
                throw new IllegalStateException(
                        "Chain ID 查询失败。");
            }

            BigInteger chainId =
                    chainResponse.getChainId();

            if (!BigInteger.valueOf(56).equals(chainId)) {
                throw new IllegalStateException(
                        "当前网络不是 BSC 主网，Chain ID："
                                + chainId);
            }

            System.out.println(
                    "Chain ID：" + chainId);

            EthBlockNumber blockResponse =
                    web3j.ethBlockNumber().send();

            if (blockResponse == null
                    || blockResponse.hasError()) {
                throw new IllegalStateException(
                        "区块号查询失败。");
            }

            System.out.println(
                    "最新区块号："
                            + blockResponse.getBlockNumber());

        } finally {
            web3j.shutdown();
        }
    }
}
