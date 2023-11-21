package functions;

import java.io.Serializable;

public class ArrayTabulatedFunction implements TabulatedFunction, Serializable {
    private FunctionPoint[] pointsList;
    private int pointsNumber;


    public ArrayTabulatedFunction(double leftX, double rightX, int pointsCount) {
        //Конструктор, создающий табличную функцию с указанным количеством точек между границами домена leftX и rightX.
        if (leftX >= rightX || pointsCount < 2) {
            throw new IllegalArgumentException("[!] Ошибка! Левая граница должна быть меньше правой. Точек должно быть больше или равно двух.");
        }
        this.pointsList = new FunctionPoint[pointsCount];
        double interval = (rightX - leftX) / (pointsCount - 1);
        for (int i = 0; i < pointsCount; i++) {
            this.pointsList[i] = new FunctionPoint(leftX + i * interval, 0);
        }
        this.pointsNumber = pointsCount;
    }

    public ArrayTabulatedFunction(double leftX, double rightX, double[] values) {
        //Конструктор, создающий табличную функцию с точками в указанных координатах X и соответствующих значениях Y.
        if (leftX >= rightX || values.length < 2) {
            throw new IllegalArgumentException("[!] Ошибка! Левая граница должна быть меньше правой.  Точек должно быть больше или равно двух.");
        }
        this.pointsList = new FunctionPoint[values.length];
        double interval = (rightX - leftX) / (values.length - 1);
        for (int i = 0; i < values.length; i++) {
            this.pointsList[i] = new FunctionPoint(leftX + i * interval, values[i]);
        }
        this.pointsNumber = values.length;
    }

    public ArrayTabulatedFunction(FunctionPoint[] pointsList) {
        if (pointsList.length < 2) {
            throw new IllegalArgumentException("[!] Ошибка! Точек должно быть больше или равно двух.");
        }
        if (!isSortedByX(pointsList)) {
            throw new IllegalArgumentException("[!] Ошибка! Точки должны быть отсортированы по X.");
        }
        this.pointsList = pointsList;
        this.pointsNumber = pointsList.length;
    }

    private boolean isSortedByX(FunctionPoint[] pointsList) {
        for (int i = 0; i < pointsList.length - 1; i++) {
            if (pointsList[i].getX() > pointsList[i + 1].getX()) {
                return false;
            }
        }
        return true;
    }

    public double getLeftDomainBorder() {
        //Возвращает координату X левой границы.
        return this.pointsList[0].getX();
    }

    public double getRightDomainBorder() {
        //Возвращает координату Х правой границы.
        return this.pointsList[this.getPointsCount() - 1].getX();
    }


    private boolean isInsideBorder(double x) {
        //Проверка на принадлежности точки по Х  в заданном промежутке
        return (x >= this.getLeftDomainBorder() && x <= this.getRightDomainBorder());
    }

    private double linearInterpolation(double x, int pointNumber) {
        //Линейная интерполяция для определения значения точек, лежащих между другими точками
        /*
             y2 - y1 = k(x2 - x1) + b
             k = (y2 - y1) / (x2 - x1)
             b = y1 - kx
        */
        double k = (this.pointsList[pointNumber].getY() - this.pointsList[pointNumber - 1].getY()) / (this.pointsList[pointNumber].getX() - this.pointsList[pointNumber - 1].getX());
        double b = this.pointsList[pointNumber].getY() - k * this.pointsList[pointNumber].getX();
        return k * x + b;
    }

    private int[] binarySearch(double x) {
        //Алгоритм бинарного поиска. Ищет индекс точки, в которую будем ее вставлять
        //Возвращаем значение в зависимости от метки 0 или 1.
        //Если метка 1, то элемент есть и возвращаем его индекс.
        //Если метка 0, то элемента нет и возвращаем его левого соседа.
        int[] results = new int[2];
        int left = 0;
        int right = getPointsCount() - 1;
        while (left <= right) {
            int middle = (left + right) / 2;
            double guess = this.pointsList[middle].getX();
            if (guess == x) {
                results[0] = middle;
                results[1] = 1;
                return results;
            }
            if (guess < x) {
                left = middle + 1;
            }
            if (guess > x) {
                right = middle - 1;
            }
        }
        results[0] = left;
        results[1] = 0;
        return results;
    }

    public double getFunctionValue(double x) {
        //Вычисляет и возвращает значение функции по заданной координате X с использованием линейной интерполяции.
        if (!isInsideBorder(x)) {
            return Double.NaN;
        }
        int[] result = binarySearch(x);
        if (result[1] == 1) {
            return this.pointsList[result[0]].getY();
        } else {
            return linearInterpolation(x, result[0]);
        }
    }


    public int getPointsCount() {
        //Возвращает число точек в функции
        return this.pointsNumber;
    }

    public FunctionPoint getPoint(int i) {
        //Возвращает точку по индексу
        if (!isCorrectIndex(i)) {
            throw new FunctionPointIndexOutOfBoundsException(i);
        }
        return this.pointsList[i];
    }

    private boolean isInCorrectSortedPlace(FunctionPoint point, int index) {
        //Проверка на недопустимую точку
        return (point.getX() < this.pointsList[index - 1].getX() || point.getX() > this.pointsList[index + 1].getX());
    }

    private boolean isCorrectIndex(int index) {
        //Проверка на корректность индекса
        return (index >= 0 && index < this.pointsNumber);
    }

    public void setPoint(int index, FunctionPoint point) throws FunctionPointIndexOutOfBoundsException,
            InappropriateFunctionPointException {
        //Устанавливает точку по индексу
        if (!isCorrectIndex(index)) {
            throw new FunctionPointIndexOutOfBoundsException(index);
        }
        if (isInCorrectSortedPlace(point, index)) {
            throw new InappropriateFunctionPointException(index);
        }
        this.pointsList[index] = point;
    }

    public double getPointX(int index) {
        //Возвращает координату Х точки по индексу
        if (!isCorrectIndex(index)) {
            throw new FunctionPointIndexOutOfBoundsException(index);
        }
        return this.pointsList[index].getX();
    }

    public void setPointX(int index, double x) throws FunctionPointIndexOutOfBoundsException,
            InappropriateFunctionPointException {
        // Устанавливает координату Х точки по индексу
        if (!isCorrectIndex(index)) {
            throw new FunctionPointIndexOutOfBoundsException(index);
        }
        if (isInCorrectSortedPlace(this.pointsList[index], index)) {
            throw new InappropriateFunctionPointException(index);
        }
        this.pointsList[index].setX(x);
    }

    public double getPointY(int index) {
        //Возвращает координату Y точки по индексу
        if (!isCorrectIndex(index)) {
            throw new FunctionPointIndexOutOfBoundsException(index);
        }
        return this.pointsList[index].getY();
    }

    public void setPointY(int index, double y) {
        //Устанавливает координату Y точки по индексу
        if (!isCorrectIndex(index)) {
            throw new FunctionPointIndexOutOfBoundsException(index);
        }
        this.pointsList[index].setY(y);
    }

    public void deletePoint(int index) {
        //Удаляет точку по индексу. Так же изменяет размер массива при необходимости.
        if (!isCorrectIndex(index)) {
            throw new FunctionPointIndexOutOfBoundsException(index);
        } else if (this.pointsNumber < 3) {
            throw new IllegalStateException("[!] Ошибка! Точек и так меньше трех.... Куда меньше??");
        } else if (index >= 0 && index < getPointsCount()) {
            System.arraycopy(this.pointsList, index + 1, this.pointsList, index, this.getPointsCount() - index);
            this.pointsNumber--;
        }
        if (getPointsCount() < this.pointsList.length / 2) {
            decreaseCapacity();
        }
    }


    private void increaseCapacity() {
        //Увеличивает размер массива в два раза
        int newSize = getPointsCount() * 2;
        FunctionPoint[] newPointsList = new FunctionPoint[newSize];
        System.arraycopy(this.pointsList, 0, newPointsList, 0, getPointsCount());
        this.pointsList = newPointsList;
    }

    private void decreaseCapacity() {
        //Создает массив по количеству точек в массиве.
        FunctionPoint[] newPointsList = new FunctionPoint[getPointsCount()];
        System.arraycopy(this.pointsList, 0, newPointsList, 0, getPointsCount());
        this.pointsList = newPointsList;

    }

    private int findIndex(double x) {
        //Алгоритм бинарного поиска. Ищет индекс точки, в которую будем ее вставлять
        int left = 0;
        int right = getPointsCount() - 1;
        while (left <= right) {
            int middle = (left + right) / 2;
            double guess = this.pointsList[middle].getX();
            if (guess == x) {
                return -1;
            }
            if (guess < x) {
                left = middle + 1;
            }
            if (guess > x) {
                right = middle - 1;
            }
        }
        return left;
    }

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        //Добавление точки с сохранением сортировки по х
        int currentSize = getPointsCount() + 1;

        if (currentSize > getPointsCount()) {
            increaseCapacity();
        }
        int indexToInsert = findIndex(point.getX());

        if (indexToInsert == -1) {
            throw new InappropriateFunctionPointException("Ошибка! Попытка добавить точку с координатой Х, которая уже существует.");
        } else {
            System.arraycopy(this.pointsList, indexToInsert, this.pointsList, indexToInsert + 1, currentSize - indexToInsert);
            this.pointsList[indexToInsert] = point;
        }
        this.pointsNumber++;
    }

    @Override
    public String toString() {
        String str = "{ ";
        for (int i = 0; i < this.getPointsCount(); i++) {
            str += this.pointsList[i].toString();
            if (i < this.getPointsCount() - 1 && str.length() > 2) {
                str += ", ";
            }
        }
        str += " }";
        return str;
    }

    private boolean equalsSupCheck(TabulatedFunction f) {
        if (f.getPointsCount() != this.getPointsCount())
            return false;
        for (int i = 0; i < this.getPointsCount(); i++) {
            if (!this.getPoint(i).equals(f.getPoint(i)))
                return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (!(o instanceof TabulatedFunction)) {
            return false;
        }
        if (o instanceof ArrayTabulatedFunction f) {
            return equalsSupCheck(f);
        }
        else {
            TabulatedFunction f = (TabulatedFunction) o;
            return equalsSupCheck(f);
        }
    }

    @Override
    public int hashCode(){
        int hash = 1;
        for (int i = 0; i < this.pointsList.length; i++){
            hash = hash * 31 + this.pointsList[i].hashCode();
        }
        return hash;
    }

    @Override
    public Object clone(){
        return new ArrayTabulatedFunction(this.pointsList);
    }
}