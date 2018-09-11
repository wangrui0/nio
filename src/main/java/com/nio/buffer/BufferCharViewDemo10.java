package com.nio.buffer;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;

/**
 * 创建一个ByteBuffer的字符视图
 */
public class BufferCharViewDemo10 {
    public static void main(String[] args) {
        //创建一个字节缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(7).order(ByteOrder.BIG_ENDIAN);
        //创建字符缓存区视图
        CharBuffer charBuffer = byteBuffer.asCharBuffer();
        // Load the ByteBuffer with some bytes
        byteBuffer.put(0,(byte)0);
        byteBuffer.put(1,(byte)'H');
        byteBuffer.put(2,(byte)0);
        byteBuffer.put(3,(byte)'i');
        byteBuffer.put(4,(byte)0);
        byteBuffer.put(5,(byte)'!');
        byteBuffer.put(6,(byte)0);
        println (byteBuffer);//打印字节缓存区//pos=0, limit=7, capacity=7: 'java.nio.HeapByteBuffer[pos=0 lim=7 cap=7]'
        println (charBuffer);//打印字符穿冲区//pos=0, limit=3, capacity=3: 'Hi!'两个字节被映射为一个字符
        byteBuffer.put(0,(byte)'a');
        byteBuffer.put(1,(byte)'H');
        byteBuffer.put(2,(byte)'a');
        byteBuffer.put(3,(byte)'i');
        byteBuffer.put(4,(byte)'a');
        byteBuffer.put(5,(byte)'!');
        byteBuffer.put(6,(byte)'a');
        println (byteBuffer);//打印字节缓存区//pos=0, limit=7, capacity=7: 'java.nio.HeapByteBuffer[pos=0 lim=7 cap=7]'
        println (charBuffer);//打印字符穿冲区//pos=0, limit=3, capacity=3: '慈慩愡'
    }
    // Print info about a buffer
    private static void println(Buffer buffer) {
        System.out.println ("pos=" + buffer.position( ) + ", limit=" + buffer.limit( ) + ", capacity=" + buffer.capacity( ) + ": '" + buffer.toString( ) + "'");
    }
}
