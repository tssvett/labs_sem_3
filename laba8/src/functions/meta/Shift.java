package functions.meta;

import functions.Function;

public class Shift implements Function {
    private Function f;
    double shiftX, shiftY;
    public Shift(Function function_1, double shiftX, double shiftY) {
        this.f = function_1;
        this.shiftX = shiftX;
        this.shiftY = shiftY;
    }
    @Override
    public double getLeftDomainBorder() {
        return this.f.getRightDomainBorder() + this.shiftX;
    }
    @Override
    public double getRightDomainBorder() {
        return this.f.getLeftDomainBorder() + this.shiftX;
    }
    @Override
    public double getFunctionValue(double x) {
        return this.f.getFunctionValue(x + this.shiftX) + this.shiftY;
    }
}
