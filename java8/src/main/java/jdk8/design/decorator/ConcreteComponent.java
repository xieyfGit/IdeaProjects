package jdk8.design.decorator;

public class ConcreteComponent extends Component {
    @Override
    public void sayHello() {
        System.out.println("say hello world in ConcreteComponent");
    }
}
