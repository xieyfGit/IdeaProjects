package jdk8.newfeature.stream;

import jdk8.newfeature.lambda.Employee;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class StreamPractice {
    List<Employee> employees = new ArrayList<>(Arrays.asList(
            new Employee("张三", 22),
            new Employee("张三", 22),
            new Employee("张三", 22),
            new Employee("张三", 22),
            new Employee("李四", 10),
            new Employee("王五", 34),
            new Employee("赵六", 66),
            new Employee("田七", 23),
            new Employee("刘八", 33),
            new Employee()
    ));

    /**
     * 1.给定一个数字列表，如何返回一个由每个数的平方构成的列表
     */
    @Test
    public void test0() {
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9);
        list.stream()
                .map(x->x*x)
                .forEach(System.out::println);
        System.out.println("---------------------------------");
        Optional<Integer> optional = employees.stream()
                .map(e->1)
                .reduce(Integer::sum);
        System.out.println(optional.get());
    }
}
