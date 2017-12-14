package com.yf;

/**
 * 对于重写方法，访问权限不能小于父类，返回类型可以是父类的子类，异常类型不能小于父类
 * 对于重载方法，仅仅需要关系方法签名即可
 */
public class OverLoad {

    protected Return load() throws Exception{

        return null;
    }
}
class OverLoadExt extends OverLoad{
    @Override
    public ReturnExt load() throws ArithmeticException{
        return null;
    }
}
class Return{

}

class ReturnExt extends Return{

}
