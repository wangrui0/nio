package com.nio.channel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * filelock加锁举例，必须要回在final中释放锁哦
 */
public class FileLockTemplate {
    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("D://blahblah.txt", "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        FileLock lock = fileChannel.lock();
        try {
            ByteBuffer byteBuffer = ByteBuffer.allocate(10 * 1024);
            while (fileChannel.read(byteBuffer) > 0) {
                byteBuffer.flip();
                while (byteBuffer.hasRemaining()) {
                    System.out.print((char) byteBuffer.get());
                }
                byteBuffer.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.release();
        }
        /**
         * Leverage collaborative functionalities
         * Enable mission-critical functionalities
         * Facilitate ubiquitous schemas
         * Facilitate best-of-breed platforms
         * Aggregate frictionless infrastructures
         * Facilitate ubiquitous schemas
         * Reinvent integrated platforms
         * Synergize ubiquitous mindshare
         * Synergize ubiquitous infomediaries
         * Repurpose extensible methodologies
         */
    }
}
