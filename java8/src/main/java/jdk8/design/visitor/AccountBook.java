package jdk8.design.visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * 相当于访问者模式中的Object Structure
 */
public class AccountBook {
    //单子列表
    private List<Bill> billList = new ArrayList<Bill>();
    //添加单子
    public void addBill(Bill bill){
        billList.add(bill);
    }
    //供账本的查看者查看账本
    public void show(AccountBookViewer viewer){
        for (Bill bill : billList) {
            bill.accept(viewer);
        }

    }
}

