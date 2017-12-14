package jdk8.juc;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * 线程池：提供一个线程队列，队列中保存着所有等待状态的线程。避免了线程创建与销毁的额外开销，提高了响应速度。
 * <p>
 * 线程池体系结构：
 * java.util.concurrent.Executor:负责线程的使用与调度的根接口
 * |--*ExecutorService 子接口：线程池的主要接口
 * |--ThreadPoolExecutor   线程池实现类
 * |--ScheduledExecutorService 子接口：负责线程调度
 * |--ScheduledThreadPoolExecutor 继承ThreadPoolExecutor，实现ScheduledExecutorService
 * 工具类：Executors
 * ExecutorService newFixedThreadPool(int size) :创建指定数量线程的线程池
 * ExecutorService newCachedThreadPool() :缓存线程池，线程数量不固定，可以根据需求自动更改
 * ExecutorService newSingledThreadExecutor() :创建单线程线程池
 * <p>
 * ScheduledExecutorService newScheduledThreadPool(int size) :创建固定大小线程池，可以延迟或定时执行任务
 */
public class TestThreadPool {


    public static void main(String[] args) throws InterruptedException {
//        test0();
        test1();

    }
    //线程调度测试
    private static void test1() {
        final ScheduledExecutorService scheduler =
                Executors.newScheduledThreadPool(1);
        final Runnable beeper = new Runnable() {
            public void run() {
                try {
                    Thread.currentThread().sleep(3000);
                } catch (InterruptedException e) {
                }
                System.out.println("beep");
            }
        };
        final Runnable beeper2 = new Runnable() {
            public void run() {
//                try {
//                    Thread.currentThread().sleep(3000);
//                } catch (InterruptedException e) {
//                }
                System.out.println("beep2----------------------------");
            }
        };
//        final ScheduledFuture<?> beeperHandle =
                //优先保证任务执行频率
                scheduler.scheduleAtFixedRate(beeper, 3, 1, SECONDS);
                //优先保证任务执行间隔
                scheduler.scheduleWithFixedDelay(beeper2, 3, 1, SECONDS);

//        scheduler.schedule(new Runnable() {
//            public void run() {
//                beeperHandle.cancel(true);
//            }
//        }, 60 * 60, SECONDS);
    }

    //普通线程池测试
    private static void test0() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(100);
        long start = Instant.now().toEpochMilli();
        ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
        //额，单线程用时居然最短，说明多线程是一把双面刃
//        ExecutorService service = Executors.newSingleThreadExecutor();
        //推荐，可控。
//        ExecutorService service = Executors.newFixedThreadPool(40);
        //不推荐，缓存线程池线程数量不可控，那么对于相对轻量的逻辑而言，线程切换损耗大于效率收益
//        ExecutorService service = Executors.newCachedThreadPool();

//        service.submit(new Runnable() {
//            @Override
//            public void run() {
//                int sum = 10;
//                for (int i = 0; i < 100; i++) {
//                    sum++;
//                }
//                System.out.println(Thread.currentThread().getName()+":"+sum);
//            }
//        });
        List<Future<Long>> list = new LinkedList<>();

        for (int i = 0; i < 100; i++) {
            //普通并行任务
//           Future<Long > future = service.submit(new Callable<Long>() {
            //延时任务
            Future<Long> future = service.schedule(new Callable<Long>() {
                @Override
                public Long call() throws Exception {
                    long sum = 1;
                    for (int j = 1; j < 60; j++) {
                        sum *= j;
                        System.out.println(Thread.currentThread().getName() + ":" + sum);
                    }
                    latch.countDown();
                    return sum;
                }
            }, 1000, TimeUnit.MILLISECONDS);
            list.add(future);
        }

        service.shutdown();
        latch.await();
        long end = Instant.now().toEpochMilli();
        System.out.println("耗时:" + (end - start) + "ms");
//        for (Future<Long> future : list) {
//            System.out.println(future.get());
//        }
    }
}
