package jdk8.newfeature.lambda;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class LambdaSenior {

    List<Employee> employees = new ArrayList<>(Arrays.asList(
            new Employee("张三", 12),
            new Employee("李四", 33),
            new Employee("王五", 24),
            new Employee("赵六", 45),
            new Employee("田七", 8)
    ));


    //需求：所有员工先按年龄排序，再按姓名排序
    @Test
    public void test() {
        employees.sort((e1, e2) -> {
            if (e1.getAge() == e2.getAge()) {
                return e1.getName().compareTo(e2.getName());
            } else {
                return Integer.compare(e1.getAge(), e2.getAge());
            }
        });
        for (Employee employee : employees) {
            System.out.println(employee);
        }

    }

    //将字符串转换为大写
    @Test
    public void strUpper() {
        String str = "hello lambda!";
        System.out.println("-------------------使用自定义接口---------------");
//        str = getValue(str, x -> x.toUpperCase());
        str=getValue(str,String::toUpperCase);
        System.out.println(str);
        System.out.println("-------------------原始方式->内部类---------------");
        str = getValue(str, new StringLambda() {
            @Override
            public String getValue(String str) {
                return str.toLowerCase();
            }
        });
        System.out.println(str);

        System.out.println("--------------------使用内置接口(推荐)------------------");
        Consumer<String> consumer = x->System.out.println(x.toUpperCase());
        consumer.accept(str);
    }

    private String getValue(String str, StringLambda sl) {
        return sl.getValue(str);
    }

    //计算两个数的运算
    private long operation(long l1, long l2, LambdaTwoMeshOperation<Long, Long> ltmo) {
        return ltmo.getResult(l1, l2);
    }

    @Test
    public void test2() {
        System.out.println("-------------------自定义接口--------------------");
        long result = operation(100, 200, (x, y) -> x + y);
        System.out.println(result);
        System.out.println("--------------------");
        result = operation(100, 200, (x, y) -> x * y);
        System.out.println(result);
        System.out.println("-----------------使用自带接口---------------------");
        BiConsumer<Integer,Integer> biConsumer = (x, y)-> System.out.println(x+y);
        biConsumer.accept(100,100);

    }
}
@FunctionalInterface
interface LambdaTwoMeshOperation<T,R> {

    public R getResult(T t,T t2);
}
@FunctionalInterface
interface StringLambda {
    public String getValue(String str);
}