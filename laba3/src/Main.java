import functions.*;

public class Main {
    public static void main(String[] args) {
        // y = 3x
        double[] values = {3, 6, 9, 12, 15, 18, 21, 24, 27, 30};
        TabulatedFunction f1 = new ArrayTabulatedFunction(1, 10, values);
        System.out.println("[!]ПРОВЕРКА ФУНКЦИИ ЧЕРЕЗ МАССИВ[!]");
        System.out.println("====================================================================");
        System.out.println("Проверка конструктора");
        System.out.println(f1.getFunctionValue(3));
        System.out.println("Проверка интерполяции:");
        System.out.println(f1.getFunctionValue(3.5));
        System.out.println(f1.getFunctionValue(3.6));
        System.out.println(f1.getFunctionValue(4));
        System.out.println(f1.getFunctionValue(0));
        System.out.println("====================================================================");

        System.out.println("Проверка добавления точек");
        System.out.println("Текущее число точек: " + f1.getPointsCount());
        System.out.println("Добавляем точку (0, 5)...");
        try {
            f1.addPoint(new FunctionPoint(0, 5));
        } catch (InappropriateFunctionPointException e) {
            System.out.println(e);
        }
        System.out.println("Число точек после попытки добавления: " + f1.getPointsCount());
        System.out.println("====================================================================");

        System.out.println("Проверка добавления существующей точки");
        System.out.println("Текущее число точек: " + f1.getPointsCount());
        try {
            f1.addPoint(new FunctionPoint(0, 5));
        }
        catch (InappropriateFunctionPointException e) {
            System.out.println(e);
        }
        System.out.println("Число точек после попытки добавления: " + f1.getPointsCount());
        System.out.println("====================================================================");

        System.out.println("Проверка удаления  несуществующей точки");
        System.out.println("Число точек до удаления: " + f1.getPointsCount());
        try {
            f1.deletePoint(-5);
        }
        catch (functions.FunctionPointIndexOutOfBoundsException e) {
            System.out.println(e);
        }
        System.out.println("Число точек после удаления: " + f1.getPointsCount());
        System.out.println("====================================================================");




        TabulatedFunction f2 = new LinkedListTabulatedFunction(1, 10, values);
        System.out.println("\n\n\n\n[!]ПРОВЕРКА ФУНКЦИИ ЧЕРЕЗ ДВУСВЯЗНЫЙ СПИСОК[!]");
        System.out.println("====================================================================");
        System.out.println("Проверка конструктора");
        System.out.println(f2.getFunctionValue(3));
        System.out.println("Проверка интерполяции:");
        System.out.println(f2.getFunctionValue(3.5));
        System.out.println(f2.getFunctionValue(3.6));
        System.out.println(f2.getFunctionValue(4));
        System.out.println(f2.getFunctionValue(0));
        System.out.println("====================================================================");

        System.out.println("Проверка добавления точек");
        System.out.println("Текущее число точек: " + f2.getPointsCount());
        System.out.println("Добавляем точку (0, 5)...");
        try {
            f2.addPoint(new FunctionPoint(0, 5));
        } catch (InappropriateFunctionPointException e) {
            System.out.println(e);
        }
        System.out.println("Число точек после попытки добавления: " + f2.getPointsCount());
        System.out.println("====================================================================");

        System.out.println("Проверка добавления существующей точки");
        System.out.println("Текущее число точек: " + f2.getPointsCount());
        try {
            f2.addPoint(new FunctionPoint(0, 5));
        }
        catch (InappropriateFunctionPointException e) {
            System.out.println(e);
        }
        System.out.println("Число точек после попытки добавления: " + f2.getPointsCount());
        System.out.println("====================================================================");

        System.out.println("Проверка удаления  несуществующей точки");
        System.out.println("Число точек до удаления: " + f2.getPointsCount());
        try {
            f2.deletePoint(-5);
        }
        catch (functions.FunctionPointIndexOutOfBoundsException e) {
            System.out.println(e);
        }
        System.out.println("Число точек после удаления: " + f2.getPointsCount());
        System.out.println("====================================================================");
    }
}