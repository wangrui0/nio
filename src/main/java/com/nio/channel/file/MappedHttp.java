package com.nio.channel.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 内存映射缓冲区如何同scatter/gather结合使用
 * Dummy HTTP server using MappedByteBuffers.
 * Given a filename on the command line, pretend to be * a web server and generate an HTTP response containing
 * the file content preceded by appropriate headers. The
 * data is sent with a gathering write.
 *
 * @author wangrui02
 */
public class MappedHttp {
    private static final String OUTPUT_FILE = "D://MappedHttp.out";
    private static final String LINE_SEP = "\r\n";
    private static final String SERVER_ID = "Server: Ronsoft Dummy Server";
    private static final String HTTP_HDR = "HTTP/1.0 200 OK" + LINE_SEP + SERVER_ID + LINE_SEP;
    private static final String HTTP_404_HDR = "HTTP/1.0 404 Not Found" + LINE_SEP + SERVER_ID + LINE_SEP;
    private static final String MSG_404 = "Could not open file: ";

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("Usage: filename");
            return;
        }
        String file = args[0];//文件名
        ByteBuffer head = ByteBuffer.wrap(bytes(HTTP_HDR));//head  将字符串转换成字符数组，创建一个字符缓存区
        ByteBuffer dynhdrs = ByteBuffer.allocate(128);
        ByteBuffer[] gather = {head, dynhdrs, null};
        String contentType = "unknown/unknown";
        long contentLength = -1;
        try {
            FileInputStream fis = new FileInputStream(file);
            FileChannel fc = fis.getChannel();
            MappedByteBuffer filedata = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            gather[2] = filedata;//与gather一起使用
            contentLength = fc.size();
            contentType = URLConnection.guessContentTypeFromName(file);
        } catch (IOException e) {
            // file could not be opened; report problem
            ByteBuffer buf = ByteBuffer.allocate(128);
            String msg = MSG_404 + e + LINE_SEP;
            buf.put(bytes(msg));
            buf.flip();
            // Use the HTTP error response
            gather[0] = ByteBuffer.wrap(bytes(HTTP_404_HDR));
            gather[2] = buf;
            contentLength = msg.length();
            contentType = "text/plain";
        }
        StringBuffer sb = new StringBuffer();
        sb.append("Content-Length: " + contentLength);
        sb.append(LINE_SEP);
        sb.append("Content-Type: ").append(contentType);
        sb.append(LINE_SEP).append(LINE_SEP);
        dynhdrs.put(bytes(sb.toString()));
        dynhdrs.flip();
        FileOutputStream fos = new FileOutputStream(OUTPUT_FILE);
        FileChannel out = fos.getChannel();
        // All the buffers have been prepared; write 'em out
        while (out.write(gather) > 0) {
            // Empty body; loop until all buffers are empty
        }
        out.close();
        System.out.println("output written to " + OUTPUT_FILE);
    }
    // Convert a string to its constituent bytes
    // from the ASCII character set
    private static byte[] bytes(String string) throws UnsupportedEncodingException {
        return string.getBytes("US-ASCII");
    }

}
