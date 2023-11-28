package functions;

import java.io.Serializable;

public interface TabulatedFunction extends Function,  Iterable<FunctionPoint> {

    public int getPointsCount();

    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException;
    public FunctionPoint getPoint(int i);

    public double getPointX(int index);

    public void setPointX(int index, double x) throws InappropriateFunctionPointException;

    public double getPointY(int index);

    public void setPointY(int index, double y);

    public void deletePoint(int index);

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException;

    Object clone();
}

