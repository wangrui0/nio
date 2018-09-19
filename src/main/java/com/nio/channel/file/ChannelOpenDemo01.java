package com.nio.channel.file;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * (1)通道是访问I/O服务的导管。正如我们在第一章中所讨论的，I/O可以分为广义的两大类别：File I/O和Stream I/O。
 * 那么相应地有两种类型的通道也就不足为怪了，它们是文件（file）通道和套接字（socket）通道。
 * 如果您参考一下图3-2，您就会发现有一个FileChannel类和三个socket通道类：SocketChannel、ServerSocketChannel和DatagramChannel。
 * (2)通道可以以多种方式创建。Socket通道有可以直接创建新socket通道的工厂方法。
 * 但是一个FileChannel对象却只能通过在一个打开的RandomAccessFile、FileInputStream或FileOutputStream对象上调用getChannel( )方法来获取。
 * 您不能直接创建一个FileChannel对象。File和socket通道会在后面的章节中予以详细讨论。
 */
public class ChannelOpenDemo01 {
    private static final String host = "127.0.0.1";
    private static final Integer port = 9091;

    public static void main(String[] args) {
        //创建socket通道的方法
        SocketChannel sc = null;
        try {
            sc = SocketChannel.open();
            sc.connect(new InetSocketAddress(host, port));
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(port));
            DatagramChannel datagramChannel = DatagramChannel.open();
            RandomAccessFile randomAccessFile = new RandomAccessFile("somefile", "r");
            FileChannel channel = randomAccessFile.getChannel();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //创建fileChannel通道的方法

    }
}
