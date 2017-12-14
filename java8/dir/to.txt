package jdk8.nio;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;

/**
 * 一：缓冲区(Buffer)：在JAVA NIO中负责数据的存取。缓冲区底层就是数组，用于存储不同类型(原始数据类型)的数据：
 * 根据数据类型不同，提供了对应数据类型的缓冲区(boolean除外)
 *
 * ByteBuffer
 * CharBuffer
 * ShortBuffer
 * IntBuffer
 * LongBuffer
 * FloatBuffer
 * DoubleBuffer
 *
 * 上述缓冲区的管理方式几乎一致：通过allocate()获取缓冲区
 *
 * 二：缓冲区存取数据的两个核心方法：
 *  put():存储数据到缓冲区
 *  get():从缓冲区取出数据
 *
 * 三、缓冲区 java.nio.Buffer四个核心属性：
 *  capacity:容量，表示缓冲区中最大存储数据容量。一旦声明不允许修改
 *  limit:界限，表示缓冲区中可操作数据的索引临界点。（大于等于limit的索引都不支持读写）
 *  position:位置，表示缓冲区中下一个可读/写的位置。
 *  mark:标记，表示记录当前position,可以通过reset()方法恢复到mark的位置
 *      mark<=position<=limit<=capacity
 *      这个条件在手动这是position、limit的时候会进行判断并设置为满足状态
 *
 * 四、直接缓冲区与非直接缓冲区
 *  非直接缓冲区: allocate()方法将缓冲区建立在JVM内存中
 *                    磁盘 <-- 内核空间 --> <--用户地址空间-->应用程序
 *  直接缓冲区:allocateDirect()方法将缓冲区直接建立在物理内存中，可以提高效率,仅有ByteBuffer支持
 *                    磁盘 <-->  物理内存映射文件  <-->应用程序
 *
 */
public class TestBuffer {

    //以ByteBuffer为例:相较于其它类型缓冲区，功能最强大，可以写入所有原始数据类型的元素
    //缓冲区基本操作：get put
    // 初始状态下:所有元素初始值为0
    //  capacity由allocate()入参决定；
    //  limit 可能为0，也可能由buffer类型、buffer创建方式决定
    //  position 默认为0
    //  mark 默认-1，此时调用reset抛出InvalidMarkException
    @Test
    public void baseOperations() {
        ByteBuffer buf = ByteBuffer.allocate(1024);
//        ByteBuffer buf = ByteBuffer.allocateDirect(1024);
        System.out.println("isDirect? :"+buf.isDirect());
        buf.put(new byte[]{'a','b','c',2,4,'d'});//position:6 limit=capacity :1024 mark = -1(默认值)
        System.out.println("position:"+buf.position()+"--"+"limit:"+buf.limit());
//---------------------------------------flip-----------------------------------------------------

        //转换为读取状态，底层实际上是设置position、limit为读取状态下的正确值
        System.out.println(buf.get(0));
        buf.flip();//limit = position;position = 0;mark=-1
        System.out.println("position:"+buf.position()+"--"+"limit:"+buf.limit());
        System.out.println(buf.get());//position:1
//---------------------------------------rewind---------------------------------------------------

        System.out.println("before rewind position:"+buf.position()+"--"+"limit:"+buf.limit());
        //调整缓冲区为初始读取状态，以便重复读取
        buf.rewind();//position =0 ;mark = -1
        System.out.println("after rewind position:"+buf.position()+"--"+"limit:"+buf.limit());
//---------------------------------------mark And reset--------------------------------------------

        //标记当前position,调用reset方法返回标记位置
        buf.mark();//mark = position
        buf.get();
        buf.reset();//position = mark;
//---------------------------------------rewind--------------------------------------------------

        //get(int index)明确指定index的时候，不会影响position
        System.out.println((char)buf.get(0));//position:1
        System.out.println("position:"+buf.position()+"--"+"limit:"+buf.limit());
        //若index<0或者>=limit，抛出java.lang.IndexOutOfBoundsException
//        buf.get(-1);
//---------------------------------------putInt(int v)----------------------------------------------

        //写入其它类型数据，此时，将会从position位置开始覆盖缓冲区数据
        buf.putInt(257);//position:5 limit:6
        //读取写入的int，需要从覆写的起始位置开始读取
        System.out.println(buf.getInt(1));
//---------------------------------------position(int np) And limit(int nl)--------------------------

        buf.position(1);//手动设置position为1
        buf.limit(100);//手动设置limit为100
        System.out.println("position:"+buf.position()+"--"+"limit:"+buf.limit());
//---------------------------------------clear()-------------------------------------------------------

        //可用于转换读写状态，position=0,limit=capacity 但数据仍在，可以通过显示指定索引得到值
        buf.clear();
        System.out.println("position:"+buf.position()+"--"+"limit:"+buf.limit());



    }
}
