package com.nio.buffer;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * 缓冲区入门
 */
public class BufferDemo01 {
    public static void main(String[] args) {
        //存入Hello
        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        byteBuffer.put((byte) 'H').put((byte) 'e').put((byte) 'l').put((byte) 'l').put((byte) 'o');
        //法一：读取
        byteBuffer.limit(byteBuffer.position()).position(0);//从填充到释放状态的缓冲区翻转
        for (int i = 0; i < byteBuffer.limit(); i++) {
            System.out.print((char)byteBuffer.get());//Hello
        }
        System.out.println();
        //法二：读取
        ByteBuffer byteBuffer2 = (ByteBuffer)byteBuffer.flip();//从填充到释放状态的缓冲区翻转 limit = position;position = 0;mark = -1  把上界设为位置的值，并把位置设为0
        while (byteBuffer2.hasRemaining()) {
            System.out.print((char)byteBuffer2.get());//Hello
        }
        System.out.println();
        //下面的效率更高欧，因为不会重复去检查
        ByteBuffer byteBuffer4 = (ByteBuffer)byteBuffer.flip();
        int count = byteBuffer4.remaining();
        for(int i=0;i<count;i++){
            System.out.print((char)byteBuffer.get());//Hello
        }
        System.out.println();
        //将缓存区释放到数组
        ByteBuffer byteBuffer3 = (ByteBuffer)byteBuffer.flip();
        char[] copy=new char[byteBuffer3.limit()];
        for(int i=0;byteBuffer3.hasRemaining();i++){
            copy[i]=(char)byteBuffer3.get();
        }
        System.out.println(Arrays.toString(copy));//[H, e, l, l, o]
    }
}
