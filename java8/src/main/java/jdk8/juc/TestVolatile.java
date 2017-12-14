package jdk8.juc;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 一：volatile：当多个线程进行共享数据操作时，可以保证内存中的数据对相互可见
 * 相较于synchronized是一种更轻量级的同步策略
 * <p>
 * 注意：
 * 1.对多线程操作不具备 "互斥性"
 * 2.不能保证修饰变量的 "原子性"
 * <p>
 * 二：对于原子性问题的解决：JDK1.5后 java.util.concurrent 包下提供了常用的原子变量类型
 * 1.内部属性使用了 volatile保证内存可见性
 * 2.CAS(Compare-And-Swap)算法保证了操作的原子性
 * CAS是底层硬件对于并发操作共享数据的支持，包含三个操作数：
 * V:内存值 ->操作之前读取初始数据，并进行自定义的计算
 * A:预估值 ->更新内存之前再读取一次内存此时的数据
 * B:更新值 ->若两次读取的数据相等，则更新
 * 当且仅当V==A 时，才将V更新为B，否则不做任何操作
 *
 *
 * 三：CopyOnWriteArrayList\CopyOnWriteArraySet:"写入并复制"
 * 注意：添加操作时，效率低，因为每次添加都会进行复制，开销非常大。并发迭代操作多时可以选择
 *
 *四：分段锁 ：ConcurrentHashMap,ConcurrentSkipListMap,ConcurrentSkipListSet
 *  并发访问Collection时：ConcurrentHashMap优于HashMap;ConcurrentSkipListMap优于TreeMap
 *  当期望的读数远远大于更新数时,CopyOnWriteArrayList优于同步的ArrayList
 *
 * 五：闭锁：CountDownLatch一个同步辅助类，在完成一组正在进行的其它线程中执行的操作之前，它允许一个或多个线程一致等待
 *  业务意义：确保在某些服务初始化完成后在执行后续操作
 *
 */
public class TestVolatile {

    //使用volatile同步策略
    public static void test0() {
        MyThread mt = new MyThread();
        Thread thread = new Thread(mt);
        thread.start();
        while (true) {
            if (mt.isFlag()) {
                System.out.println("-----------------------");
                break;
            }
        }
    }

    //使用synchronized同步策略
    public static void test1() {
        MyThread mt = new MyThread();
        Thread thread = new Thread(mt);
        thread.start();
        while (true) {
            synchronized (mt) {
                if (mt.isFlag()) {
                    System.out.println("-----------------------");
                    break;
                }
            }
        }
    }

    //使用原子变量类型解决原子性问题
    //CopyOnWriteArrayList与Collections.synchronizedList比较
    public static void test2(){
        MyThread myThread = new MyThread();
        for (int i = 0; i < 10; i++) {
            new Thread(myThread).start();
        }
    }

    public static void main(String[] args) {
        test2();
    }

}

class MyThread implements Runnable {

    private volatile boolean flag;
    private AtomicInteger atomicInteger = new AtomicInteger(0);
    private int i = 0;
    //java.util.ConcurrentModificationException
//    private List<String> list = Collections.synchronizedList(new ArrayList<>());
    //但是效率低
    private CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

    {
        list.add("AA");
        list.add("BB");
        list.add("CC");
    }

    @Override
    public void run() {
        //volatile测试
//        this.flag = true;
//        System.out.println("flag is " + flag);
        //非原子变量并发测试
//        try {
////            Thread.currentThread().sleep(200);
////        } catch (InterruptedException e) {
////        }
////        System.out.println(i++);
        //原子变量并发测试
//        System.out.println(getAndIncrement());
        //CopyOnWriteArrayList测试
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
            list.add("DD");
        }
        System.out.println(list.size());
    }


    private int getAndIncrement() {
        return atomicInteger.getAndIncrement();
    }

    public boolean isFlag() {
        return flag;
    }
}
