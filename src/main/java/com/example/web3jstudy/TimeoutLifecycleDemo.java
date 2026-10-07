package com.example.web3jstudy;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.Response;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.http.HttpService;

/**
 * W1.7：超时与客户端生命周期。
 *
 * 重点：
 * 1. 通过自定义 OkHttpClient 配置 HTTP 超时。
 * 2. 一个 Web3j 客户端复用多次 RPC 请求。
 * 3. 程序结束时统一调用 web3j.shutdown()。
 */
public class TimeoutLifecycleDemo {

    public static void main(String[] args) {

        String rpcUrl =
                "https://rpc.ankr.com/bsc/替换为你的_API_KEY";

        /*
         * 使用 Web3j 提供的 OkHttpClient Builder。
         *
         * connectTimeout：
         * 建立 TCP / TLS 连接的最大等待时间。
         *
         * readTimeout：
         * 连接建立后，等待服务端返回数据的读取超时。
         *
         * writeTimeout：
         * 向服务端写入请求数据的超时。
         *
         * callTimeout：
         * 一次完整 HTTP Call 从开始到结束的总时间上限。
         */
        OkHttpClient httpClient =
                HttpService.getOkHttpClientBuilder()
                        .connectTimeout(5, TimeUnit.SECONDS)
                        .readTimeout(15, TimeUnit.SECONDS)
                        .writeTimeout(15, TimeUnit.SECONDS)
                        .callTimeout(20, TimeUnit.SECONDS)
                        .build();

        HttpService httpService =
                new HttpService(rpcUrl, httpClient);

        /*
         * 客户端在本程序中只创建一次。
         * 后面的多次 RPC 查询都复用同一个 Web3j / HttpService / OkHttpClient。
         */
        Web3j web3j =
                Web3j.build(httpService);

        try {
            EthBlockNumber firstResponse =
                    web3j.ethBlockNumber().send();

            checkResponse(
                    firstResponse,
                    "第一次区块号查询");

            System.out.println(
                    "第一次区块号："
                            + firstResponse.getBlockNumber());

            EthBlockNumber secondResponse =
                    web3j.ethBlockNumber().send();

            checkResponse(
                    secondResponse,
                    "第二次区块号查询");

            System.out.println(
                    "第二次区块号："
                            + secondResponse.getBlockNumber());

            System.out.println(
                    "两次 RPC 查询复用了同一个 Web3j 客户端。");

        } catch (IOException e) {

            /*
             * 连接、读取、写入或整体 Call 超时时，
             * 都可能最终表现为 IOException 或其子类。
             */
            System.err.println(
                    "RPC 通信失败："
                            + e.getClass().getSimpleName()
                            + "："
                            + e.getMessage());

        } finally {

            /*
             * 结束本次 Web3j 客户端的生命周期。
             *
             * Web3j 5.0.3 中，shutdown() 会关闭其内部调度线程，
             * 并调用底层 Web3jService.close()。
             *
             * 这不是关闭 Ankr，也不是关闭 BSC 节点。
             */
            web3j.shutdown();

            System.out.println(
                    "Web3j 客户端已结束使用。");
        }
    }

    private static void checkResponse(
            Response<?> response,
            String queryName) {

        if (response == null) {
            throw new IllegalStateException(
                    queryName + " 未收到有效响应。");
        }

        if (response.hasError()) {
            throw new IllegalStateException(
                    queryName + " 失败，错误码："
                            + response.getError().getCode()
                            + "，错误信息："
                            + response.getError().getMessage());
        }
    }
}
