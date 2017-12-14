package jdk8.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 解决多线程安全问题：
 * jdk1.5之前：synchronized 隐式锁
 * 1.同步代码块
 * 2.同步方法
 *
 * jdk1.5之后：更灵活但是有风险-->锁未释放
 * 3.同步锁Lock ：是一个显示的锁需要通过lock方法上锁，通过unlock方法释放锁
 *
 * Lock lock = new ReentrantLock(boolean fair)
 * 默认fair为false，即非公平锁，允许插队，效率更高。原因：在恢复一个被挂起的线程与该线程真正运行之间存在着严重的延迟。
 * 公平锁：当锁被持有或者等待队列不为空时，新请求按顺序加入到队列中，不允许插队
 * 非公平锁：仅当锁被持有时，新请求才被加入队列中
 */
public class TestLock {

    public static void main(String[] args) {
        TicktDemo ticktDemo  = new TicktDemo();

        Thread thread1 = new Thread(ticktDemo,"一号窗口");
        Thread thread2 = new Thread(ticktDemo,"二号窗口");
        Thread thread3 = new Thread(ticktDemo,"三号窗口");

        thread1.start();
        thread2.start();
        thread3.start();

    }

}

class TicktDemo implements Runnable {

    private /*volatile*/ int ticket = 10;
    private Lock lock = new ReentrantLock();

    @Override
    public void run() {
        while(true){
            lock.lock();
            try {
                if (ticket > 0) {
                    Thread.currentThread().sleep(100);
                    System.out.println(Thread.currentThread().getName() + "余票：" + --ticket);
                } else {
                    break;
                }
            } catch (InterruptedException e) {
            } finally {
                lock.unlock();
            }
        }

    }
}
