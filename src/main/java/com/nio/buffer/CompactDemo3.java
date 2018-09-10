package com.nio.buffer;

import java.nio.ByteBuffer;

/**
 * 调用compact()的作用是丢弃已经释放的数据，保留未释放的数据
 */
public class CompactDemo3 {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        byteBuffer.put((byte) 'H').put((byte) 'e').put((byte) 'l').put((byte) 'l').put((byte) 'o');
        byteBuffer.flip();
//        while (byteBuffer.hasRemaining()) {
//            System.out.print((char)byteBuffer.get());
//        }
        int count = byteBuffer.remaining();
        for(int i=0;i<2&&i<count;i++){
            System.out.print((char)byteBuffer.get());
        }
        System.out.println("====================compact=================");
        byteBuffer.compact();
        byteBuffer.put((byte) ' ').put((byte) 'w').put((byte) 'o').put((byte) 'r').put((byte) 'l').put((byte) 'd');
        byteBuffer.flip();
        while(byteBuffer.hasRemaining()){
            System.out.print((char)byteBuffer.get());
        }
        System.out.println();
        /**
         * console:
         *
         * He====================compact=================
         * llo world
         */
    }
}
