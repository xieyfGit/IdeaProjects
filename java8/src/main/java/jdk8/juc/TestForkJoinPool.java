package jdk8.juc;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * Fork/Join :分支合并框架
 */
public class TestForkJoinPool {

    @Test
    public void jdk8Lambda() {
        Instant start = Instant.now();
       long sum = LongStream.rangeClosed(0,1000000000)
                .parallel()
                .reduce(0,Long::sum);
        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println("耗时:"+ Duration.between(start,end).toMillis());
    }


    @Test
    public void forkJoin() {
        Instant start = Instant.now();
        ForkJoinPool pool = new ForkJoinPool();

        ForkJoinTask<Long> task = new forkJoinDemo(0L, 1000000000);

        Long sum = pool.invoke(task);

        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println("耗时:"+ Duration.between(start,end).toMillis());
    }

    @Test
    public void recursiveSum() {
        Instant start = Instant.now();
        new recursiveSum().sum(0, 1000000000);
        System.out.println(recursiveSum.total);
        Instant end = Instant.now();
        System.out.println("耗时:"+ Duration.between(start,end).toMillis());
    }

    @Test
    public void common() {
        Instant start = Instant.now();
        long sum0 = 0;
        for (long i = 0; i <= 1000000000; i++) {
            sum0 += i;
        }
        System.out.println(sum0);
        Instant end = Instant.now();
        System.out.println("耗时:"+ Duration.between(start,end).toMillis());
    }
}

class recursiveSum {
    private static final long CRITICAL = 100000;//临界值
    public static long total = 0;

    public void sum(long start, long end) {
        long length = end - start;
        long sum = 0;
        if (length > CRITICAL) {
            long mid = (end + start) / 2;
            sum(start, mid);
            sum(mid + 1, end);
        }else {
            for (long i = start; i <= end; i++) {
                sum += i;
            }
        }
        total += sum;
    }
}

class forkJoinDemo extends RecursiveTask<Long> {

    private static final long serialVersionUID = -626616676272656856L;
    private static final long CRITICAL = 100000L;//临界值
    private long start;
    private long end;

    forkJoinDemo(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long length = end - start;
        if (length <= CRITICAL) {
            long sum = 0L;
            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        } else {
            long mid = (start + end) / 2;
            forkJoinDemo left = new forkJoinDemo(start, mid);
            left.fork();//进行拆分，同时压入线程队列

            forkJoinDemo right = new forkJoinDemo(mid + 1, end);
            right.fork();//进行拆分，同时压入线程队列

            Long leftSum = left.join();
            Long rightSum = right.join();

            return leftSum + rightSum;//合并
        }
    }
}