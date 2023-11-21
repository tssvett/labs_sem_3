package functions.meta;

import functions.Function;

public class Composition implements Function {
    private Function f1;
    private Function f2;
    public Composition(Function function_1, Function function_2) {
        this.f1 = function_1;
        this.f2 = function_2;
    }
    @Override
    public double getLeftDomainBorder() {
        return f1.getLeftDomainBorder();
    }
    @Override
    public double getRightDomainBorder() {
        return f1.getRightDomainBorder();
    }
    @Override
    public double getFunctionValue(double x) {
        return f1.getFunctionValue(f2.getFunctionValue(x));
    }
}
