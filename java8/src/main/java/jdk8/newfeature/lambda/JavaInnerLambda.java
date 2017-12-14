package jdk8.newfeature.lambda;


import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * java8中内置四大核心函数式接口
 *
 * 一.Consumer<T>:消费型接口
 *      void accept(T t);
 *
 * 二.Supplier<T>：供给型接口
 *      T get();
 *
 * 三.Function<T,R>:函数型接口
 *      R apply(T t);
 *
 * 四.Predicate<T>:断言型接口
 *      boolean test(T t);
 *
 */
public class JavaInnerLambda {
    @Test
    public void test() {
        happy(10000, x -> System.out.println("张三此次消费" + x));
        System.out.println("---------------更简洁的实现方式，直接实现接口------------------");
        Consumer<Double> consumer = x -> System.out.println("张三此次消费" + x);
        consumer.accept(1000.0);

//        List<Double> list =getNumList(5,()-> Math.random());
        List<Double> list =getNumList(5,Math::random);
        System.out.println(list);

//        String result = stringHandler("Hello World!",x->x.toUpperCase());
        String result = stringHandler("Hello World!",String::toUpperCase);
        System.out.println(result);

        List<String> list0 = new ArrayList<>(Arrays.asList(
                "abee",
                "abcsdfee",
                "ae",
                "abccdeews"
        ));
        List<String> list1 = filterStr(list0,x->x.length()>2);
        System.out.println(list1);
    }

    //消费型接口
    public void happy(double money, Consumer<Double> consumer) {
        consumer.accept(money);
    }

    //供给型接口
    public List<Double> getNumList(int count, Supplier<Double> supplier) {
        List<Double> list = new ArrayList<>(5);
        for (int i = 0; i <count ; i++) {
            list.add(supplier.get());
        }
        return list;
    }

    //函数型接口
    public String stringHandler(String str,Function<String,String> function){
        return function.apply(str);
    }

    //断言型接口
    public List<String> filterStr(List<String> list, Predicate<String> predicate) {
        List<String> result = new ArrayList<>();
        for (String s : list) {
            if (predicate.test(s)) {
                result.add(s);
            }
        }
        return  result;
    }
}