package jdk8.newfeature.lambda;

import org.junit.Test;

import java.util.Comparator;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 方法引用：若Lambda体中的内容有方法已经实现了，我们可以使用“方法引用”(可以理解为Lambda表达式的另外一种表现形式)
 * 主要有三种语法格式：
 * <p>
 * 对象::实例方法名
 * 类::静态方法名
 * 类::实例方法名  当参数列表的第一个参数为方法的调用者，[第二个参数时方法的参数的情况下可以使用]
 *
 * 构造器引用：
 * 类名::new  自动匹配与函数接口中抽象方法参数列表相同的构造方法
 *
 *
 * 数组引用
 * Type[]::new
 *
 */
public class LambdaMethodRef {

    //数组引用
    public void test6() {
        Function<Integer,String[]> function =(x)->new String[x];
        String[] strings = function.apply(10);

        Function<Integer, String[]> function1 = String[]::new;
        function1.apply(10);

    }

    //构造器引用 自动匹配与函数接口中抽象方法参数列表相同的构造方法
    @Test
    public void test5() {
        Supplier<Employee> supplier = Employee::new;
        Employee employee = supplier.get();
        System.out.println(employee);

    }


    //类::实例方法名
    @Test
    public void test4() {
        //当左参刚好为方法调用者时，可以使用类方法::实例方法名
        BiPredicate<String, String> bp = (x, y) -> x.equals(y);
        BiPredicate<String, String> bp2 = String::equals;

    }

    //类::静态方法名
    @Test
    public void test3() {
        //当左右参数一致时，对静态方法可以使用 类名::方法名
//        Comparator<Integer> comparator = (x,y)->Integer.compare(x,y);
        Comparator<Integer> comparator = Integer::compare;
        System.out.println(comparator.compare(1, 2));

        System.out.println(Integer.compare(1, 2));
    }


    @Test
    public void test1() {
        Consumer<Integer> consumer0 = x-> System.out.println(x*x);
        //当左右参数一致时可以 实例::方法名,上面这种就不行
//        Consumer<Integer> consumer = x-> System.out.println(x);
        Consumer<Integer> consumer = System.out::println;
//        Consumer<String> consumer = System.out::println;
        consumer.accept(1);
    }

    //对象::实例方法名
    @Test
    public void test2() {
        Employee employee = new Employee("mrxie", 35);
        System.out.println("----------------Supplier<T>->T get()----------------------------");
        Supplier<String> supplier = employee::getName;
        System.out.println(supplier.get());
        System.out.println("--------------Function<T,R>方式->R apply(T t)----------------------------");
//        Function<Employee,String> function = x -> x.getName();
        //当入参刚好为方法调用者的时候，支持  类::实例方法
        Function<Employee,String> function =Employee::getName;
        System.out.println(function.apply(employee));
        System.out.println("----------Function<T,R>方式->另一种方式，策略模式------------------");
        System.out.println(getName(employee, (x) -> x.getName()));
    }

    private String getName(Employee employee, Function<Employee, String> function) {
        return function.apply(employee);
    }
}
