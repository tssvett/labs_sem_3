package functions;

import java.io.*;

public class TabulatedFunctions {
    private TabulatedFunctions() {}
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
