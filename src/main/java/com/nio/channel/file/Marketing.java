package com.nio.channel.file;

import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.GatheringByteChannel;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Demonstrate gathering write using many buffers.
 */
public class Marketing {
    private static final String DEMOGRAPHIC = "D://blahblah.txt";

    // "Leverage frictionless methodologies"
    public static void main(String[] args) throws Exception {
        int reps = 10;
        if (args.length > 0) {
            reps = Integer.parseInt(args[0]);
        }
        FileOutputStream fos = new FileOutputStream(DEMOGRAPHIC);
        GatheringByteChannel gatherChannel = fos.getChannel();
        // Generate some brilliant marcom, er, repurposed content
        ByteBuffer[] byteBuffers = utterBS(reps);
        // Deliver the message to the waiting market
        while(gatherChannel.write(byteBuffers)>0){
            // Empty body
            // Loop until write( ) returns zero
        }
        System.out.println ("Mindshare paradigms synergized to " + DEMOGRAPHIC);
        fos.close();
    }

    /**
     * @param howMany
     * @return
     */
    private static ByteBuffer[] utterBS(int howMany) throws UnsupportedEncodingException {
        List<ByteBuffer> list = new LinkedList<>();
        for (int i = 0; i < howMany; i++) {
            list.add(pickRandom(col1, " "));
            list.add(pickRandom(col2, " "));
            list.add(pickRandom(col3, newline));
        }
        ByteBuffer[] buffers = new ByteBuffer[list.size()];
        list.toArray(buffers);
        return buffers;
    }

    // The communications director
    private static Random rand = new Random();

    // Pick one, make a buffer to hold it and the suffix, load it with
// the byte equivalent of the strings (will not work properly for
    // non-Latin characters), then flip the loaded buffer so it's ready // to be drained
    private static ByteBuffer pickRandom(String[] strings, String suffix) throws UnsupportedEncodingException {
        String string = strings[rand.nextInt(strings.length)];
        int total = string.length() + suffix.length();
        ByteBuffer buf = ByteBuffer.allocate(total);
        buf.put(string.getBytes("US-ASCII"));
        buf.put(suffix.getBytes("US-ASCII"));
        buf.flip();
        return buf;
    }

    // ------------------------------------------------
    // These are just representative; add your own
    private static String[] col1 = {"Aggregate", "Enable", "Leverage", "Facilitate", "Synergize", "Repurpose", "Strategize", "Reinvent", "Harness"};
    private static String[] col2 = {"cross-platform", "best-of-breed", "frictionless", "ubiquitous", "extensible", "compelling", "mission-critical", "collaborative", "integrated"};
    private static String[] col3 = {"methodologies", "infomediaries", "platforms", "schemas", "mindshare", "paradigms", "functionalities", "web services", "infrastructures"};
    private static String newline = System.getProperty("line.separator");

}
