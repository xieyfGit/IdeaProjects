package jdk8.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 阻塞式IO重现
 */
public class TestBlockIO {

    String path = System.getProperty("user.dir") + "/dir";

    @Test
    public void client() {

        try (SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9090));
             FileChannel inChannel = FileChannel.open(Paths.get(path + "/1.png"), StandardOpenOption.READ)) {

            ByteBuffer buf = ByteBuffer.allocate(1024);

            while (inChannel.read(buf) != -1) {
                buf.flip();
                sChannel.write(buf);
                buf.clear();
            }

            sChannel.shutdownOutput();
            buf.clear();
            while (sChannel.read(buf) != -1) {
                buf.flip();
                System.out.println(buf.position()+"---"+buf.limit());
                System.out.println(new String(buf.array(), 0, buf.limit()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void server() {
        try (ServerSocketChannel ssChannel = ServerSocketChannel.open();
             FileChannel outChannel = FileChannel.open(Paths.get(path + "/6.png"), StandardOpenOption.WRITE, StandardOpenOption.CREATE)) {

            ssChannel.bind(new InetSocketAddress(9090));
            ByteBuffer buf = ByteBuffer.allocate(1024);
            try (SocketChannel sChannel = ssChannel.accept()) {
                while (sChannel.read(buf)!=-1) {
                    buf.flip();
                    outChannel.write(buf);
                    buf.clear();
                }

                buf.put("服务器端接收图片成功!".getBytes());
                buf.flip();
                sChannel.write(buf);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
