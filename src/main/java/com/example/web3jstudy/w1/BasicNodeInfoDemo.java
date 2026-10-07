package com.example.web3jstudy.w1;

import java.io.IOException;
import java.math.BigInteger;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.Response;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthChainId;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

/**
 * W1.4：组合查询基础节点信息。
 *
 * 查询内容：
 * 1. Chain ID
 * 2. 最新区块号
 * 3. 节点客户端版本
 */
public class BasicNodeInfoDemo {

    public static void main(String[] args) throws IOException {

        String rpcUrl = "https://rpc.ankr.com/bsc/替换为你的_API_KEY";

        Web3j web3j = Web3j.build(new HttpService(rpcUrl));

        try {
            // 1. 查询 Chain ID。
            EthChainId chainIdResponse = web3j.ethChainId().send();
            checkResponse(chainIdResponse, "Chain ID");

            BigInteger chainId = chainIdResponse.getChainId();

            // BSC 主网应为 56。
            if (!BigInteger.valueOf(56).equals(chainId)) {
                throw new IllegalStateException(
                        "当前网络不是预期的 BSC 主网，Chain ID：" + chainId);
            }

            // 2. 查询最新区块号。
            EthBlockNumber blockNumberResponse =
                    web3j.ethBlockNumber().send();

            checkResponse(blockNumberResponse, "最新区块号");

            BigInteger blockNumber =
                    blockNumberResponse.getBlockNumber();

            // 3. 查询节点客户端版本。
            Web3ClientVersion clientVersionResponse =
                    web3j.web3ClientVersion().send();

            checkResponse(clientVersionResponse, "客户端版本");

            String clientVersion =
                    clientVersionResponse.getWeb3ClientVersion();

            System.out.println("Chain ID：" + chainId);
            System.out.println("最新区块号：" + blockNumber);
            System.out.println("节点客户端版本：" + clientVersion);

        } finally {
            web3j.shutdown();
        }
    }

    /**
     * 统一检查 JSON-RPC 是否返回错误。
     */
    private static void checkResponse(
            Response<?> response,
            String queryName) {

        if (response == null) {
            throw new IllegalStateException(
                    queryName + " 查询未收到有效响应。");
        }

        if (response.hasError()) {
            throw new IllegalStateException(
                    queryName + " 查询失败，错误码："
                            + response.getError().getCode()
                            + "，错误信息："
                            + response.getError().getMessage());
        }
    }
}
