package functions.meta;

import functions.Function;

public class Power implements Function {
    private Function f1, f2;
    public Power(Function function1, Function function2) {
        this.f1 = function1;
        this.f2 = function2;
    }
    @Override
    public double getLeftDomainBorder() {
        return Math.min(f1.getLeftDomainBorder(), f2.getLeftDomainBorder());
    }
    @Override
    public double getRightDomainBorder() {
        return Math.min(f1.getRightDomainBorder(), f2.getRightDomainBorder());
    }
    @Override
    public double getFunctionValue(double x) {
        return Math.pow(f1.getFunctionValue(x), f1.getFunctionValue(x));
    }
}
