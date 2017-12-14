package jdk8.newfeature.stream;

import jdk8.newfeature.lambda.Employee;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Stream 归约与收集
 */
public class StreamReduce {

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

    //归约：
    //reduce(T identity,BinaryOperator bo)/reduce(BinaryOperator bo)——可以将流中元素反复结合起来，看到一个值.
    @Test
    public void test0() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        Integer sum = list.stream().reduce(0, Integer::sum);
        System.out.println(sum);
        System.out.println("---------------  map-reduce -----------------");
        Optional<Integer> optional = employees.stream()
                .map(Employee::getAge)
                .reduce(Integer::sum);
        System.out.println(optional.get());
    }

    //收集：
    // collect——将流转换为其它形式，。接收一个Collector接口的实现，用于给Stream中元素做汇总的方法
    @Test
    public void test1() {
        List<String> list = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toList());
        list.forEach(System.out::println);
        System.out.println("----------------------------------");
        Set<String> set = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toCollection(HashSet::new));
        set.forEach(System.out::println);
        System.out.println("--------------总数--------------------");
        long count = employees.stream()
                .limit(3)
                .collect(Collectors.counting());
        System.out.println(count);

        System.out.println("--------------平均值-------------------");
        double avg = employees.stream()
                .collect(Collectors.averagingDouble(Employee::getAge));
        System.out.println(avg);

        System.out.println("----------------年龄最大的员工信息------------------");
        Optional<Employee> employee = employees.stream()
                .collect(Collectors.maxBy((x, y) -> Integer.compare(x.getAge(), y.getAge())));

        Optional<Employee> employee2 = employees.stream()
                .min((x, y) -> Integer.compare(x.getAge(), y.getAge()));
        System.out.println(employee);
        System.out.println(employee2);

        System.out.println("-------------------最小值------------------------");
        Optional<Employee> employee3 = employees.stream()
                .collect(Collectors.minBy((x, y) -> Integer.compare(x.getAge(), y.getAge())));
        System.out.println(employee3);

        System.out.println("------------------分组----------------------------");
        Map<Integer, List<Employee>> map = employees.stream()
                .collect(Collectors.groupingBy(Employee::getAge));
        for (Map.Entry<Integer, List<Employee>> entry : map.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }

        System.out.println("-------------------多级分组---------------------------");
        Map<Integer,Map<String,List<Employee>>> map1= employees.stream()
                .collect(Collectors.groupingBy(Employee::getAge, Collectors.groupingBy(e -> {
                    if (((Employee) e).getAge() < 30) {
                        return "青年";
                    } else {
                        return "中年";
                    } })));
        System.out.println(map1);

        System.out.println("---------------------分区-----------------------------");
        Map<Boolean,List<Employee>> map2=employees.stream()
                .collect(Collectors.partitioningBy(e->e.getAge()>22));

        System.out.println(map2);

        System.out.println("--------------------聚合工具函数-----------------------");
        DoubleSummaryStatistics dds =employees.stream()
                .collect(Collectors.summarizingDouble(Employee::getAge));
        System.out.println(dds.getCount());
        System.out.println(dds.getAverage());
        System.out.println(dds.getMax());
        System.out.println(dds.getMin());
        System.out.println(dds.getSum());

        System.out.println("----------------------连接字符串-----------------------");
        String result = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.joining(",","prefix--","--suffix"));
        System.out.println(result);

    }

}
