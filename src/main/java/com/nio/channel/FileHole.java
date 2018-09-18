package com.nio.channel;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 如果该文件被顺序读取的话，所有空洞都会被“0”填充但不占用磁盘空间。
 * 读取该文件的进程会看到5,000,021个字节，大部分字节都以“0”表示。
 * 试试在该文件上运行strings命令，看看您会得到什么。
 * 再试试将文件大小的值提高到50或100MB，看看您的全部磁盘空间消耗以及顺序扫描该文件所需时间会发生何种变化（前者不会改变，但是后者将有非常大的增加）。
 */
public class FileHole {
    public static void main(String[] args) throws IOException {
        // Create a temp file, open for writing, and get
        // a FileChannel
        File temp = File.createTempFile("holy", null);
        RandomAccessFile file = new RandomAccessFile(temp, "rw");
        FileChannel channel = file.getChannel();
        // Create a working buffer
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(100);
        putdata(0, byteBuffer, channel);
        putdata(5000000, byteBuffer, channel);
        putdata(50000, byteBuffer, channel);
        // Size will report the largest position written, but
        // there are two holes in this file. This file will
        // not consume 5 MB on disk (unless the filesystem is
        // extremely brain-damaged)
        System.out.println("Wrote temp file '" + temp.getPath() + "', size=" + channel.size());
        //Wrote temp file 'C:\Users\WANGRU~1\AppData\Local\Temp\holy8808929036136301769.tmp', size=5000021  4.76M中间很多的空白
        channel.close();
        file.close();
    }

    private static void putdata(int position, ByteBuffer buffer, FileChannel channel) throws IOException {
        String string = "*<----location" + position;
        buffer.clear();
        buffer.put(string.getBytes("US-ASCII"));
        buffer.flip();
        channel.position(position);
        channel.write(buffer);
    }
}
