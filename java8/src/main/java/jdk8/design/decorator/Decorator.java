package jdk8.design.decorator;

public class Decorator extends Component {
    protected Component component;

    public Decorator(Component component) {
        this.component = component;
    }

    @Override
    public void sayHello() {
        //转发请求给组件对象，可以在转发前后执行一些附加动作
        component.sayHello();
    }
}
