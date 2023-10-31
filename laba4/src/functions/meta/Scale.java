package functions.meta;

import functions.Function;

public class Scale implements Function {
    private Function f;
    double scaleX, scaleY;
    public Scale(Function function_1, double scaleX, double scaleY) {
        this.f = function_1;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }
    @Override
    public double getLeftDomainBorder() {
        return this.f.getRightDomainBorder() * this.scaleX;
    }
    @Override
    public double getRightDomainBorder() {
        return this.f.getLeftDomainBorder() * this.scaleX;
    }
    @Override
    public double getFunctionValue(double x) {
        return this.f.getFunctionValue(x) * this.scaleY;
    }
}
