# Web3j Learning

面向 BNB Smart Chain（BSC）的 Java / Web3j 学习仓库。

# 学习环境

- JDK 21
- Maven
- Web3j 5.0.3
- BNB Smart Chain（BSC）
- Ankr HTTPS RPC
- IntelliJ IDEA

# 教程目录

| 课节 | 教程 | 示例代码 | 状态 |
|---|---|---|---|
| W0 | [最小工程与第一次调用](docs/W0.md) | [FirstRpcDemo.java](src/main/java/com/example/web3jstudy/FirstRpcDemo.java) | ✅ |
| W1.1 | [查询 Chain ID](docs/W1.1.md) | [ChainIdDemo.java](src/main/java/com/example/web3jstudy/ChainIdDemo.java) | ✅ |
| W1.2 | [客户端与通信组件](docs/W1.2.md) | [ClientServiceDemo.java](src/main/java/com/example/web3jstudy/ClientServiceDemo.java) | ✅ |
| W1.3 | [请求对象与响应对象](docs/W1.3.md) | [RequestResponseDemo.java](src/main/java/com/example/web3jstudy/RequestResponseDemo.java) | ✅ |
| W1.4 | [组合基本查询](docs/W1.4.md) | [BasicNodeInfoDemo.java](src/main/java/com/example/web3jstudy/BasicNodeInfoDemo.java) | ✅ |
| W1.5 | [错误处理](docs/W1.5.md) | [RpcErrorHandlingDemo.java](src/main/java/com/example/web3jstudy/RpcErrorHandlingDemo.java) | ✅ |
| W1.6 | [同步与异步调用](docs/W1.6.md) | [SyncAsyncDemo.java](src/main/java/com/example/web3jstudy/SyncAsyncDemo.java) | ✅ |
| W1.7 | [超时与客户端生命周期](docs/W1.7.md) | [TimeoutLifecycleDemo.java](src/main/java/com/example/web3jstudy/TimeoutLifecycleDemo.java) | 🔄 |

# 仓库约定

每个课节：

- 教程单独保存在 `docs/` 下；
- Java 示例独立可运行，不依赖上一节的 Java 类；
- 知识点可以承接上一节，不重复基础内容；
- RPC 地址仅保留占位符；
- 真实 API Key、钱包私钥和助记词不得提交到仓库。

# 当前进度

```text
W0    ✅
W1.1  ✅
W1.2  ✅
W1.3  ✅
W1.4  ✅
W1.5  ✅
W1.6  ✅
W1.7  🔄 学习中
```
