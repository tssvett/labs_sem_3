package functions;

import java.io.*;
import java.lang.reflect.Constructor;

public class TabulatedFunctions {
    private TabulatedFunctions() {}

    private static TabulatedFunctionFactory factory = new ArrayTabulatedFunction.ArrayTabulatedFunctionFactory();

    public static void setTabulatedFunctionFactory(TabulatedFunctionFactory factory) {
        TabulatedFunctions.factory = factory;
    }
    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointCount) {
        try {
            return factory.createTabulatedFunction(leftX, rightX, pointCount);
        }
        catch (IllegalArgumentException e) {
            return null;
        }
    }
    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values) {
        try {
            return factory.createTabulatedFunction(leftX, rightX, values);
        }
        catch (IllegalArgumentException e) {
            return null;
        }
    }
    public static TabulatedFunction createTabulatedFunction(FunctionPoint[] points) {
        try {
            return factory.createTabulatedFunction(points);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction> functionClass, double leftX, double rightX, int amount) {
        Constructor[] constructors = functionClass.getConstructors();
        for (Constructor constructor : constructors) {
            Class[] types = constructor.getParameterTypes();
            if (types.length == 3 && types[0].equals(Double.TYPE) && types[1].equals(Double.TYPE) && types[2].equals(Integer.TYPE)) {
                try {
                    return (TabulatedFunction) constructor.newInstance(leftX, rightX, amount);
                } catch (Exception e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }
        return null;
    }
    public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction> functionClass, double leftX, double rightX, double[] points) {
        Constructor[] constructors = functionClass.getConstructors();
        for (Constructor constructor : constructors) {
            Class[] types = constructor.getParameterTypes();
            if (types.length == 3 && types[0].equals(Double.TYPE) && types[1].equals(Double.TYPE) && types[2].equals(points.getClass())) {
                try {
                    return (TabulatedFunction) constructor.newInstance(leftX, rightX, points);
                } catch (Exception e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }
        return null;
    }
    public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction> functionClass, FunctionPoint[] points) {
        Constructor[] constructors = functionClass.getConstructors();
        for (Constructor constructor : constructors) {
            Class[] types = constructor.getParameterTypes();
            if (types.length > 0 && types[0].equals(points.getClass())) {
                try {
                    return (TabulatedFunction) constructor.newInstance(new Object[]{points});
                } catch (Exception e) {
                    System.out.println(e.getMessage() + '\n' + '\n');
                    throw new IllegalArgumentException(e);
                }
            }
        }
        return null;
    }
    public static TabulatedFunction tabulate(Function f, double leftX, double rightX, int pointCount) {
        if (leftX < f.getLeftDomainBorder() || rightX > f.getRightDomainBorder()) {
            throw new IllegalArgumentException("[!] Указанные границы для табулирования выходят за область определения функции");
        }
        FunctionPoint[] points = new FunctionPoint[pointCount];
        double interval = (rightX - leftX) / (pointCount - 1);
        for (int i = 0; i < pointCount; i++) {
            points[i] = new FunctionPoint(leftX + i * interval, f.getFunctionValue(leftX + i * interval));
        }
        return new ArrayTabulatedFunction(points);
    }

    public static TabulatedFunction tabulate(Class<? extends TabulatedFunction> functionClass, Function f, double leftX, double rightX, int pointCount) {
        try {
            if (leftX < f.getLeftDomainBorder() || rightX > f.getRightDomainBorder()) {
                throw new IllegalArgumentException();
            }
            FunctionPoint[] points = new FunctionPoint[pointCount];
            double difference = (rightX - leftX) / (pointCount - 1);
            points[0] = new FunctionPoint(leftX, f.getFunctionValue(leftX));
            for (int i = 1; i < pointCount; i++) {
                points[i] = new FunctionPoint(leftX + i * difference, f.getFunctionValue(leftX + i * difference));
            }
            return createTabulatedFunction(functionClass, leftX, rightX, pointCount);
        }
        catch (IllegalArgumentException e) {
            return null;
        }
        catch (FunctionPointIndexOutOfBoundsException ex){

        }
        return null;
    }
    public static void outputTabulatedFunction(TabulatedFunction f, OutputStream out) throws IOException {
        DataOutputStream cout = new DataOutputStream(out);
        cout.writeInt(f.getPointsCount());
        for (int i = 0; i < f.getPointsCount(); i++) {
            cout.writeDouble(f.getPointX(i));
            cout.writeDouble(f.getPointY(i));
        }
        cout.flush();
    }

    public static TabulatedFunction inputTabulatedFunction(InputStream in) throws IOException {
        DataInputStream cin = new DataInputStream(in);
        int amount = cin.readInt();
        FunctionPoint[] points = new FunctionPoint[amount];
        for (int i = 0; i < amount; i++) {
            points[i] = new FunctionPoint(cin.readDouble(), cin.readDouble());
        }
        return new ArrayTabulatedFunction(points);
    }

    public static void writeTabulatedFunction(TabulatedFunction f, Writer out) throws IOException {
        PrintWriter cout = new PrintWriter(out);
        cout.println(f.getPointsCount());
        for (int i = 0; i < f.getPointsCount(); i++) {
            cout.println(f.getPointX(i) + " " + f.getPointY(i));
        }
        cout.flush();
    }

    public static TabulatedFunction readTabulatedFunction(Reader in) throws IOException {
        StreamTokenizer cin = new StreamTokenizer(in);
        cin.nextToken();
        int amount = (int) cin.nval;
        FunctionPoint[] points = new FunctionPoint[amount];
        for (int i = 0; i < amount; i++) {
            cin.nextToken();
            double x = cin.nval;
            cin.nextToken();
            double y = cin.nval;
            points[i] = new FunctionPoint(x, y);
        }
        return new ArrayTabulatedFunction(points);
    }
}
