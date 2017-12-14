package jdk8.juc;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 *  CountDownLatch一个同步辅助类，在完成一组正在进行的其它线程中执行的操作之前，它允许一个或多个线程一致等待
 *  业务意义：确保在某些服务初始化完成后在执行后续操作
 */
public class TestCountDownLatch {

    public static void main(String[] args) {
        final CountDownLatch latch = new CountDownLatch(15);
        List<LatchDemo> list = new ArrayList<>();
        list.add(new LatchDemo(latch,500));
        list.add(new LatchDemo(latch,1000));
        list.add(new LatchDemo(latch,5000));

        long start = Instant.now().toEpochMilli();

        for (int i = 0; i < 5; i++) {
            for (LatchDemo latchDemo : list) {
                new Thread(latchDemo).start();
            }
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
        }
        long end = Instant.now().toEpochMilli();
        System.out.println("耗时-ms:"+(end-start));

    }
}

class LatchDemo implements Runnable {

    private CountDownLatch latch;
    private int end;

    public LatchDemo() {
    }

    public LatchDemo(CountDownLatch latch,int end) {
        this.latch = latch;
        this.end = end;
    }

    @Override
    public void run() {
        synchronized (this) {
            try {
                for (int i = 0; i <end ; i++) {
                    if (i%2==0) {
                        System.out.println(Thread.currentThread().getName()+"->>"+i);
                    }
                }
            }finally {
                latch.countDown();
            }

        }
    }
}
