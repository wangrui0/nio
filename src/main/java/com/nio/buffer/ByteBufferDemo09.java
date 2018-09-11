package com.nio.buffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * 字节缓存区
 * (1)八位的字节来自于市场力量和实践的结合。它之所以实用是因为8位足以表达可用的字符集（至少是英文字符），
 * 8是2的三次乘方（这简化了硬件设计），八恰好容纳两个十六进制数字，而且8的倍数提供了足够的组合位来存储有效的数值数据类型
 * (2)字节顺序
 * (3)直接缓冲区
 *  字节缓冲区跟其他缓冲区类型最明显的不同在于，它们可以成为通道所执行的I/O的源头和/或目标.通道只接收ByteBuffer作为参数。
 * 直接缓冲区被用于与通道和固有I/O例程交互。它们通过使用固有代码来告知操作系统直接释放或填充内存区域，对用于通道直接或原始存取的内存区域中的字节元素的存储尽了最大的努力。
 * 直接字节缓冲区通常是I/O操作最好的选择。在设计方面，它们支持JVM可用的最高效I/O机制。非直接字节缓冲区可以被传递给通道，但是这样可能导致性能损耗。
 * 通常非直接缓冲不可能成为一个本地I/O操作的目标。
 * 如果您向一个通道中传递一个非直接ByteBuffer对象用于写入，通道可能会在每次调用中隐含地进行下面的操作：
 * 1.创建一个临时的直接ByteBuffer对象。
 * 2.将非直接缓冲区的内容复制到临时缓冲中。
 * 3.使用临时缓冲区执行低层次I/O操作。
 * 4.临时缓冲区对象离开作用域，并最终成为被回收的无用数据。
 * 直接ByteBuffer是通过调用具有所需容量的ByteBuffer.allocateDirect()函数产生的，就像我们之前所涉及的allocate()函数一样。
 * 注意用一个wrap()函数所创建的被包装的缓冲区总是非直接的。
 * 使用直接缓冲区或非直接缓冲区的性能权衡会因JVM，操作系统，以及代码设计而产生巨大差异。
 * 通过分配堆栈外的内存，您可以使您的应用程序依赖于JVM未涉及的其它力量。当加入其他的移动部分时，确定您正在达到想要的效果。
 * 我以一条旧的软件行业格言建议您：先使其工作，再加快其运行。不要一开始就过多担心优化问题；首先要注重正确性。JVM实现可能会执行缓冲区缓存或其他的优化，
 * 这会在不需要您参与许多不必要工作的情况下为您提供所需的性能。
 * (4)数据元素视图
 * 视图缓冲区通过已存在的缓冲区对象实例的工厂方法来创建。这种视图对象维护它自己的属性，容量，位置，上界和标记，但是和原来的缓冲区共享数据元素。
 * 我们已经在2.3节见过了这样的简单例子，在例子中一个缓冲区被复制和切分。但是ByteBuffer类允许创建视图来将byte型缓冲区字节数据映射为其它的原始数据类型。
 * 例如，asLongBuffer()函数创建一个将八个字节型数据当成一个long型数据来存取的视图缓冲区。
 * 这些函数从当前位置开始存取ByteBuffer的字节数据，就好像一个数据元素被存储在那里一样。根据这个缓冲区的当前的有效的字节顺序，这些字节数据会被排列或打乱成需要的原始数据类型。
 * 比如说，如果getInt()函数被调用，从当前的位置开始的四个字节会被包装成一个int类型的变量然后作为函数的返回值返回
 */
public class ByteBufferDemo09 {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        ByteBuffer duplicate = byteBuffer.duplicate();
//        System.out.println(byteBuffer.order().equals(duplicate.order()));//true
        duplicate.order(ByteOrder.LITTLE_ENDIAN);
        //注意order两者不进行同步。也就是不共享
//        System.out.println(byteBuffer.order().equals(duplicate.order()));//false
        //视图缓冲区举例
        ByteBuffer byteBuffe2 = ByteBuffer.allocate(100);
        byteBuffe2.put(0,(byte)0);
        byteBuffe2.put(1,(byte)'H');
        byteBuffe2.put(2,(byte)0);
        byteBuffe2.put(3,(byte)'i');
        byteBuffe2.put(4,(byte)0);
        byteBuffe2.put(5,(byte)'!');
        byteBuffe2.put(6,(byte)0);
        byteBuffe2.put(7,(byte)0);
        byteBuffe2.put(8,(byte)0);
        byteBuffe2.put(9,(byte)0);
        byteBuffe2.put(10,(byte)0);
        byte b = byteBuffe2.get();
        System.out.println(b);//0
        char aChar = byteBuffe2.getChar();
        System.out.println(aChar);//䠀

    }
}
