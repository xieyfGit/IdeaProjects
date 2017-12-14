import com.yf.spring.ioc.reflect.Car;
import com.yf.spring.ioc.reflect.MyCar;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestReflect {

    @Test
    public void test() {

        initCar();
    }

    private void initCar() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            Class cls = loader.loadClass("com.yf.spring.ioc.reflect.MyCar");
            Constructor constructor = null;
            constructor = cls.getDeclaredConstructor();
            MyCar car = (MyCar) constructor.newInstance();
            car.setName("大众AA");
            car.setWheelCount(4);
            car.wheelCount = 5;
            //获取public方法
            Method method = cls.getMethod("fly");
            method.invoke(car);
            //获取继承方法
            method = cls.getMethod("drive");
            method.invoke(car);

            System.out.println("MyCar.getClasses");
            for (Class aClass : cls.getClasses()) {
                System.out.println(aClass.getSimpleName());
            }

            System.out.println("MyCar.getDeclaredFields");
            for (Field field : cls.getDeclaredFields()) {
                field.setAccessible(true);
                System.out.println(field.getName());
            }

            System.out.println("MyCar.getInterfaces");
            for (Class aClass : cls.getInterfaces()) {
                System.out.println(aClass.getSimpleName());
            }
            //获取带参数构造方法
            cls = loader.loadClass("com.yf.spring.ioc.reflect.Car");
            constructor = cls.getDeclaredConstructor(String.class, int.class);
            Car car2 = (Car) constructor.newInstance(new Object[]{"大众CC", 6});

            //获取非public方法
            method = cls.getDeclaredMethod("music");
            method.setAccessible(true);
            method.invoke(car2);

            System.out.println("Car.getDeclaredFields");
            for (Field field : cls.getDeclaredFields()) {
                field.setAccessible(true);
                System.out.println(field.getName());
            }
            System.out.println("Car.getClasses");
            for (Class aClass : cls.getClasses()) {
                System.out.println(aClass.getSimpleName());
            }

            System.out.println("Car.getInterfaces");
            for (Class aClass : cls.getInterfaces()) {
                System.out.println(aClass.getSimpleName());
            }

        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | ClassNotFoundException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
