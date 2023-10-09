package functions;

public class TabulatedFunction {
    private FunctionPoint points_list[];
    private int point_number;

    public TabulatedFunction(double leftX, double rightX, int pointsCount){
        this.points_list = new FunctionPoint[pointsCount];
        double interval = (rightX - leftX) / (pointsCount - 1);
        for (int i = 0; i < pointsCount; i++){
            this.points_list[i] = new FunctionPoint(leftX + i * interval, 0);
        }
        this.point_number = pointsCount;
    }

    public TabulatedFunction(double leftX, double rightX, double[] values){
        this.points_list = new FunctionPoint[values.length];
        double interval = (rightX - leftX) / (values.length - 1);
        for (int i = 0; i < values.length; i++){
            this.points_list[i] = new FunctionPoint(leftX + i * interval, values[i]);
        }
        this.point_number = values.length;
    }

    public double getLeftDomainBorder(){
        return this.points_list[0].getX();
    }

    public double getRightDomainBorder(){
        return this.points_list[this.getPointsCount() - 1].getX();
    }


    private boolean isInsideBorder(double x){
        return (x >= this.getLeftDomainBorder() && x <= this.getRightDomainBorder());
    }

    private double LinearInterpolation(double x, int pointNumber){
        /*
             y2 - y1 = k(x2 - x1) + b
             k = (y2 - y1) / (x2 - x1)
             b = y1 - k
        */
        double k = (this.points_list[pointNumber].getY() - this.points_list[pointNumber+1].getY()) / (this.points_list[pointNumber].getX() - this.points_list[pointNumber + 1].getX());
        double b = this.points_list[pointNumber].getY() - k * this.points_list[pointNumber].getX();
        return k * x + b;
    }

    public double getFunctionValue(double x){
        if (!isInsideBorder(x)){
                return Double.NaN;
        }
        int i = 0;
        while(i < this.points_list.length){
            //Если точка есть в списке точек, то возвращаем ее значение
            if (this.points_list[i].getX() == x){
                return this.points_list[i].getY();
            }
            //Если точки нет в списке, то ищем ближайшую точку
            else if (x < this.points_list[i].getX()) {
                //Когда находим, перестаем искать
                break;
            }
            i++;
        }
        //Интерполируем по найденной ближайшей точке
        return LinearInterpolation(x, i);
    }

    public int getPointsCount(){
        return this.point_number;
    }

    public FunctionPoint getPoint(int i){
        return this.points_list[i];
    }

    private boolean isIncorrectPosition(FunctionPoint point, int index){
        return (point.getX() < this.points_list[index-1].getX() || point.getX() > this.points_list[index + 1].getX());
    }
    public void setPoint(int index, FunctionPoint point){
        if (isIncorrectPosition(point, index)){
            System.out.println("Недопустимая точка!");
        }
        //Пометка! может быть сначала стоит заменить на нулл
        this.points_list[index] = point;
    }

    public double getPointX(int index){
        return this.points_list[index].getX();
    }

    public void setPointX(int index, double x){
        if (isIncorrectPosition(this.points_list[index], index)){
            System.out.println("Недопустимая точка!");
        }
        this.points_list[index].setX(x);
    }

    public double getPointY(int index){
        return this.points_list[index].getY();
    }

    public void setPointY(int index, double y){
        this.points_list[index].setY(y);
    }

    public void deletePoint(int index){
        if (index >= 0 && index < getPointsCount()) {
            System.arraycopy(this.points_list, index + 1, this.points_list, index, this.getPointsCount() - index);
            this.point_number--;
        }
    }


    private void increaseCapacity(){
        int new_size = getPointsCount() * 2;
        FunctionPoint[] new_points_list = new FunctionPoint[new_size];
        System.arraycopy(this.points_list, 0, new_points_list, 0, getPointsCount());
        this.points_list = new_points_list;
    }

    private int BinarySearch(double x){
        int left = 0;
        int right = getPointsCount() - 1;
        while (left <= right){
            int middle = (left + right) / 2;
            double guess = this.points_list[middle].getX();
            if (guess == x){
                return -1;
            }
            if (guess < x){
                left = middle + 1;
            }
            if (guess > x){
                right = middle - 1;
            }
        }
        return left;
    }

    public void addPoint(FunctionPoint point){
        int current_size = getPointsCount() + 1;

        if (current_size > getPointsCount()){
            increaseCapacity();
        }
        int index_to_insert = BinarySearch(point.getX());

        if (index_to_insert == -1){
            System.out.println("Такая точка уже есть! Нельзя вставить дубликат.");
            return;
        }
        else {
            System.arraycopy(this.points_list, index_to_insert, this.points_list, index_to_insert + 1, current_size - index_to_insert);
            this.points_list[index_to_insert] = point;
        }
        this.point_number++;

    }

}
