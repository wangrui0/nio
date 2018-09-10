package com.nio.buffer;

import com.nio.enums.BusinessExceptionEnum;
import com.nio.exception.BusinessException;

import java.nio.CharBuffer;

public class CharBuffer06 {
    private static int index = 0;

    private static String[] strings = {
            "A random string value",
            "The product of an infinite number of monkeys",
            "Hey hey we're the Monkees",
            "Opening act for the Monkees: Jimi Hendrix",
            "'Scuse me while I kiss this fly", // Sorry Jimi ;-)
            "Help Me! Help Me!",
    };

    public static void main(String[] args) {
        try {
            CharBuffer buffer = CharBuffer.allocate(50);
//            while (fillBufferChar(buffer)) {//fill
            while (fillBufferStr(buffer)) {//fill
                buffer.flip();
                drainBufferChar(buffer);//drain
                buffer.clear();// position = 0; limit = capacity; mark = -1
            }
        } catch (BusinessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理数据
     *
     * @param smallArray
     * @param minLength
     */
    private static void proccessData(char[] smallArray, int minLength) {
        for (int i = 0; i < minLength; i++) {
            System.out.print(smallArray[i]);
        }
        System.out.println();
    }


    private static void drainBufferChar(CharBuffer buffer) {
        char[] smallArray = new char[10];
        while (buffer.hasRemaining()) {
            int minLength = Math.min(buffer.remaining(), smallArray.length);
            buffer.get(smallArray, 0, minLength);
            proccessData(smallArray, minLength);
        }
    }


    private static boolean fillBufferChar(CharBuffer buffer) {
        if (index >= strings.length) {
            return false;
        }

        String string = strings[index++];
        if (buffer.remaining() < string.length()) {
            throw new BusinessException(BusinessExceptionEnum.BUFFER_OVER_FLOW.getCode(), BusinessExceptionEnum.BUFFER_OVER_FLOW.getMsg());
        }
        char[] src = string.toCharArray();
        buffer.put(src, 0, src.length);
        return true;

    }
    /**
     * A random s
     * tring valu
     * e
     * The produc
     * t of an in
     * finite num
     * ber of mon
     * keys
     * Hey hey we
     * 're the Mo
     * nkees
     * Opening ac
     * t for the
     * Monkees: J
     * imi Hendri
     * x
     * 'Scuse me
     * while I ki
     * ss this fl
     * y
     * Help Me! H
     * elp Me!
     */

    private static boolean fillBufferStr(CharBuffer buffer) {
        if (index >= strings.length) {
            return false;
        }
        String string = strings[index++];
        if (buffer.remaining() < string.length()) {
            throw new BusinessException(BusinessExceptionEnum.BUFFER_OVER_FLOW.getCode(), BusinessExceptionEnum.BUFFER_OVER_FLOW.getMsg());
        }
        buffer.put(string, 0, string.length());
        return true;
    }
}
