package jdk8.design.decorator;

public class DecoratorMain {
    public static void main(String[] args) {
        Component component = new ConcreteComponent();

        Decorator decorator = new ConcreteDecorator(component);
        decorator.sayHello();
    }
}
