package com.nio.channel.file;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * FileChannel位置（position）是从底层的文件描述符获得的，
 * 该position同时被作为通道引用获取来源的文件对象共享。这也就意味着一个对象对该position的更新可以被另一个对象看到
 */
public class FilePositionAndHole {
    public static void main(String[] args) throws IOException {
        File temp = File.createTempFile("holy", null);
        RandomAccessFile randomAccessFile = new RandomAccessFile(temp, "r");
        // Set the file position
        randomAccessFile.seek(1000);
        // Create a channel from the file
        FileChannel fileChannel = randomAccessFile.getChannel();
        // This will print "1000"
        System.out.println("file pos:"+fileChannel.position());
        // Change the position using the RandomAccessFile object
        randomAccessFile.seek(500);
        // This will print "500"
        System.out.println("file pos:"+fileChannel.position());
        // Change the position using the FileChannel object
        fileChannel.position(200);
        // This will print "200"
        System.out.println("file pos:"+randomAccessFile.getFilePointer());
        /**
         * file pos:1000
         * file pos:500
         * file pos:200
         */
    }
}
