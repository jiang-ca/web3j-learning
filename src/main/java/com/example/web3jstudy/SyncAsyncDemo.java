package com.example.web3jstudy;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.Response;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthChainId;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

/**
 * W1.6：同步调用与异步调用。
 */
public class SyncAsyncDemo {

    public static void main(String[] args) {

        String rpcUrl =
                "https://rpc.ankr.com/bsc/替换为你的_API_KEY";

        Web3j web3j =
                Web3j.build(new HttpService(rpcUrl));

        try {
            /*
             * =========================
             * 1. 同步调用
             * =========================
             */
            System.out.println("开始同步查询区块号");

            EthBlockNumber blockResponse =
                    web3j.ethBlockNumber().send();

            checkResponse(blockResponse, "区块号");

            BigInteger blockNumber =
                    blockResponse.getBlockNumber();

            System.out.println(
                    "同步查询完成，区块号：" + blockNumber);

            /*
             * =========================
             * 2. 异步调用
             * =========================
             */
            System.out.println();
            System.out.println("开始异步查询");

            CompletableFuture<EthChainId> chainIdFuture =
                    web3j.ethChainId().sendAsync();

            CompletableFuture<Web3ClientVersion> clientVersionFuture =
                    web3j.web3ClientVersion().sendAsync();

            System.out.println(
                    "两个异步 RPC 已提交，main 线程继续执行");

            CompletableFuture.allOf(
                    chainIdFuture,
                    clientVersionFuture
            ).join();

            EthChainId chainIdResponse =
                    chainIdFuture.join();

            Web3ClientVersion clientVersionResponse =
                    clientVersionFuture.join();

            checkResponse(
                    chainIdResponse,
                    "Chain ID");

            checkResponse(
                    clientVersionResponse,
                    "客户端版本");

            System.out.println(
                    "Chain ID："
                            + chainIdResponse.getChainId());

            System.out.println(
                    "客户端版本："
                            + clientVersionResponse
                            .getWeb3ClientVersion());

        } catch (IOException e) {

            System.err.println(
                    "同步 RPC 调用失败："
                            + e.getMessage());

        } catch (CompletionException e) {

            Throwable cause = e.getCause();

            System.err.println(
                    "异步 RPC 调用失败："
                            + (cause == null
                            ? e.getMessage()
                            : cause.getMessage()));

        } finally {
            web3j.shutdown();
        }
    }

    /**
     * 检查 JSON-RPC 响应。
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
