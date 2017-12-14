package jdk8.newfeature.stream;

import jdk8.newfeature.lambda.Employee;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * java8新增的集合、数组操作API
 * 进一步抽象流的概念，将集合、数组作为数据源，将结果输出作为数据流目的地
 * 注意：流操作不会对数据源造成任何影响
 * 1.Stream不会存储数据
 * 2.Stream不会改变源，会产生一个新的Stream
 * 3.Stream延迟执行，在需要结果的时候才会执行
 * <p>
 * 集合：数据   流：计算
 * <p>
 * 操作步骤：
 * 1.获取一个流
 * 2.流水线式中间操作
 * 3。终止操作
 */
public class StreamAPI {

    List<Employee> employees = new ArrayList<>(Arrays.asList(
            new Employee("张三", 22),
            new Employee("张三", 22),
            new Employee("张三", 22),
            new Employee("张三", 22),
            new Employee("李四", 10),
            new Employee("王五", 34),
            new Employee("赵六", 66),
            new Employee("田七", 23),
            new Employee("刘八", 33)
    ));

    private List<String> list = Arrays.asList("abc", "dds", "sefd", "ddde", "aaa", "bbb", "ccc");

    //将指定字符串转换为char类型存入list，并返回list的流对象
    private static Stream<Character> charFilter(String str) {
        List<Character> list0 = new ArrayList<>();
        for (char c : str.toCharArray()) {
            list0.add(c);
        }
        return list0.stream();
    }

    private static List<Character> charFilter2(String str) {
        List<Character> list0 = new ArrayList<>();
        for (char c : str.toCharArray()) {
            list0.add(c);
        }
        return list0;
    }

    /**
     * 映射
     * map:接收Lambda，将元素转换为其它形式或提取信息。接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
     * flatMap:接收一个函数作为参数，改流中每个值都换成另一个流，然后把所有流连接成一个流
     */
    @Test
    public void test3() {
        employees.stream()
                .map(Employee::getName)
                .distinct()
                .forEach(System.out::println);

        //需求：将所有字符提取出来
        System.out.println("---------------使用map方式，较麻烦--------------------");
        //这种方式比较麻烦
        list.stream()
                .map(StreamAPI::charFilter)//{{a,b,c}，{d,d,s}，{s,e,f,d,}，{d,d,d,e}}
                .limit(1)
                .forEach((x) -> {
                    x.forEach(System.out::println);
                });
        System.out.println("--------------map将每次函数应用到每个元素上，并返回一个新的元素-----------");
        list.stream()
                .map(StreamAPI::charFilter2)
                .limit(1)
                .forEach(System.out::println);
        System.out.println("-------------使用flatMap优化,将每个值换成一个流，最后合并所有流成一个流---------------------");

        //使用flatMap优化
        list.stream()
                //{{a,b,c}，{d,d,s}，{s,e,f,d,}，{d,d,d,e}}->{a,b,c,d,d,s,s,e,f,d,,d,d,d,e}
                .flatMap(StreamAPI::charFilter)
                .limit(1)
                .forEach(System.out::println);

    }
    @Test
    public void test1() {
        //1.通过Collection的stream()获取串行流、paralleStream获取并行流
        Stream<Employee> stream = employees.stream();

        //2.通过Arrays的stream()获取串行流，参数为数组
        Stream<Employee> stream2 = Arrays.stream(employees.toArray(new Employee[employees.size()]));

        //3.通过Stream类中的of方法,参数为可变长度参数
        Stream<Employee> stream3 = Stream.of(employees.toArray(new Employee[employees.size()]));

        //4.创建无限流
        //①迭代方式
        Stream stream4 = Stream.iterate(0, x -> x+2 );
        stream4.limit(10).forEach(System.out::println);
        //②生成方式
        Stream stream5 = Stream.generate(Math::random);
        stream5.limit(10).forEach(System.out::println);
    }

    /**
     * 终止操作
     * allMatch:检查是否匹配所有元素
     * anyMatch:检查是否至少匹配一个元素
     * noneMatch:检查是否没有匹配所有元素
     * findFirst:返回第一个元素
     * findAny:返回当前流中任意元素
     * count:返回流中元素的个数
     * max:返回流中的最大值
     * min:返回流中的最小值
     */
    @Test
    public void test5() {
        System.out.println("----------allMatch-----------------");
        System.out.println(list.stream()
                .allMatch(x -> {
                    return x.length() == 3;
                }));
        System.out.println("----------anyMatch-----------------");
        System.out.println(list.stream()
                .anyMatch(x -> x.length() == 3));
        System.out.println("----------noneMatch-----------------");
        System.out.println(list.stream()
                .noneMatch(x -> x.length() == 2));
        System.out.println("----------findFirst->Optional-----------------");
        Optional<Employee> optional = employees.stream()
                .findFirst();
        System.out.println(optional.isPresent() ? optional.get() : "null");
        System.out.println("-----------findFirst->orElse-----------------");
        System.out.println(employees.stream()
                .findFirst().orElse(null));
        System.out.println("----------findAny-----------------");
        System.out.println(list.parallelStream()
                .filter(x->x.length()==3)
                .findAny().orElse("null"));
        System.out.println("----------count-----------------");
        System.out.println(list.stream()
                .count());
        System.out.println("----------max-----------------");
        System.out.println(list.stream()
                .max(String::compareTo).orElse("null"));
        System.out.println("----------min-----------------");
        System.out.println(list.stream()
                .min(String::compareTo).orElse("null"));

    }

    /**
     * 排序
     * sorted():自然排序(Comparable)
     * sorted(Comparator comparator):定制排序
     */
    @Test
    public void test4() {
        System.out.println("------------------自然排序---------------");
        list.stream()
                .sorted()
                .forEach(System.out::println);

        System.out.println("----------------定制排序--------------------");
        employees.stream()
                .sorted((x, y) -> {
                    if (x.getAge() == y.getAge()) {
                        return x.getName().compareTo(y.getName());
                    } else {
                        return x.getAge() - y.getAge();
                    }
                })
                .forEach(System.out::println);

    }

    /**
     * 筛选与切片
     * filter:接收Lambda,从中排除某些元素
     * limit(n):截断流，限制元素数量
     * skip(n):跳过元素,返回一个扔掉了前N个元素的流，若不足N，则返回空流。与limit互补
     * distinct:筛选，通过流所产生元素的hashCode()和equals()去除重复元素
     */
    @Test
    public void test2() {
//        Stream<Employee> stream = employees.stream();
//        //中间操作,不执行任何操作，称之为：延迟执行
//        stream.filter((x) -> x.getAge() > 35);
//        //终止操作
//        stream.forEach(System.out::println);
        employees.stream()
                .filter(x -> {
                    System.out.println("短路");
                    return x.getAge() > 20;
                })//过滤条件之间使||关系，即一旦满足条件就不再迭代,即：不会遍历流的所有元素，提高了效率
                .limit(4)
                .skip(2)
                .distinct()//便于测试，重新实现了Employee的hashCode()和equals()方法
                .forEach(System.out::println);

    }


}
