import functions.FunctionPoint;
import functions.TabulatedFunction;

public class Main {
    public static void main(String[] args) {
        // y = 3x
        double values[] = {3, 6, 9, 12, 15, 18, 21, 24, 27, 30};
        TabulatedFunction f = new TabulatedFunction(1, 10, values);

        System.out.println("====================================================================");
        System.out.println("Проверка конструктора");
        System.out.println(f.getFunctionValue(3));
        System.out.println(f.getFunctionValue(3.5));
        System.out.println(f.getFunctionValue(3.6));
        System.out.println(f.getFunctionValue(0));
        System.out.println("====================================================================");

        System.out.println("Проверка добавления точек");
        System.out.println(f.getPointsCount());
        f.addPoint(new FunctionPoint(0, 5));
        System.out.println(f.getPointsCount());
        System.out.println(f.getFunctionValue(0));
        System.out.println("====================================================================");

        System.out.println("Проверка добавления существующей точки");
        System.out.println(f.getPointsCount());
        f.addPoint(new FunctionPoint(0, 5));
        System.out.println(f.getPointsCount());
        System.out.println(f.getFunctionValue(0));
        System.out.println("====================================================================");

        System.out.println("Проверка удаления точки");
        System.out.println(f.getPointsCount());
        f.deletePoint(0);
        System.out.println(f.getPointsCount());
        System.out.println(f.getFunctionValue(0));
        System.out.println("====================================================================");

    }

}