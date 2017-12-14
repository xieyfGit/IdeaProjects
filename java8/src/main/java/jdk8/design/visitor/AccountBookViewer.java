package jdk8.design.visitor;

/**
 * 访问者模式中的Visitor
 */
public interface AccountBookViewer {
    //查看消费的单子
    void view(ConsumeBill bill);

    //查看收入的单子
    void view(IncomeBill bill);
}
