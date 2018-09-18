package com.nio.channel;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.channels.Channels;
import java.io.FileInputStream;

/**
 * 由于经常需要从一个位置将文件数据批量传输到另一个位置，FileChannel类添加了一些优化方法来提高该传输过程的效率：
 * transferTo( )和transferFrom( )方法允许将一个通道交叉连接到另一个通道，而不需要通过一个中间缓冲区来传递数据。
 * 只有FileChannel类有这两个方法，因此channel-to-channel传输中通道之一必须是FileChannel。
 * 您不能在socket通道之间直接传输数据，不过socket通道实现WritableByteChannel和ReadableByteChannel接口，
 * 因此文件的内容可以用transferTo( )方法传输给一个socket通道，或者也可以用transferFrom( )方法将数据从一个socket通道直接读取到一个文件中。
 * 直接的通道传输不会更新与某个FileChannel关联的position值。请求的数据传输将从position参数指定的位置开始，传输的字节数不超过count参数的值。
 * 实际传输的字节数会由方法返回，可能少于您请求的字节数。
 * Test channel transfer. This is a very simplistic concatenation
 * program. It takes a list of file names as arguments, opens each
 * in turn and transfers (copies) their content to the given
 * WritableByteChannel (in this case, stdout).
 */
public class ChannelTransfer {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.err.println("Usage: filename ...");
            return;
        }
        catFiles(Channels.newChannel(System.out), args);
    }

    // Concatenate the content of each of the named files to
    // the given channel. A very dumb version of 'cat'.
    private static void catFiles(WritableByteChannel target, String[] files) throws IOException {
        for (int i = 0; i < files.length; i++) {
            FileInputStream fileInputStream = new FileInputStream(files[i]);
            FileChannel channel = fileInputStream.getChannel();
            channel.transferTo(0,channel.size(),target);
            channel.close();
            fileInputStream.close();
        }
    }
}
