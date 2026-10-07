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
| W1.7 | [超时与客户端生命周期](docs/W1.7.md) | [TimeoutLifecycleDemo.java](src/main/java/com/example/web3jstudy/TimeoutLifecycleDemo.java) | ✅ |

# 仓库约定

每个课节：

- 教程单独保存在 `docs/` 下；
- Java 示例独立可运行，不依赖上一节的 Java 类；
- 知识点可以承接上一节，不重复基础内容；
- RPC 地址仅保留占位符；
- 真实 API Key、钱包私钥和助记词不得提交到仓库。

# 完整学习路线

完整 W0—W8 大纲：

- [查看完整学习路线与进度](docs/ROADMAP.md)

# 当前进度

```text
W0｜最小工程与第一次调用           ✅ 已通过

W1｜Web3j 客户端、请求与响应       ✅ 已通过
W1.1｜查询 Chain ID               ✅ 已通过
W1.2｜客户端与通信组件             ✅ 已通过
W1.3｜请求对象与响应对象           ✅ 已通过
W1.4｜组合基本查询                 ✅ 已通过
W1.5｜错误处理                     ✅ 已通过
W1.6｜同步与异步调用               ✅ 已通过
W1.7｜超时与客户端生命周期         ✅ 已通过

W2｜区块、交易、回执与 BNB 余额    🔄 下一阶段
W3｜ABI 与只读合约调用            ⬜ 未开始
W4｜合约事件查询与解析            ⬜ 未开始
W5｜测试环境签名与发送交易         ⬜ 未开始
W6｜Spring Boot 与数据持久化      ⬜ 未开始
W7｜可靠采集与故障恢复            ⬜ 未开始
W8｜完整业务接入与综合验收         ⬜ 未开始
```
