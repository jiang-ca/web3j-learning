# Web3j 学习

面向 BNB Smart Chain（BSC）的 Java / Web3j 学习示例。

# W1.3｜请求对象与响应对象

当前提交包含 W1.3 的独立示例 `RequestResponseDemo`，展示请求 JSON、原始响应正文、请求与响应 ID，以及区块号结果解码。不依赖其他小节的源码。

环境沿用课程基线：JDK 21、Maven、Web3j 5.0.3。

在 IDEA 中打开本项目并加载 Maven 依赖，在 `src/main/java/com/example/web3jstudy/RequestResponseDemo.java` 中把 `rpcUrl` 替换为自己的 Ankr BSC 主网 HTTPS Endpoint，直接运行 `main`。

仓库只保留 RPC 地址占位符。真实 API Key 仅在本地填写，不要提交到仓库或包含在公开截图中；`.gitignore` 不会隐藏已经跟踪的 Java 源码中的凭证。

# 输出示例

下面的 ID 和区块号仅用于说明格式，不是实时查询记录：

```text
请求 JSON：{"jsonrpc":"2.0","method":"eth_blockNumber","params":[],"id":0}
预期响应类型：EthBlockNumber
响应 JSON：{"jsonrpc":"2.0","id":0,"result":"0x782d88a"}
响应 ID：0
result：0x782d88a
区块号：126015626
```

程序只执行一次只读 RPC 查询，不签名或广播链上交易。查看请求 JSON 不会发送请求，读取响应字段不会再次访问节点。本例没有执行 Chain ID 校验，目标网络由填写的 Endpoint 决定。

# 验收状态

W1.3 待实操验收。代码入库不代表本节验收通过。
