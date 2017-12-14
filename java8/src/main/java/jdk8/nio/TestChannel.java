package jdk8.nio;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.*;

/**
 * 一、Channel：表示与数据源之间的连接，或者用于IO操作的一个组件。它本身不具备存储功能，一般配合缓冲区使用
 * <p>
 * 二、Channel的主要实现类：
 * java.nio.channels.Channel:
 * |--FileChannel
 * |--SocketChannel
 * |--ServerSocketChannel
 * |--DatagramChannel
 * 三、获取通道：
 * 1.Java针对支持通道的类提供了getChannel方法
 * 本地IO
 * |--FileInputStream、FileOutPutStream
 * |--RandomAccessFile
 * 网络IO
 * |--Socket
 * |--ServerSocket
 * |--DatagramSocket
 * <p>
 * 2.JDK1.7中 NIO.2针对各个通道提供了一个静态方法：open()
 * 3.JDK1.7中 NIO.2的Files工具类的newByteChannel()
 * <p>
 * 四：通道之间直接数据传输
 * transferFrom
 * transferTo
 * <p>
 * 五、分散与聚集 ：
 * 分散读取：将Channel中的数据分散到各个缓冲区中 按缓冲区顺序依次写入
 * 聚集写入：将多个缓冲区的数据聚集写入到一个Channel中 按缓冲区顺序依次读取
 *
 * 六:字符集：CharSet
 *  编码：字符串->字节数组
 *  解码：字节数组->字符串
 */
public class TestChannel {
    String path = System.getProperty("user.dir") + "/dir";

    //字符集测试
    @Test
    public void test4() {

    }


    //分散读取与聚集写入
    @Test
    public void test3() {
        try (RandomAccessFile fromFile = new RandomAccessFile(path + "/from.txt", "rw");
             RandomAccessFile toFile = new RandomAccessFile(path + "/to.txt", "rw");
             FileChannel inChannel = fromFile.getChannel();
             FileChannel outChannel = toFile.getChannel()) {

            ByteBuffer buf1 = ByteBuffer.allocate(100);
            ByteBuffer buf2 = ByteBuffer.allocate((int)inChannel.size()-100);
            ByteBuffer[] bufs = new ByteBuffer[]{buf1,buf2};

            inChannel.read(bufs);

            for (ByteBuffer buf : bufs) {
                System.out.println(new String(buf.array(),0,buf.limit()));
                System.out.println("---------------------------缓冲区分割线----------------------------");
            }

            //设置position为0，便于读取缓冲区到Channel中
            for (ByteBuffer buf : bufs) {
                buf.flip();
            }
            outChannel.write(bufs);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //使用通道间的直接数据传输==使用直接缓存 等价于allocateDirect()
    @Test
    public void test2() {
        try (FileChannel inChannel = FileChannel.open(Paths.get(path + "/1.png"), READ);
             FileChannel outChannel = FileChannel.open(Paths.get(path + "/4.png"), READ, WRITE, CREATE)) {

            inChannel.transferTo(0, inChannel.size(), outChannel);
            outChannel.transferFrom(inChannel, 0, inChannel.size());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //利用通道的静态方法open(),映射内存文件==使用直接缓存 等价于allocateDirect()
    @Test
    public void test1() {
        try (FileChannel inChannel = FileChannel.open(Paths.get(path + "/1.png"), READ);
             FileChannel outChannel = FileChannel.open(Paths.get(path + "/3.png"), READ, WRITE, CREATE)) {

            //内存映射文件 缓冲区
            MappedByteBuffer inMapBuf = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
            MappedByteBuffer outMapBuf = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());

            //直接对缓冲区进行读写
            outMapBuf.put(inMapBuf);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //在流的基础上获取Channel
    @Test
    public void test0() {
        //得到的是编译后的路径
        ByteBuffer buffer;
        System.out.println();
        try (FileInputStream in = new FileInputStream(path + "/1.png");
             FileOutputStream out = new FileOutputStream(path + "/2.png");
             FileChannel inChannel = in.getChannel();
             FileChannel outChannel = out.getChannel()) {
            buffer = ByteBuffer.allocate(1024);
            while (inChannel.read(buffer) != -1) {
                buffer.flip();
                outChannel.write(buffer);
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
