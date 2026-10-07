package com.example.web3jstudy.w1;

import java.io.IOException;
import java.math.BigInteger;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.Response;
import org.web3j.protocol.core.methods.response.EthChainId;
import org.web3j.protocol.exceptions.ClientConnectionException;
import org.web3j.protocol.http.HttpService;

/**
 * W1.5：区分 HTTP / I/O、JSON-RPC 和业务校验错误。
 */
public class RpcErrorHandlingDemo {

    public static void main(String[] args) {

        String rpcUrl =
                "https://rpc.ankr.com/bsc/替换为你的_API_KEY";

        Web3j web3j =
                Web3j.build(new HttpService(rpcUrl));

        try {
            EthChainId response =
                    web3j.ethChainId().send();

            // 第一层：JSON-RPC 层错误。
            checkRpcError(response);

            // 第二层：结果完整性检查。
            String result = response.getResult();

            if (result == null || result.isBlank()) {
                throw new IllegalStateException(
                        "RPC 请求成功，但响应中没有 Chain ID result。");
            }

            BigInteger chainId =
                    response.getChainId();

            // 第三层：业务配置校验。
            if (!BigInteger.valueOf(56).equals(chainId)) {
                throw new IllegalStateException(
                        "网络配置错误：预期 BSC 主网 Chain ID=56，实际="
                                + chainId);
            }

            System.out.println("Chain ID：" + chainId);
            System.out.println("RPC 查询及业务校验均通过。");

        } catch (ClientConnectionException e) {

            // HTTP 层失败。
            System.err.println(
                    "HTTP / RPC 服务访问失败："
                            + e.getMessage());

        } catch (IOException e) {

            // 网络传输、响应读取或反序列化等 I/O 异常。
            System.err.println(
                    "RPC 通信失败："
                            + e.getMessage());

        } finally {
            web3j.shutdown();
        }
    }

    /**
     * 检查已经收到的 JSON-RPC 响应是否包含 error。
     */
    private static void checkRpcError(Response<?> response) {

        if (response == null) {
            throw new IllegalStateException(
                    "RPC 未返回有效响应对象。");
        }

        if (!response.hasError()) {
            return;
        }

        Response.Error error =
                response.getError();

        throw new IllegalStateException(
                "JSON-RPC 错误："
                        + "code=" + error.getCode()
                        + "，message=" + error.getMessage()
                        + "，data=" + error.getData());
    }
}
