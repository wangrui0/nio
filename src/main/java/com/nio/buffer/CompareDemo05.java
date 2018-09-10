package com.nio.buffer;

import java.nio.ByteBuffer;

/**
 * 所有的缓冲区都提供了一个常规的equals( )函数用以测试两个缓冲区的是否相等，以及一个compareTo( )函数用以比较缓冲区。
 * 比较是针对每个缓冲区内剩余数据进行的，与它们在equals( )中的方式相同，直到不相等的元素被发现或者到达缓冲区的上界
 * 如果每个缓冲区中剩余的内容相同，那么equals( )函数将返回true，否则返回false。因为这个测试是用于严格的相等而且是可换向的。前面的程序清单中的缓冲区名称可以颠倒，并会产生相同的结果.
 * 两个缓冲区被认为相等的充要条件是：
 *  两个对象类型相同。包含不同数据类型的buffer永远不会相等，而且buffer绝不会等于非buffer对象。
 *  两个对象都剩余同样数量的元素。Buffer的容量不需要相同，而且缓冲区中剩余数据的索引也不必相同。但每个缓冲区中剩余元素的数目（从位置到上界）必须相同。
 *  在每个缓冲区中应被Get()函数返回的剩余数据元素序列必须一致。
 * 缓冲区也支持用compareTo( )函数以词典顺序进行比较。这一函数在缓冲区参数小于，等于，或者大于引用compareTo( )的对象实例时，分别返回一个负整数，0和正整数。
 * 这些就是所有典型的缓冲区所实现的java.lang.Comparable接口语义。这意味着缓冲区数组可以通过调用java.util.Arrays.sort()函数按照它们的内容进行排序。
 */
public class CompareDemo05 {
    public static void main(String[] args) {
        //position和limit之间的
        //两个被认为相等的，返回true
        ByteBuffer byteBuffer1 = ByteBuffer.allocate(100);
        byteBuffer1.put((byte) 'd').put((byte) 'o').put((byte) 't').put((byte) 'c').put((byte) 'o').put((byte) 'm').put((byte) ' ').put((byte) 'i').put((byte) 'o').put((byte) 'b');////com
        byteBuffer1.position(3).limit(6);
        ByteBuffer byteBuffer2 = ByteBuffer.allocate(100);
        byteBuffer2.put((byte) 'c').put((byte) 'o').put((byte) 'm').put((byte) '3').put((byte) 'd').put((byte) 'y');//com
        byteBuffer2.position(0).limit(3);
        System.out.println(byteBuffer1.equals(byteBuffer2));
        //两个被认为不相等的，返回false
        ByteBuffer byteBuffer3 = ByteBuffer.allocate(100);
        byteBuffer3.put((byte) 'c').put((byte) 'o').put((byte) 'm').put((byte) '3').put((byte) 'd').put((byte) 'y');////com
        byteBuffer3.flip();
        ByteBuffer byteBuffer4 = ByteBuffer.allocate(100);
        byteBuffer4.put((byte) 'c').put((byte) 'o').put((byte) 'm').put((byte) '3').put((byte) 'd').put((byte) 'y');////com
        byteBuffer4.position(2).limit(6);
        System.out.println(byteBuffer3.equals(byteBuffer4));
    }
}
