package com.nio.channel.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * Demonstrate asynchronous connection of a SocketChannel
 * 。请记住，sockets是面向流的而非包导向的。它们可以保证发送的字节会按照顺序到达但无法承诺维持字节分组。
 * 某个发送器可能给一个socket写入了20个字节而接收器调用read( )方法时却只收到了其中的3个字节。剩下的17个字节还是传输中。
 * 由于这个原因，让多个不配合的线程共享某个流socket的同一侧绝非一个好的设计选择
 */
public class ConnectAsync {
    private static String host = "127.0.0.1";
    private static String port = "1234";

    public static void main(String[] args) throws IOException {
        if (args.length == 2) {
            host = args[0];
            port = args[1];
        }
        InetSocketAddress socketAddress = new InetSocketAddress(host, Integer.parseInt(port));
        SocketChannel sc = SocketChannel.open();
        sc.configureBlocking(false);
        System.out.println("initiating connection");
        sc.connect(socketAddress);
        while (!sc.finishConnect()) {
            doSomethingUseful();
        }
        System.out.println ("connection established");
        // Do something with the connected socket
        // The SocketChannel is still nonblocking
        sc.close();


    }

    private static void doSomethingUseful() {
        System.out.println("doing something useless");
    }
}
