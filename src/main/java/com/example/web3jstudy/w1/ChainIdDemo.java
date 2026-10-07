package com.example.web3jstudy.w1;

import java.io.IOException;
import java.math.BigInteger;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthChainId;
import org.web3j.protocol.http.HttpService;

/**
 * W1.1：查询 Chain ID，并校验是否为 BSC 主网。
 */
public class ChainIdDemo {

    public static void main(String[] args) throws IOException {

        String rpcUrl =
                "https://rpc.ankr.com/bsc/替换为你的_API_KEY";

        BigInteger expectedChainId =
                BigInteger.valueOf(56);

        Web3j web3j =
                Web3j.build(new HttpService(rpcUrl));

        try {
            EthChainId response =
                    web3j.ethChainId().send();

            if (response == null) {
                throw new IllegalStateException(
                        "RPC 未返回有效响应。");
            }

            if (response.hasError()) {
                throw new IllegalStateException(
                        "查询 Chain ID 失败，错误码："
                                + response.getError().getCode()
                                + "，错误信息："
                                + response.getError().getMessage());
            }

            String rawChainId =
                    response.getResult();

            if (rawChainId == null
                    || rawChainId.isBlank()) {
                throw new IllegalStateException(
                        "RPC 响应缺少 Chain ID。");
            }

            BigInteger actualChainId =
                    response.getChainId();

            System.out.println(
                    "原始结果：" + rawChainId);
            System.out.println(
                    "节点返回的 Chain ID："
                            + actualChainId);
            System.out.println(
                    "预期的 Chain ID："
                            + expectedChainId);

            if (!expectedChainId.equals(actualChainId)) {
                throw new IllegalStateException(
                        "网络标识不匹配：预期 "
                                + expectedChainId
                                + "，实际 "
                                + actualChainId);
            }

            System.out.println(
                    "校验通过：节点返回的网络标识符合预期。");

        } finally {
            web3j.shutdown();
        }
    }
}
