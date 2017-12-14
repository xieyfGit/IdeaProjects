package jdk8.newfeature.lambda;


import org.junit.Test;

import java.util.Comparator;
import java.util.function.Consumer;

@FunctionalInterface
interface MathInterface<T> {
    Integer getValue(Integer num);

    default boolean getFlag(Object obj) {
        return false;
    }
}

/**
 * 一:Lambda表达式基础语法
 * java8中引入了一个新操作符 "->" 该操作符称为箭头操作符或者Lambda操作符
 * "->"左侧：Lambda表达式的参数列表
 * "->"右侧 Lambda表达式所需执行的功能，即：Lambda 体
 * <p>
 * 语法格式:
 * 1.无参数，无返回值
 * () -> System.out.println("Hello Lambda!");
 * 2.有参数一个参数，无返回值
 * (x) -> System.out.println(x);
 * 3.若只有一个参数，小括号可以省略
 * x -> System.out.println(x);
 * 4.有两个以上的参数，有返回值且lambda体中有多条语句,此时Lambda体必须使用{}
 * Comparator<Integer> comparator = (x,y)-> {
 * System.out.println("函数式接口：仅有一个方法的接口");
 * return Integer.compare(x,y);
 * };
 * 5.有两个以上的参数，有返回值且lambda体中只有一条语句，则return和{}都可以省略
 * Comparator<Integer> comparator = (x, y) -> Integer.compare(x, y);
 * 6.Lambda表达式的参数列表的数据类型可以省略，因为JVM编译器会通过上下文、目标 推断出类型：即类型推断
 * Comparator<Integer> comparator = (Integer x, Integer y) -> Integer.compare(x, y);
 * <p>
 * 记忆口诀：
 * 上联：左右遇一括号省
 * 下联：左侧推断类型省
 * 横批：能省则省
 * <p>
 * 二:Lambda表达式需要函数式接口支持
 * 函数式接口：接口中只有一个抽象方法，可以使用@FunctionalInterface注解接口，会在编程时验证
 */
public class LambdaBase {


    //需求：对一个数进行运算，策略模式
    private Integer operation(Integer num, MathInterface mf) {
        return mf.getValue(num);
    }

    @Test
    public void test5() {
        Integer result = operation(100, x -> x * x);
        System.out.println(result);
        System.out.println("------------------");
        result = operation(200, x -> x + 200);
        System.out.println(result);
        System.out.println("--------改进的方式，可以少创建一个方法----------");
        Consumer<Integer> consumer = x -> System.out.println(x + 200);
        consumer.accept(100);
        System.out.println("-------------------最佳改进的方式------------------");
        MathInterface mf = x -> x * x;
        System.out.println(mf.getValue(100));
    }

    @Test
    public void test() {

        //对于内部类调用同级别的局部变量，改变量需要为final类型
        //jdk1.7以前必须是final,现在默认是final,可以省略
        int num = 0;
        //
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("hello Non_Lambda!" + num);
            }
        };
        r.run();
        System.out.println("-------------------------------");

        Runnable r2 = () -> System.out.println("hello Lambda!" + num);
        r2.run();
    }

    @Test
    public void test2() {
//        Consumer<String> consumer = (x) -> System.out.println(x);
        Consumer<String> consumer = System.out::println;
        consumer.accept("lambda V5!");
    }

    //当-Lambda体有多条语句时，需要加上{}
    @Test
    public void test3() {
        Comparator<Integer> comparator = (x, y) -> {
            System.out.println("函数式接口：仅有一个方法的接口");
            return Integer.compare(x, y);
        };
    }

    @Test
    public void test4() {
//        Comparator<Integer> comparator = (x, y) -> Integer.compare(x, y);
        Comparator<Integer> comparator = Integer::compare;
    }


}