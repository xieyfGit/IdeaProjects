package jdk8.nio;

import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * Pipe：用于线程间传输数据的管道
 * Pipe.SinkChannel和Pipe.SourceChannel总是成对出现：一个负责写入，一个负责读取
 */
public class TestPipe {
    @Test
    public void test() throws IOException {
        Pipe pipe = Pipe.open();
        try (Pipe.SinkChannel inChannel = pipe.sink();
        Pipe.SourceChannel outChannel = pipe.source()) {
            ByteBuffer buf = ByteBuffer.allocate(1024);
            buf.put("pipe 发送一段文字...".getBytes());
            buf.flip();
            inChannel.write(buf);
            buf.clear();

            outChannel.read(buf);
            int len = buf.position();
            buf.flip();
            System.out.println(new String(buf.array(),0,len));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
