package com.nio.buffer;

import java.nio.CharBuffer;
import java.util.Arrays;

/**
 * 新的缓冲区是由分配或包装操作创建的。分配操作创建一个缓冲区对象并分配一个私有的空间来储存容量大小的数据元素。
 * 包装操作创建一个缓冲区对象但是不分配任何空间来储存数据元素。它使用您所提供的数组作为存储空间来储存缓冲区中的数据元素
 */
public class CreateBufferDemo07 {
    public static void main(String[] args) {
        System.out.println("法一：allocate");//这段代码隐含地从堆空间中分配了一个char型数组作为备份存储器来储存100个char变量。
        //法一：allocate要分配一个容量为100个char变量的Charbuffer:
        CharBuffer charBuffer = CharBuffer.allocate(100);
        charBuffer.put('H').put('e').put('l').put('l').put('o').put('w');
        charBuffer.flip();
        while (charBuffer.hasRemaining()) {
            System.out.print(charBuffer.get());
        }
        System.out.println("法一：allocated对应的array");
        if(charBuffer.hasArray()){
            char[] array =charBuffer.array();
            System.out.println(Arrays.toString(array));
        }
        //法二：通过wrap;如果您想提供您自己的数组用做缓冲区的备份存储器，请调用wrap()函数：
        System.out.println("通过wrap");
        char[] chars = new char[100];
        CharBuffer charBufferWrap = CharBuffer.wrap(chars);
        charBufferWrap.put('H').put('e').put('l').put('l').put('o').put('w');
        charBufferWrap.flip();
        while (charBufferWrap.hasRemaining()) {
            System.out.print(charBufferWrap.get());
        }
        System.out.println();
        System.out.println(Arrays.toString(chars));
        //法三：wrap (char [] array, int offset, int length)
        //带有offset和length作为参数的wrap()函数版本则会构造一个按照您提供的offset和length参数值初始化位置和上界的缓冲区。这样做： CharBuffer charbuffer = CharBuffer.wrap (myArray, 12, 42); 创建了一个position值为12，limit值为54，容量为myArray.length的缓冲区。
        System.out.println("通过wrap2");
        CharBuffer charBufferWrap2 = CharBuffer.wrap(chars,charBufferWrap.position(),charBufferWrap.limit());
        charBufferWrap2.put('H').put('e').put('l').put('l').put('o').put('w');
        charBufferWrap2.flip();
        while (charBufferWrap2.hasRemaining()) {
            System.out.print(charBufferWrap2.get());
        }
        System.out.println();
        System.out.println(Arrays.toString(chars));
        System.out.println("法二：wrapd对应的array");
        if(charBufferWrap2.hasArray()){
            char[] array =charBuffer.array();
            System.out.println(Arrays.toString(array));
        }
        //方法四：
        System.out.println("通过wrap (CharSequence csq)");
        String str5="hello";
        CharBuffer strWrap = CharBuffer.wrap(str5);
        char[] chars1 = new char[strWrap.remaining()];
        strWrap.get(chars1);
        System.out.println(Arrays.toString(chars1));

    }
}
