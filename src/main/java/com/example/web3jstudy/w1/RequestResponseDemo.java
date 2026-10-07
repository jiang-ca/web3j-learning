package com.example.web3jstudy.w1;

import java.io.IOException;

import org.web3j.protocol.ObjectMapperFactory;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.http.HttpService;

/**
 * W1.3：查看 RPC 请求对象、原始响应，以及解码后的查询结果。
 *
 * 本例独立运行，不依赖其他小节的类。RPC 地址仅在本地替换，
 * 不要把包含真实 API Key 的代码提交到仓库。
 */
public class RequestResponseDemo {

    public static void main(String[] args) throws IOException {

        String rpcUrl = "https://rpc.ankr.com/bsc/替换为你的_API_KEY";

        // true：保留原始响应正文，供 getRawResponse() 读取。
        HttpService httpService = new HttpService(rpcUrl, true);
        Web3j web3j = Web3j.build(httpService);

        try {
            // 构造请求，暂不发送。
            Request<?, EthBlockNumber> request = web3j.ethBlockNumber();

            // 使用 Web3j 的 JSON 工具查看请求内容。
            // 此处只是本地序列化，不会访问节点。
            String requestJson = ObjectMapperFactory.getObjectMapper()
                    .writeValueAsString(request);

            System.out.println("请求 JSON：" + requestJson);
            System.out.println(
                    "预期响应类型：" + request.getResponseType().getSimpleName());

            // 发送请求，获得指定类型的响应对象。
            EthBlockNumber response = request.send();

            if (response == null) {
                throw new IllegalStateException("RPC 未返回有效响应。");
            }

            // 查看收到的 JSON 正文，而不是重新拼装一个响应示例。
            System.out.println("响应 JSON：" + response.getRawResponse());

            if (response.hasError()) {
                throw new IllegalStateException(
                        "RPC 查询失败，错误码："
                                + response.getError().getCode()
                                + "，错误信息："
                                + response.getError().getMessage());
            }

            // 正常响应的 ID 应与本次请求一致。
            if (request.getId() != response.getId()) {
                throw new IllegalStateException(
                        "请求与响应 ID 不匹配：请求 " + request.getId()
                                + "，响应 " + response.getId());
            }

            String rawResult = response.getResult();

            if (rawResult == null || rawResult.isBlank()) {
                throw new IllegalStateException("响应中缺少区块号。");
            }

            System.out.println("响应 ID：" + response.getId());
            System.out.println("result：" + rawResult);
            System.out.println("区块号：" + response.getBlockNumber());

        } finally {
            // 结束本次客户端使用；详细资源管理在后续课程展开。
            web3j.shutdown();
        }
    }
}
