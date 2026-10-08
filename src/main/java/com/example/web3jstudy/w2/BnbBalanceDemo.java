package com.example.web3jstudy.w2;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

/**
 * W2.2：查询 BSC 地址的原生 BNB 余额。
 */
public class BnbBalanceDemo {

    public static void main(String[] args) throws IOException {

        String rpcUrl =
                "https://rpc.ankr.com/bsc_testnet_chapel/303785a842b9c374f30ecebd5045cf6088f5e300595eed1ed04b49dd1b7bc126";

        /*
         * 这里使用零地址作为公开示例地址。
         * 也可以替换成任意合法的 BSC 地址。
         *
         * 查询余额不需要该地址的私钥。
         */
        String address =
                "0x566c1d509521d666fdbd487a36e3162725e68b8f";

        Web3j web3j =
                Web3j.build(new HttpService(rpcUrl));

        try {
            /*
             * eth_getBalance(address, "latest")
             *
             * 查询该地址在最新区块状态下的原生币余额。
             */
            EthGetBalance response =
                    web3j.ethGetBalance(
                            address,
                            DefaultBlockParameterName.LATEST)
                            .send();

            if (response == null) {
                throw new IllegalStateException(
                        "RPC 未返回有效响应。");
            }

            if (response.hasError()) {
                throw new IllegalStateException(
                        "查询余额失败，错误码："
                                + response.getError().getCode()
                                + "，错误信息："
                                + response.getError().getMessage());
            }

            /*
             * 原始 result 是十六进制 quantity。
             */
            String rawResult =
                    response.getResult();

            /*
             * getBalance() 把 result 解码成 BigInteger。
             * 该值的单位是 Wei。
             */
            BigInteger balanceWei =
                    response.getBalance();

            /*
             * BSC 原生 BNB 使用 18 位精度。
             * Convert.Unit.ETHER 在这里使用的是 10^18 的换算因子。
             */
            BigDecimal balanceBnb =
                    Convert.fromWei(
                            new BigDecimal(balanceWei),
                            Convert.Unit.ETHER);

            System.out.println(
                    "查询地址：" + address);

            System.out.println(
                    "原始 result：" + rawResult);

            System.out.println(
                    "余额（Wei）：" + balanceWei);

            System.out.println(
                    "余额（BNB）："
                            + balanceBnb.toPlainString());

        } finally {
            web3j.shutdown();
        }
    }
}
