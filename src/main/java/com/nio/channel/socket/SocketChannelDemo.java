package com.nio.channel.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketChannelDemo {
    private static final String ip = "127.0.0.1";
    private static final Integer port = 1234;
    private static final String GREETING = "hello server";

    public static void main(String[] args) throws IOException, InterruptedException {
        InetSocketAddress addr = new InetSocketAddress(ip, port);
        SocketChannel sc = SocketChannel.open();//。静态的open( )方法可以创建一个新的SocketChannel对象，
        sc.configureBlocking(false);//非阻塞
        sc.connect(addr);//创建连接
        while (!sc.finishConnect()) {
            System.out.println("wait to connect");
            Thread.sleep(100);
        }
        System.out.println("connected");
        byte[] bytes = GREETING.getBytes();
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        sc.write(buffer);
        sc.close();
    }
}
