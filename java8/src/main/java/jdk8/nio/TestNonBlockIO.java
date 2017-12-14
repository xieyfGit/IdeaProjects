package jdk8.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Iterator;

/**
 * 非阻塞式NIO 主要针对网络IO
 * <p>
 * Selector:选择器--负责SelectableChannel的多路复用器。监控SelectableChannel的IO状态
 * java.nio.channels.Channel
 * |--SelectableChannel
 * |--SocketChannel
 * |--ServerSocketChannel
 * |--DatagramChannel
 * <p>
 *     java.nio.channels.Pipe 线程间的单向通道,由一个可读管道和可写管道组成
 * |--Pipe.SinkChannel 可写管道，向缓冲区写数据
 * |--Pipe.SourceChannel. 可读管道，从缓冲区读取数据
 *
 * SelectionKey：一个代号，用于表示某个Channel到某个Selector的注册监听相关信息，并不是所有Selectable实现都支持所有SelectionKey定义的ops，
 *                  如SocketChannel不支持SelectionKey.OP_ACCEPT，而ServerSocketChannel支持。可以通过channel.validOps()来查看.
 *      每个代号包含两个操作集(元素为注册的事件，如：OP_READ)，分别为interest-set和ready-set
 *          interest-set为selector监控ops:在注册的时候根据指定的参数确定，也可以通过注册返回的SelectionKey对象的interestOps(int ops)添加
 *          ready-set为准备就绪的ops：由selector检测到注册的channel中已经准备就绪的 ops集合，仅仅是暗示，但不保证一定准确。其它外部事件或者该通道上的IO操作可能会影响其准确性。
 */
public class TestNonBlockIO {
    String path = System.getProperty("user.dir") + "/dir";

    @Test
    public void client() {

        try (SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9090))) {

            ByteBuffer buf = ByteBuffer.allocate(1024);
            sChannel.configureBlocking(false);
            Selector selector = Selector.open();

            SelectionKey skey =sChannel.register(selector,SelectionKey.OP_WRITE,"attr");
            System.out.println(skey.readyOps());//4  SelectionKey.OP_WRITE
            System.out.println("-----------------------");
            System.out.println(skey.interestOps());//4 SelectionKey.OP_WRITE
            System.out.println(skey.attachment());

            buf.put(LocalDateTime.now().toString().getBytes());
            buf.flip();
            sChannel.write(buf);
            buf.clear();

//            if (selector.select() > 0) {
//                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
//                while (iterator.hasNext()) {
//                    SelectionKey key = iterator.next();
//                    System.out.println(key.readyOps());//4  SelectionKey.OP_WRITE
//                    System.out.println("-----------------------");
//                    System.out.println(key.interestOps());//4 SelectionKey.OP_WRITE
//                    System.out.println(key.attachment());
//                    if (key.isWritable()) {
//                        buf.put(LocalDateTime.now().toString().getBytes());
//                        buf.flip();
//                        sChannel.write(buf);
//                        buf.clear();
//                    }
//                    iterator.remove();
//                }
//
//            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void server() {
        try (ServerSocketChannel ssChannel = ServerSocketChannel.open()) {

            //设置通道为非阻塞模式
            ssChannel.configureBlocking(false);
            //绑定端口
            ssChannel.bind(new InetSocketAddress(9090));
            //获取选择器
//            Selector selector = WindowsSelectorProvider.provider().openSelector();
            Selector selector = Selector.open();
            //注册通道到选择器，并且指定监听事件，并将
            SelectionKey sk = ssChannel.register(selector,SelectionKey.OP_ACCEPT);
            //可以手动设置监听事件 interest-set
//            sk.interestOps(SelectionKey.OP_ACCEPT);
//            sk.attach("attr");

            //轮询获取选择器上已经准备就绪的事件，selector.select()会阻塞在这直到对应通道某个准备就绪事件发生
            while (selector.select() > 0) {
                //selector.selectedKeys()获取已经准备就绪的事件SelectionKey set也就是readySet
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    //获取准备就绪的事件
                    SelectionKey key = iterator.next();
//                    System.out.println(key.readyOps());//1 SelectionKey.OP_ACCEPT
//                    System.out.println(key.interestOps());//1 SelectionKey.OP_ACCEPT
//                    System.out.println(key.attachment());
                    if (key.isAcceptable()) {
                        SocketChannel sChannel = ssChannel.accept();
                        sChannel.configureBlocking(false);
                        sChannel.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        SocketChannel sChannel = (SocketChannel) key.channel();
                        ByteBuffer buf = ByteBuffer.allocate(1024);
                        int len = 0;
                        while ((len = sChannel.read(buf)) > 0) {
                            buf.flip();
                            System.out.println(new String(buf.array(), 0, buf.limit()));
                            buf.clear();
                        }
                    }
                    //取消准备就绪键，将其从selected-keySet中移除
                    iterator.remove();
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
