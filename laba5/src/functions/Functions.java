package functions;

import functions.meta.*;
public class Functions {
    private Functions(){}

    public static Function shift(Function f1, double shiftX, double shiftY) {
        return new Shift(f1, shiftX, shiftY);
    }
    public static Function scale(Function f1, double scaleX, double scaleY) {
        return new Scale(f1, scaleX, scaleY);
    }

    public static Function sum(Function f1, Function f2) {
        return new Sum(f1, f2);
    }
    public static Function power(Function f1, Function f2) {
        return new Power(f1, f2);
    }
    public static Function mult(Function f1, Function f2) {
        return new Mult(f1, f2);
    }
    public static Function composition(Function f1, Function f2) {
        return new Composition(f1, f2);
    }
}