package com.nio.buffer;

import java.nio.CharBuffer;

/**
 * public abstract CharBuffer duplicate( );
 * public abstract CharBuffer asReadOnlyBuffer( );
 * public abstract CharBuffer slice( );
 * (1)Duplicate()函数创建了一个与原始缓冲区相似的新缓冲区。两个缓冲区共享数据元素，拥有同样的容量，但每个缓冲区拥有各自的位置，上界和标记属性。
 * 对一个缓冲区内的数据元素所做的改变会反映在另外一个缓冲区上。
 * 这一副本缓冲区具有与原始缓冲区同样的数据视图。如果原始的缓冲区为只读，或者为直接缓冲区，新的缓冲区将继承这些属性。
 * 注意：复制一个缓冲区会创建一个新的Buffer对象，但并不复制数据。原始缓冲区和副本都会操作同样的数据元素。
 * (2)可以使用asReadOnlyBuffer()函数来生成一个只读的缓冲区视图。
 * 这与duplicate()相同，除了这个新的缓冲区不允许使用put()，
 * 并且其isReadOnly()函数将会返回true。对这一只读缓冲区的put()函数的调用尝试会导致抛出ReadOnlyBufferException异常。
 * 如果一个只读的缓冲区与一个可写的缓冲区共享数据，或者有包装好的备份数组，那么对这个可写的缓冲区或直接对这个数组的改变将反映在所有关联的缓冲区上，包括只读缓冲区。
 * (3)
 * 分割缓冲区与复制相似，但slice()创建一个从原始缓冲区的当前位置开始的新缓冲区，并且其容量是原始缓冲区的剩余元素数量（limit-position）。
 * 这个新缓冲区与原始缓冲区共享一段数据元素子序列。分割出来的缓冲区也会继承只读和直接属性。(对应位置的映射，对应位置改变，slice改变)
 */
public class CopyAndSliceBuffer08 {
    public static void main(String[] args) {
        //duplicate
        CharBuffer buffer = CharBuffer.allocate(8);
        buffer.put('H').put('e').put('l').put('l').put('o').put('w');
        buffer.position (2).limit (5).mark( ).position (4);//position-->4 limit --->5
        CharBuffer dupeBuffer = buffer.duplicate( );
        buffer.clear( );
        //4个坐标不共享
        System.out.println(dupeBuffer.position()+"============="+dupeBuffer.limit());//4=============5
        System.out.println(buffer.position()+"============="+buffer.limit());//0=============8
        //数据是否共享呢？也可以说数组是否共享呢？
        buffer.put("A");//buffer使用flip不好使，看一下他的源码你就懂得了
        dupeBuffer.flip();
        buffer.position(dupeBuffer.position()).limit(dupeBuffer.limit());//设置他俩的坐标要一样
        System.out.println(buffer.equals(dupeBuffer));//true
        //asReadOnlyBuffer
        CharBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        System.out.println(readOnlyBuffer.isReadOnly());//true
        //slice
        buffer.position(2).limit(5);
        CharBuffer sliceBuffer = buffer.slice();//position大小为：limit-position
        while(sliceBuffer.hasRemaining()){//position=0;limit=capacity=3
            System.out.print(sliceBuffer.get());//llo
        }
        System.out.println();
        buffer.put(2,'a');
        buffer.put(3,'a');
        buffer.put(4,'a');
        sliceBuffer.flip();
        while(sliceBuffer.hasRemaining()){//position=0;limit=capacity=3
            System.out.print(sliceBuffer.get());//aaa
        }
        /**
         * 4=============5
         * 0=============8
         * true
         * true
         * llo
         * aaa
         */
    }
}

