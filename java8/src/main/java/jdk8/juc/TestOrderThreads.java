package jdk8.juc;

import java.time.Instant;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程按序交替
 */
public class TestOrderThreads {
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(300);
        OrderThreadDemo otd = new OrderThreadDemo(countDownLatch);
        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 100; i++) {
                    otd.printB(i);
                }
            }
        }, "Thread-B");
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 100; i++) {
                    otd.printA(i);
                }

            }
        }, "Thread-A");
        Thread threadC = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 100; i++) {
                    otd.printC(i);
                }
            }
        }, "Thread-C");

        long start = Instant.now().toEpochMilli();
        threadB.start();
        threadA.start();
        threadC.start();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = Instant.now().toEpochMilli();
        System.out.println("耗时："+(end-start)+"ms");
    }
}

class OrderThreadDemo {
    private int order = 1;
    private Lock lock = new ReentrantLock(true);
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();
    private CountDownLatch countDownLatch;

    public OrderThreadDemo(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public void printA(int loop) {
        lock.lock();
        try {
            while (order != 1) {
                condition1.await();//释放当前条件关联的锁，并休眠当前线程，直到调用当前条件的signal方法且当前线程被选中为执行线程
            }
            System.out.println(Thread.currentThread().getName() + "第 " + loop + " 轮");
            order = 2;
            condition2.signal();
            countDownLatch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printB(int loop) {
        lock.lock();
        try {
            while (order != 2) {
                condition2.await();
            }
            System.out.println(Thread.currentThread().getName() + "第 " + loop + " 轮");
            order = 3;
            condition3.signal();
            countDownLatch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printC(int loop) {
        lock.lock();
        try {
            while (order != 3) {
                condition3.await();
            }
            System.out.println(Thread.currentThread().getName() + "第 " + loop + " 轮");
            order = 1;
            condition1.signal();
            countDownLatch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

