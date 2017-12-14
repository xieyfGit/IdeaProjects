package jdk8.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

/**
 * DatagramChannel 通过send发送缓冲区，receive或者read(T buf)接收缓冲区，超出缓冲区容量的数据会被抛弃 T指的是? extend Buffer
 *
 */
public class TestDatagramChannel {

    @Test
    public void send() {
        try (DatagramChannel dc = DatagramChannel.open()) {
            dc.configureBlocking(false);
            ByteBuffer buf = ByteBuffer.allocate(1024);
            buf.put("发送一个UDP包...".getBytes());
            buf.flip();
            dc.send(buf, new InetSocketAddress("127.0.0.1", 9090));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void receive() {
        try (DatagramChannel dc = DatagramChannel.open()) {
            dc.configureBlocking(false);
            ByteBuffer buf = ByteBuffer.allocate(10);
            dc.bind(new InetSocketAddress(9090));
            Selector selector = Selector.open();
            dc.register(selector, SelectionKey.OP_READ);
            while (selector.select() > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isReadable()) {
                        DatagramChannel dc0 = (DatagramChannel) key.channel();
                        //报错，因为DatagramChannel并不会维持连接
//                        int len = 0;
//                        while ((len = dc0.read(buf))>0) {
//                            buf.flip();
//                            System.out.println(new String(buf.array(),0,len));
//                            buf.clear();
//                        }

                        dc.receive(buf);
                        int len = buf.position();
                        buf.flip();
                        System.out.println(new String(buf.array(), 0, len));
                        buf.clear();

//                        dc.receive(buf);
//                        int len = buf.position();
//                        buf.flip();
//                        System.out.println(new String(buf.array(),0,len));
//                        buf.clear();

                    }
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
