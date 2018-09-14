package com.nio.channel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * ByteChannel的read( ) 和write( )方法使用ByteBuffer对象作为参数。两种方法均返回已传输的字节数，可能比缓冲区的字节数少甚至可能为零。
 * 缓冲区的位置也会发生与已传输字节相同数量的前移。如果只进行了部分传输，缓冲区可以被重新提交给通道并从上次中断的地方继续传输。
 * 该过程重复进行直到缓冲区的hasRemaining( )方法返回false值。
 */
public class ChannelCopy {
    /**
     * This code copies data from stdin to stdout. Like the 'cat' * command, but without any useful options.
     */
    public static void main(String[] args) {
        try (ReadableByteChannel source = Channels.newChannel(System.in);
             WritableByteChannel dest = Channels.newChannel(System.out)) {
//            channelCopy1(source, dest);
            channelCopy2(source, dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        ReadableByteChannel source = null;
//        WritableByteChannel dest = null;
//        try {
//            source = Channels.newChannel(System.in);
//            dest = Channels.newChannel(System.out);
//            channelCopy1(source, dest);
//            channelCopy2
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (source != null) {


//                    source.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            try {
//                if (source != null) {
//                    dest.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//    }
//    }

    }

    /**
     * Channel copy method 1. This method copies data from the src
     * channel and writes it to the dest channel until EOF on src.
     * This implementation makes use of compact( ) on the temp buffer
     * to pack down the data if the buffer wasn't fully drained. This
     * may result in data copying, but minimizes system calls. It also
     * requires a cleanup loop to make sure all the data gets sent.aaa
     */
    private static void channelCopy1(ReadableByteChannel src, WritableByteChannel dest) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(10 * 1024);
        while (src.read(buffer) != -1) {
            // Prepare the buffer to be drained
            buffer.flip();//limit = position; position = 0;mark = -1;
            // Write to the channel; may block
            dest.write(buffer);//channel 是一种非阻塞 io 操作，write操作并不能一次将buffer 中的数据全部写入到指定的 channel 中去，但如果一次写不完的话么第二次再读取的时候，我们就要将 position = limit ,limit = capacity ,然后再读取，不然第二次读取的数据会把第一次没有write 完的数据覆盖掉，
            // If partial transfer, shift remainder down
            // If buffer is empty, same as doing clear( )
            buffer.compact();//position = limit ,limit = capacity
        }
        // EOF will leave buffer in fill state
        buffer.flip();
        // Make sure that the buffer is fully drained
        while (buffer.hasRemaining()) {
            dest.write(buffer);
        }
    }

    /**
     * Channel copy method 2. This method performs the same copy, but
     * assures the temp buffer is empty before reading more data. This
     * never requires data copying but may result in more systems calls.
     * No post-loop cleanup is needed because the buffer will be empty
     * when the loop is exited.
     */
    private static void channelCopy2(ReadableByteChannel src, WritableByteChannel dest) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(10 * 1024);
        while (src.read(buffer) != -1) {
            // Prepare the buffer to be drained
            buffer.flip();
            // Make sure that the buffer was fully drained
            while (buffer.hasRemaining()) {
                dest.write(buffer);
            }
            // Make the buffer empty, ready for filling
            buffer.clear();//position = 0;  limit = capacity;mark = -1;
        }
    }
}
