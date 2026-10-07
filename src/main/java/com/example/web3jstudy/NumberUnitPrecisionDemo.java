package com.example.web3jstudy;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

/**
 * W2.1：数值、单位与精度。
 *
 * 演示内容：
 * 1. JSON-RPC 十六进制 quantity -> BigInteger
 * 2. Wei -> Gwei / BNB 显示值
 * 3. BNB -> Wei 的精确转换
 * 4. 为什么链上原始整数不使用 double 保存
 */
public class NumberUnitPrecisionDemo {

    public static void main(String[] args) {

        /*
         * 1 BNB 对应的最小单位数量。
         *
         * 0xde0b6b3a7640000
         * = 1000000000000000000
         */
        String hexQuantity = "0xde0b6b3a7640000";

        // JSON-RPC quantity 解码为任意精度整数。
        BigInteger wei = Numeric.decodeQuantity(hexQuantity);

        /*
         * Web3j 的 Convert.Unit.ETHER 名称沿用 Ethereum 术语，
         * 本质上代表 10^18 Wei 的换算因子。
         *
         * BSC 原生 BNB 同样使用 18 位精度，
         * 因此这里可以用该换算因子把 Wei 转成 BNB 显示值。
         */
        BigDecimal bnb = Convert.fromWei(
                new BigDecimal(wei),
                Convert.Unit.ETHER);

        BigDecimal gwei = Convert.fromWei(
                new BigDecimal(wei),
                Convert.Unit.GWEI);

        System.out.println("RPC 十六进制数量：" + hexQuantity);
        System.out.println("Wei：" + wei);
        System.out.println("Gwei：" + gwei.toPlainString());
        System.out.println("BNB：" + bnb.toPlainString());

        /*
         * 从用户可读的 BNB 金额转换回链上最小单位。
         *
         * 使用字符串创建 BigDecimal，避免先经过 double。
         */
        BigDecimal inputBnb =
                new BigDecimal("0.123456789012345678");

        BigDecimal weiDecimal = Convert.toWei(
                inputBnb,
                Convert.Unit.ETHER);

        /*
         * 链上最小单位必须是整数。
         * toBigIntegerExact() 会在存在小数 Wei 时直接报错，
         * 避免静默截断精度。
         */
        BigInteger inputWei =
                weiDecimal.toBigIntegerExact();

        BigDecimal restoredBnb = Convert.fromWei(
                new BigDecimal(inputWei),
                Convert.Unit.ETHER);

        System.out.println();
        System.out.println("输入 BNB：" + inputBnb.toPlainString());
        System.out.println("转换为 Wei：" + inputWei);
        System.out.println("再还原 BNB：" + restoredBnb.toPlainString());

        /*
         * double 是二进制浮点数。
         * 下面故意展示从 double 直接构造 BigDecimal 后的值，
         * 说明为什么金额不应先经过 double。
         */
        BigDecimal unsafe =
                new BigDecimal(0.1);

        BigDecimal safe =
                new BigDecimal("0.1");

        System.out.println();
        System.out.println("double 0.1 转 BigDecimal：" + unsafe.toPlainString());
        System.out.println("字符串 0.1 转 BigDecimal：" + safe.toPlainString());
    }
}
