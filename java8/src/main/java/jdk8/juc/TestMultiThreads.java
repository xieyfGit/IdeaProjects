package jdk8.juc;

import java.time.Instant;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 创建多线程方式：
 * 1.实现Runnable接口
 * 2.继承Thread
 * 3.实现Callable接口 --方法可以有返回值，且可以抛出异常
 * 4.使用线程池
 */
public class TestMultiThreads {

    public static void main(String[] args) {
        CallableDemo callableDemo = new CallableDemo();

        //CallableDemo的执行需要FutureTask 实现类支持 -->用于接收运行结果
        FutureTask<Integer> task = new FutureTask<>(callableDemo);
        FutureTask<Integer> task2 = new FutureTask<>(callableDemo);
        long start = Instant.now().toEpochMilli();
        new Thread(task).start();
        new Thread(task2).start();

        //接口运行接口
        try {
            Integer sum = task.get();
            Integer sum2 = task2.get();
            System.out.println(sum);
            System.out.println(sum2);
            long end = Instant.now().toEpochMilli();
            System.out.println("-----耗时："+(end-start)+" ms----------------------------------");//可用于闭锁
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }
}
class CallableDemo implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        Thread.currentThread().sleep(3000);
        int sum = 0;
        for (int i = 0; i < Integer.MAX_VALUE ; i++) {
            sum+=i;
        }
        return sum;
    }
}

class ThreadDemo extends Thread{
    @Override
    public void run() {

    }
}

class RunableDemo implements Runnable {

    @Override
    public void run() {

    }
}

