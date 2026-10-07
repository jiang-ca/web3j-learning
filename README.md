# Web3j 学习

面向 BNB Smart Chain（BSC）的 Java / Web3j 学习示例。

当前课程基线：

- JDK 21
- Maven
- Web3j 5.0.3
- BNB Smart Chain（BSC）
- Ankr HTTPS RPC
- IDEA 直接运行示例类

仓库中的示例彼此独立，不依赖上一小节的 Java 类或工具方法。RPC 地址在源码中只保留占位符，真实 API Key 仅在本地填写。

# 当前示例

## W1.3｜请求对象与响应对象

文件：

```text
src/main/java/com/example/web3jstudy/RequestResponseDemo.java
```

学习内容：

- `Request<?, EthBlockNumber>`
- JSON-RPC 请求字段
- 请求与响应 ID
- 原始响应 JSON
- `result` 与解码后区块号

状态：✅ 已验收

## W1.4｜组合基本查询

文件：

```text
src/main/java/com/example/web3jstudy/BasicNodeInfoDemo.java
```

学习内容：

- `eth_chainId`
- `eth_blockNumber`
- `web3_clientVersion`
- 同一个 Web3j 客户端执行多次独立 RPC 查询

状态：✅ 已验收

## W1.5｜错误处理

文件：

```text
src/main/java/com/example/web3jstudy/RpcErrorHandlingDemo.java
```

学习内容：

- HTTP / RPC 服务访问失败
- `IOException`
- JSON-RPC `error`
- 响应结果完整性检查
- 业务配置校验

状态：✅ 已验收

## W1.6｜同步与异步调用

文件：

```text
src/main/java/com/example/web3jstudy/SyncAsyncDemo.java
```

学习内容：

- `.send()` 同步调用
- `.sendAsync()` 异步调用
- `CompletableFuture`
- `CompletableFuture.allOf(...).join()`
- 异步异常的 `CompletionException`
- 异步调用与 JSON-RPC Batch 的区别

状态：🔄 待验收

# 安全说明

源码中的 RPC 地址保持：

```text
https://rpc.ankr.com/bsc/替换为你的_API_KEY
```

真实 API Key 不提交到公开仓库。

本仓库当前示例只涉及只读 RPC 查询，不包含钱包私钥、助记词或真实资产交易。
