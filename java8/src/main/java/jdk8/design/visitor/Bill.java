package jdk8.design.visitor;

/**
 * 访问者模式中的Element
 */
public interface Bill {
    void  accept(AccountBookViewer viewer);
}
