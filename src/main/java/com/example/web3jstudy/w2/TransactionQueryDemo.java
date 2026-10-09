package com.example.web3jstudy.w2;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Response;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

/**
 * W2.4：根据交易 Hash 查询完整交易对象。
 */
public class TransactionQueryDemo {

    private static final int MAX_LOOKBACK_BLOCKS = 20;

    public static void main(String[] args) throws IOException {

        String rpcUrl =
                "https://rpc.ankr.com/bsc/替换为你的_API_KEY";

        Web3j web3j =
                Web3j.build(new HttpService(rpcUrl));

        try {
            /*
             * 为了让示例独立运行，先从最近区块中找到一笔交易 Hash。
             * 这里只把 W2.3 的区块查询作为准备步骤，
             * 本节真正学习的是 eth_getTransactionByHash。
             */
            String transactionHash =
                    findRecentTransactionHash(web3j);

            EthTransaction response =
                    web3j.ethGetTransactionByHash(
                            transactionHash)
                            .send();

            checkResponse(
                    response,
                    "交易查询");

            Transaction transaction =
                    response.getTransaction()
                            .orElseThrow(
                                    () -> new IllegalStateException(
                                            "RPC 未找到交易："
                                                    + transactionHash));

            BigInteger valueWei =
                    transaction.getValue();

            BigDecimal valueBnb =
                    Convert.fromWei(
                            new BigDecimal(valueWei),
                            Convert.Unit.ETHER);

            System.out.println(
                    "交易 Hash："
                            + transaction.getHash());

            System.out.println(
                    "所属区块号："
                            + transaction.getBlockNumber());

            System.out.println(
                    "所属区块 Hash："
                            + transaction.getBlockHash());

            System.out.println(
                    "区块内交易索引："
                            + transaction.getTransactionIndex());

            System.out.println(
                    "Chain ID："
                            + transaction.getChainId());

            System.out.println(
                    "From："
                            + transaction.getFrom());

            System.out.println(
                    "To："
                            + (transaction.getTo() == null
                            ? "<合约创建交易，无 to 地址>"
                            : transaction.getTo()));

            System.out.println(
                    "Nonce："
                            + transaction.getNonce());

            System.out.println(
                    "Value（Wei）："
                            + valueWei);

            System.out.println(
                    "Value（BNB）："
                            + valueBnb.toPlainString());

            System.out.println(
                    "Gas Limit："
                            + transaction.getGas());

            if (transaction.getGasPriceRaw() != null) {
                BigInteger gasPriceWei =
                        transaction.getGasPrice();

                BigDecimal gasPriceGwei =
                        Convert.fromWei(
                                new BigDecimal(gasPriceWei),
                                Convert.Unit.GWEI);

                System.out.println(
                        "Gas Price（Wei）："
                                + gasPriceWei);

                System.out.println(
                        "Gas Price（Gwei）："
                                + gasPriceGwei.toPlainString());
            }

            System.out.println(
                    "交易类型："
                            + transaction.getType());

            String input =
                    transaction.getInput();

            int inputBytes =
                    input == null || input.length() <= 2
                            ? 0
                            : (input.length() - 2) / 2;

            System.out.println(
                    "Input 字节数："
                            + inputBytes);

            System.out.println(
                    "Input："
                            + summarizeInput(input));

        } finally {
            web3j.shutdown();
        }
    }

    /**
     * 从 latest 开始向前查找最近一笔已经进入区块的交易。
     */
    private static String findRecentTransactionHash(
            Web3j web3j) throws IOException {

        EthBlock latestResponse =
                web3j.ethGetBlockByNumber(
                        DefaultBlockParameterName.LATEST,
                        false)
                        .send();

        checkResponse(
                latestResponse,
                "最新区块查询");

        EthBlock.Block latestBlock =
                latestResponse.getBlock();

        if (latestBlock == null) {
            throw new IllegalStateException(
                    "RPC 没有返回最新区块。");
        }

        BigInteger latestBlockNumber =
                latestBlock.getNumber();

        for (int i = 0;
             i < MAX_LOOKBACK_BLOCKS;
             i++) {

            BigInteger blockNumber =
                    latestBlockNumber.subtract(
                            BigInteger.valueOf(i));

            EthBlock.Block block;

            if (i == 0) {
                block = latestBlock;
            } else {
                EthBlock blockResponse =
                        web3j.ethGetBlockByNumber(
                                DefaultBlockParameter.valueOf(
                                        blockNumber),
                                false)
                                .send();

                checkResponse(
                        blockResponse,
                        "区块 " + blockNumber + " 查询");

                block =
                        blockResponse.getBlock();
            }

            if (block == null) {
                continue;
            }

            List<EthBlock.TransactionResult> transactions =
                    block.getTransactions();

            if (transactions == null
                    || transactions.isEmpty()) {
                continue;
            }

            Object firstTransaction =
                    transactions.get(0).get();

            if (firstTransaction instanceof String hash
                    && !hash.isBlank()) {

                System.out.println(
                        "找到交易的区块号："
                                + block.getNumber());

                return hash;
            }
        }

        throw new IllegalStateException(
                "最近 "
                        + MAX_LOOKBACK_BLOCKS
                        + " 个区块没有找到交易，请重新运行或增大查找范围。");
    }

    private static void checkResponse(
            Response<?> response,
            String queryName) {

        if (response == null) {
            throw new IllegalStateException(
                    queryName
                            + " 未收到有效响应。");
        }

        if (response.hasError()) {
            throw new IllegalStateException(
                    queryName
                            + " 失败，错误码："
                            + response.getError().getCode()
                            + "，错误信息："
                            + response.getError().getMessage());
        }
    }

    private static String summarizeInput(
            String input) {

        if (input == null || input.isBlank()) {
            return "<null>";
        }

        int maxLength = 66;

        if (input.length() <= maxLength) {
            return input;
        }

        return input.substring(
                0,
                maxLength)
                + "...";
    }
}
