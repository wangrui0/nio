package com.nio.channel.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelDemo02 {
    public static void main(String[] args) {
        try {
            readFile(new File("D:\\file\\a.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 使用nio去读取文件
     * @param file
     * @throws IOException
     */
    public static void readFile(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel channel = fileInputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int read = channel.read(byteBuffer);
        byteBuffer.flip();
        while (read>0&&byteBuffer.hasRemaining()) {
            System.out.print((char) byteBuffer.get());
        }
       // channel.write(byteBuffer); 注意fileInputStream.getChannel只有只读权限哦
    }
}
