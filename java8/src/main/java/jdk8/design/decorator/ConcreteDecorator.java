package jdk8.design.decorator;

public class ConcreteDecorator extends Decorator {

    public ConcreteDecorator(Component component) {
        super(component);
    }

    //在调用父类的operation方法之前需要执行的操作
    public void writeHello() {
        System.out.println("write hello world in ConcreteDecorator");
    }

    @Override
    public void sayHello() {
        writeHello();
        super.sayHello();
    }

}
