package com.example.web3jstudy.w2;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.http.HttpService;

/**
 * W2.3：查询 BSC 最新区块。
 *
 * 本节重点：
 * 1. eth_getBlockByNumber
 * 2. 区块号 / 区块哈希 / 父区块哈希
 * 3. 时间戳
 * 4. 区块内交易数量
 * 5. fullTransactionObjects 参数
 */
public class BlockQueryDemo {

    public static void main(String[] args) throws IOException {

        String rpcUrl =
                "https://rpc.ankr.com/bsc/替换为你的_API_KEY";

        Web3j web3j =
                Web3j.build(new HttpService(rpcUrl));

        try {
            /*
             * false：
             * transactions 数组只返回交易哈希，
             * 不返回每笔交易的完整对象。
             */
            EthBlock response =
                    web3j.ethGetBlockByNumber(
                            DefaultBlockParameterName.LATEST,
                            false)
                            .send();

            if (response == null) {
                throw new IllegalStateException(
                        "RPC 未返回有效响应。");
            }

            if (response.hasError()) {
                throw new IllegalStateException(
                        "查询区块失败，错误码："
                                + response.getError().getCode()
                                + "，错误信息："
                                + response.getError().getMessage());
            }

            EthBlock.Block block =
                    response.getBlock();

            if (block == null) {
                throw new IllegalStateException(
                        "RPC 没有返回区块数据。");
            }

            /*
             * 区块 timestamp 是 Unix 时间戳，单位为秒。
             */
            Instant blockTime =
                    Instant.ofEpochSecond(
                            block.getTimestamp().longValueExact());

            List<EthBlock.TransactionResult> transactions =
                    block.getTransactions();

            int transactionCount =
                    transactions == null
                            ? 0
                            : transactions.size();

            System.out.println(
                    "区块号：" + block.getNumber());

            System.out.println(
                    "区块号原始值：" + block.getNumberRaw());

            System.out.println(
                    "区块 Hash：" + block.getHash());

            System.out.println(
                    "父区块 Hash：" + block.getParentHash());

            System.out.println(
                    "出块时间：" + blockTime);

            System.out.println(
                    "交易数量：" + transactionCount);

            System.out.println(
                    "Gas Limit：" + block.getGasLimit());

            System.out.println(
                    "Gas Used：" + block.getGasUsed());

            /*
             * fullTransactionObjects=false 时，
             * transactions 中保存的是交易 Hash。
             */
            if (transactionCount > 0) {
                String firstTransactionHash =
                        (String) transactions.get(0).get();

                System.out.println(
                        "第一笔交易 Hash："
                                + firstTransactionHash);
            }

        } finally {
            web3j.shutdown();
        }
    }
}
