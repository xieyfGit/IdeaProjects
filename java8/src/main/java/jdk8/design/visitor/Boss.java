package jdk8.design.visitor;

public class Boss implements AccountBookViewer {

    private double totalIncome;

    private double totalConsume;

    //老板只关注一共花了多少钱以及一共收入多少钱，其余并不关心
    @Override
    public void view(ConsumeBill bill) {
        totalConsume += bill.getAmount();
    }

    @Override
    public void view(IncomeBill bill) {
        totalIncome += bill.getAmount();
    }

    public double getTotalIncome() {
        System.out.println("老板查看一共收入多少，数目是：" + totalIncome);
        return totalIncome;
    }

    public double getTotalConsume() {
        System.out.println("老板查看一共花费多少，数目是：" + totalConsume);
        return totalConsume;
    }
}
