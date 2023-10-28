package functions;

public interface TabulatedFunction {

    public double getLeftDomainBorder();

    public double getRightDomainBorder();

    public int getPointsCount();

    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException;

    public double getPointX(int index);

    public void setPointX(int index, double x) throws InappropriateFunctionPointException;

    public double getPointY(int index);

    public void setPointY(int index, double y);

    public double getFunctionValue(double x);

    public void deletePoint(int index);

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException;
}

