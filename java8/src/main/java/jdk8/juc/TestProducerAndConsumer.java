package jdk8.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产-消费模式实现方式：
 * 1.睡眠-唤醒
 * 2.同步锁方式
 */
//==================================================================睡眠-唤醒 方式
//public class TestProducerAndConsumer {
//
//    public static void main(String[] args) {
//        Clerk clerk = new Clerk();
//
//        Producer producer = new Producer(clerk);
//        Consumer consumer = new Consumer(clerk);
//
//        new Thread(consumer, "消费者A").start();
//        new Thread(consumer, "消费者B").start();
//        new Thread(producer, "生产者A").start();
//        new Thread(producer, "生产者B").start();
//    }
//
//}
//
////售货员
//class Clerk {
//    //此时使用volatile无效，因为不能保证原子性
////    private volatile int stock =1;
//    //使用原子变量来保证原子性
////    private AtomicInteger  stock =new AtomicInteger(1);
//    //使用synchronized来保证同步
//    private int stock;
//
//    //进货 因为有多个生产者，所以需要synchronized对当前对象加锁,
//    public synchronized void store() throws InterruptedException {
//        while (stock >= 1) {//使用wile而不是if，避免虚假唤醒：即多个线程同时被唤醒，此时进入循环判断一次
////        if (stock.get() >= 10) {
//            System.out.println("货舱已满...");
//            this.wait();  //释放锁--等待
//        } /*else {*/
//        System.out.println(Thread.currentThread().getName() + " : " + ++stock);
////            System.out.println(Thread.currentThread().getName() + " : " + stock.getAndIncrement());
//        this.notifyAll();
//        /* }*/
//    }
//
//    //售货 因为有多个消费者，所以需要synchronized对当前对象加锁
//    public synchronized void sale() throws InterruptedException {
//        while (stock <= 0) {//使用wile而不是if，避免虚假唤醒：即多个线程同时被唤醒，此时进入循环判断一次
////        if (stock.get() <= 0) {
//            System.out.println("等待进货...");
//            this.wait();
//        } /*else {*/  //else判断可能会导致线程无法结束-->当stock<=0，且循环次数恰好没有了，则无法唤醒生产者等待线程
//        System.out.println(Thread.currentThread().getName() + " : " + --stock);
////            System.out.println(Thread.currentThread().getName() + " : " + stock.getAndDecrement());
//        this.notifyAll();
//        /* }*/
//    }
//
//
//}
//
////生产者
//class Producer implements Runnable {
//    private Clerk clerk;
//
//    public Producer(Clerk clerk) {
//        this.clerk = clerk;
//    }
//
//    @Override
//    public void run() {
//        for (int i = 0; i < 20; i++) {
//            try {
//                Thread.currentThread().sleep(200);//sleep 持有锁--等待
//                clerk.store();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
//
////消费者
//class Consumer implements Runnable {
//
//    private Clerk clerk;
//
//    public Consumer(Clerk clerk) {
//        this.clerk = clerk;
//    }
//
//    @Override
//    public void run() {
//        for (int i = 0; i < 20; i++) {
//            try {
//                Thread.currentThread().sleep(200);
//                clerk.sale();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
//=======================================================================同步锁方式
public class TestProducerAndConsumer {

    public static void main(String[] args) {
        Clerk clerk = new Clerk();

        Producer producer = new Producer(clerk);
        Consumer consumer = new Consumer(clerk);

        new Thread(consumer, "消费者A").start();
        new Thread(consumer, "消费者B").start();
        new Thread(producer, "生产者A").start();
        new Thread(producer, "生产者B").start();
    }

}

//售货员
class Clerk {

    private int stock;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void store() throws InterruptedException {
        lock.lock();
        try {
            while (stock >= 1) {//使用wile而不是if，避免虚假唤醒：即多个线程同时被唤醒，此时进入循环判断一次
                System.out.println("货舱已满...");
                condition.await();
            }
            System.out.println(Thread.currentThread().getName() + " : " + ++stock);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void sale() throws InterruptedException {
        lock.lock();
        try {
            while (stock <= 0) {//使用wile而不是if，避免虚假唤醒：即多个线程同时被唤醒，此时进入循环判断一次
                System.out.println("等待进货...");
                condition.await();
            }
                System.out.println(Thread.currentThread().getName() + " : " + --stock);
                condition.signalAll();

        } finally {
            lock.unlock();
        }

    }
}

//生产者
class Producer implements Runnable {
    private Clerk clerk;

    public Producer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            try {
                Thread.currentThread().sleep(200);//sleep 持有锁--等待
                clerk.store();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

//消费者
class Consumer implements Runnable {

    private Clerk clerk;

    public Consumer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            try {
                Thread.currentThread().sleep(200);
                clerk.sale();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}