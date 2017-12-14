package jdk8.juc;

/**
 * 1.两个普通同步方法，两个线程打印                    // one -> two
 * 2.新增sleep给getOne方法                           //two ->one
 * 3.新增普通非同步方法getThree                       // three -> one -> two
 * 4.两个线程使用不同的Thread8Demo对象                //three <--> two -> one
 * 5.修改getOne为静态同步方法                         //three <--> two -> one
 * 6.修改两个都为静态同步方法，使用任意对象             //three -> one -> two
 * 7.一个静态同步方法，一个非静态同步方法，两个对象      //three <--> two -> one
 * 8.修改两个都为静态同步方法，两个对象                //three -> one -> two
 *
 * 总结：
 * 1.静态同步方法是对Class对象加锁，而非静态同步方法是对实例对象加锁
 * 2.某一个时刻内，有且仅有一个线程持有锁，与方法数量无关
 */
public class TestThread8Monitor {

    public static void main(String[] args) {
        Thread8Demo thread8Demo = new Thread8Demo();
        Thread8Demo thread8Demo2 = new Thread8Demo();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                thread8Demo.getOne();
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                thread8Demo.getTwo();
            }
        });
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                thread8Demo.getThree();
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
    }
}

class Thread8Demo {

    public static synchronized void getOne() {
        try {
            Thread.currentThread().sleep(3000);
        } catch (InterruptedException e) {
        }
        System.out.println("one");
    }

    public  static synchronized void getTwo() {
        System.out.println("two");
    }

    public void getThree() {
        System.out.println("three");
    }
}