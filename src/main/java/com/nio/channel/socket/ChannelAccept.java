package com.nio.channel.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 使用一个非阻塞的accept( )方法
 * Test nonblocking accept( ) using ServerSocketChannel.
 * Start this program, then "telnet localhost 1234" to
 * connect to it.
 */
public class ChannelAccept {
    public static final String GREETING = "Hello I must be going.\r\n";
    public static int port = 1234;//default

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        ByteBuffer buffer = ByteBuffer.wrap(GREETING.getBytes());
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ServerSocket socket = ssc.socket();
        socket.bind(new InetSocketAddress(port));
        ssc.configureBlocking(false);//非阻塞
        while (true) {
            System.out.println("Waiting for connections");
            SocketChannel sc = ssc.accept();
            if (sc == null) {
                // no connections, snooze a while
                Thread.sleep(2000);
            } else {
                System.out.println("Incoming connection from:" + sc.socket().getRemoteSocketAddress());
                buffer.rewind();
                sc.write(buffer);
                sc.close();
            }
        }
    }
}
