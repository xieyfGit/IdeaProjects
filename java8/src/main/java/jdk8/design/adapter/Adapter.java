package jdk8.design.adapter;

/**
 * 适配器 将源接口适配成目标接口，继承源接口，实现目标接口
 */
public class Adapter extends Adaptee implements Target{

    @Override
    public void consume() {
        System.out.println("implements from Target: consume someting...");
        reduce();
    }
}
