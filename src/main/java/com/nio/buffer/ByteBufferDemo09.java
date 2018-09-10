package com.nio.buffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * 字节缓存区
 * (1)八位的字节来自于市场力量和实践的结合。它之所以实用是因为8位足以表达可用的字符集（至少是英文字符），
 * 8是2的三次乘方（这简化了硬件设计），八恰好容纳两个十六进制数字，而且8的倍数提供了足够的组合位来存储有效的数值数据类型
 * (2)字节顺序
 * (3)直接缓冲区
 * (4)数据元素视图
 */
public class ByteBufferDemo09 {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        ByteBuffer duplicate = byteBuffer.duplicate();
        System.out.println(byteBuffer.order().equals(duplicate.order()));//true
        duplicate.order(ByteOrder.LITTLE_ENDIAN);
        //注意order两者不进行同步。也就是不共享
        System.out.println(byteBuffer.order().equals(duplicate.order()));//false
    }
}
