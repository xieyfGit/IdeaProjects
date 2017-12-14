package jdk8.juc;

import java.time.Instant;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestOrderThreads2 {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(300);
        OrderThreadDemo2 otd = new OrderThreadDemo2(countDownLatch);

        Thread threadA = new Thread(otd, "Thread-A");
        Thread threadB = new Thread(otd, "Thread-B");
        Thread threadC = new Thread(otd, "Thread-C");

        long start = Instant.now().toEpochMilli();
        threadB.start();
        threadC.start();
        threadA.start();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = Instant.now().toEpochMilli();
        System.out.println("耗时：" + (end - start) + "ms");
    }
}

class OrderThreadDemo2 implements Runnable {
    private int order = 1;
    private Lock lock = new ReentrantLock(false);
    private Condition condition0 = lock.newCondition();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private CountDownLatch countDownLatch;

    public OrderThreadDemo2(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 100; i++) {
            print(i);
        }
    }

    private void print(int loop) {
        lock.lock();
        try {
            String name = Thread.currentThread().getName();
            switch (name) {
                case "Thread-A":
                    if (order != 1) {
                        condition0.await();
                    }
                    System.out.println(name + "-第-" + loop + " 轮");
                    order = 2;
                    condition1.signal();
                    countDownLatch.countDown();
                    break;

                case "Thread-B":
                    if (order != 2) {
                        condition1.await();
                    }
                    System.out.println(name + "-第-" + loop + " 轮");
                    order = 3;
                    condition2.signal();
                    countDownLatch.countDown();
                    break;

                case "Thread-C":
                    if (order != 3) {
                        condition2.await();
                    }
                    order = 1;
                    System.out.println(name + "-第-" + loop + " 轮");
                    condition0.signal();
                    countDownLatch.countDown();
                    break;
                default:
                    break;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
}