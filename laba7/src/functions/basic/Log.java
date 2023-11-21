package functions.basic;

import functions.Function;

public class Log implements Function {
    private double base;

    public Log(double bx){
        if (bx > 0 && bx != 1){
            this.base = bx;
        }
    }

    @Override
    public double getLeftDomainBorder() {
        return 0;
    }

    @Override
    public double getRightDomainBorder() {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public double getFunctionValue(double x) {
        return  x > 0 ? Math.log(x) / Math.log(base) : Double.NaN;
    }
}
