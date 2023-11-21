package functions;

import java.io.Serializable;

public class FunctionPoint implements Serializable {
    private double x;
    private double y;

    public FunctionPoint(double x, double y){
        //Конструктор для создания точки через координаты
        this.x = x;
        this.y = y;
    }

    public FunctionPoint(FunctionPoint point){
        //Конструктор для создания точки через другую точку
        this.x = point.x;
        this.y = point.y;
    }

    public FunctionPoint(){
        //Конструктор для создания точки с нулевыми координатами
        this.x = 0;
        this.y = 0;
    }

    public double getX(){
        //Возвращает координату Х
        return this.x;
    }

    public double getY(){
        //Возвращает координату Y
        return this.y;
    }

    public void setX(double x){
        //Устанавливает координату Х
        this.x = x;
    }

    public void setY(double y){
        //Устанавливает координату Y
        this.y = y;
    }

    @Override
    public String toString(){
        //Выводит координаты точки
        return "(" + this.x + ";" + this.y + ")";
    }

    @Override
    public boolean equals(Object o){
        //Сравнивает две точки
        return o instanceof FunctionPoint && this.x == ((FunctionPoint) o).getX() && this.y == ((FunctionPoint) o).getY();
    }

    @Override
    public int hashCode() {
        int result = Double.hashCode(x);
        result = 31 * result + Double.hashCode(y);
        return result;
    }

    @Override
    public Object clone(){
        return new FunctionPoint(this);
    }

}