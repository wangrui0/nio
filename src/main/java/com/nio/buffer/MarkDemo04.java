package com.nio.buffer;

import java.nio.ByteBuffer;

/**
 * 缓冲区的标记在mark( )函数被调用之前是未定义的，调用时标记被设为当前位置的值。
 * reset( )函数将位置设为当前的标记值。如果标记值未定义，调用reset( )将导致InvalidMarkException异常。
 * 一些缓冲区函数会抛弃已经设定的标记（rewind( )，clear( )，以及flip( )总是抛弃标记）。
 * 如果新设定的值比当前的标记小，调用limit( )或position( )带有索引参数的版本会抛弃标记.
 * 注意:
 * 不要混淆reset( )和clear( )。clear( )函数将清空缓冲区，而reset( )位置返回到一个先前设定的标记。
 */
public class MarkDemo04 {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        byteBuffer.put((byte) 'H').put((byte) 'e').put((byte) 'l').put((byte) 'l').put((byte) 'o').put((byte) 'w');
        byteBuffer.position(2).mark().position(4);
        int count = byteBuffer.remaining();
        for(int i=0;i<2&&i<count;i++){
            System.out.print((char)byteBuffer.get());
        }
        System.out.println();
        byteBuffer.reset();
       while (byteBuffer.hasRemaining()){
           System.out.print((char)byteBuffer.get());
       }
        /**
         * console:
         * ow
         * llow
         * Process finished with exit code 0
         */
    }
}
