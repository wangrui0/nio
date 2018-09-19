package com.nio.channel.file;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Random;

/**
 * 使用共享锁实现了reader进程，使用独占锁实现了writer进程。
 * 由于锁是与进程而不是Java线程关联的，您将需要运行该程序的多个拷贝。先从一个writer和两个或更多的readers开始，我们来看下不同类型的锁是如何交互的
 * Test locking with FileChannel.
 * Run one copy of this code with arguments "-w /tmp/locktest.dat"
 * and one or more copies with "-r /tmp/locktest.dat" to see the
 * interactions of exclusive and shared locks. Note how too many
 * readers can starve out the writer.
 * Note: The filename you provide will be overwritten. Substitute
 * an appropriate temp filename for your favorite OS.
 *
 * @author wangrui02
 */
public class LockTest {
    private static final int SIZEOF_INT = 4;
    private static final int INDEX_START = 0;
    private static final int INDEX_COUNT = 10;
    private static final int INDEX_SIZE = INDEX_COUNT * SIZEOF_INT;
    private ByteBuffer buffer = ByteBuffer.allocate(INDEX_SIZE);
    private IntBuffer indexBuffer = buffer.asIntBuffer();
    private Random rand = new Random();


    public static void main(String[] args) throws IOException, InterruptedException {
        boolean writer = false;
        String fileName;
        if (args.length != 2) {
            System.out.println("Usage :[-r|-w] filename");
            return;
        }
        writer = args[0].equals("-w");
        fileName = args[1];
        RandomAccessFile randomAccessFile = new RandomAccessFile(fileName, writer ? "rw" : "r");
        FileChannel fc = randomAccessFile.getChannel();
        LockTest lockTest = new LockTest();
        if (writer) {
            lockTest.doUpdates(fc);
        } else {
            lockTest.doQueries(fc);
        }
    }

    // ----------------------------------------------------------------
    // Simulate a series of read-only queries while
    // holding a shared lock on the index area
    private void doQueries(FileChannel fc) throws IOException, InterruptedException {
        while (true) {
            println("trying for shared lock...");
            FileLock lock = fc.lock(INDEX_START, INDEX_SIZE, true);//共享锁
            try {
                int reps = rand.nextInt(60) + 20;
                for (int i = 0; i < reps; i++) {
                    int n = rand.nextInt(INDEX_COUNT);//10
                    int position = INDEX_START + (n * SIZEOF_INT);
                    buffer.clear();
                    fc.read(buffer, position);
                    int value = indexBuffer.get(n);
                    println("Index entry" + n + "=" + value);
                    //prented to be doing some work
                    Thread.sleep(100);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.release();
            }
            println("<Sleeping>");
            Thread.sleep(rand.nextInt(3000) + 500);
        }
    }

    // ----------------------------------------------------------------
    private int lastLineLen = 0;

    // Specialized println that repaints the current line
    private void println(String msg) {
        System.out.println("\r");
        System.out.println(msg);
        for (int i = msg.length(); i < lastLineLen; i++) {
            System.out.println(" ");
        }
        System.out.println("\r");
        System.out.flush();
        lastLineLen = msg.length();
    }

    // Simulate a series of updates to the index area
    // while holding an exclusive lock
    private void doUpdates(FileChannel fc) throws IOException, InterruptedException {
        while (true) {
            println("trying for exclusive lock...");
            FileLock lock = fc.lock(INDEX_START, INDEX_SIZE, false);//独占锁
            try {
                updateIndex(fc);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.release();
            }
            println("<Sleeping>");
            Thread.sleep(rand.nextInt(2000) + 50);
        }
    }

    // Write new values to the index slots
    private int idxval = 1;

    private void updateIndex(FileChannel fc) throws InterruptedException, IOException {
        // "indexBuffer" is an int view of "buffer"
        indexBuffer.clear();
        for (int i = 0; i < INDEX_COUNT; i++) {
            idxval++;
            println("Updating index " + i + "=" + idxval);
            indexBuffer.put(idxval);
            // Pretend that this is really hard work
            Thread.sleep(500);
        }
        // leaves position and limit correct for whole buffer
        buffer.clear();
        fc.write(buffer,INDEX_COUNT);
    }
}
