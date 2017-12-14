package jdk8.juc;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁：ReadWriteLock
 * 1.读写/写写  需要保证互斥
 * 2.读读  不需要互斥
 */
public class TestReadWriteLock {

    public static void main(String[] args) {
        ReadWriteLockDemo demo = new ReadWriteLockDemo();

        new Thread(new Runnable() {
            @Override
            public void run() {
                demo.write(new Random().nextInt());
            }
        },"Write:").start();

        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    demo.read();
                }
            },"Read:").start();
        }
    }

}

class ReadWriteLockDemo {
    private int num;
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    public int read() {
        lock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+num);
            return num;
        } finally {
            lock.readLock().unlock();
        }
    }

    public void write(int num) {
        lock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+num);
            this.num = num;
            System.out.println("------------------write complete!-----------------------");
        } finally {
            lock.writeLock().unlock();
        }
    }

}
