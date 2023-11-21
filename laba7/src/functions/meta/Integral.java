package functions.meta;

import functions.Function;
import functions.FunctionPointIndexOutOfBoundsException;

public class Integral {
    public static void isInBorder(Function f, double leftX, double rightX) {
        if (leftX < f.getLeftDomainBorder() || rightX > f.getRightDomainBorder()){
            throw new IllegalArgumentException();
        }
    }
    public static double integral(Function func, double leftX, double rightX, double step) throws FunctionPointIndexOutOfBoundsException {
        isInBorder(func, leftX, rightX);
        double res = 0;
        for (; step < rightX; step += step){
            res +=func.getFunctionValue(step) * step;
        }
        return res;
    }
}