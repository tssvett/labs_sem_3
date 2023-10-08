package myfirstpackage;
public class MySecondClass {
    //имеет два приватных поля типа int;
    private int first_field;
    private int second_field;

    //конструктор, создающий объект и инициализирующий значения полей;
    public MySecondClass(int first_field, int second_field) {
        this.first_field = first_field;
        this.second_field = second_field;
    }
    //методы для получения и модификации их значений;
    public void set_first_field(int value){
        this.first_field = value;
    }

    public void set_second_field(int value){
        this.second_field = value;
    }
    public int get_first_field(){
        return this.first_field;
    }
    public int get_second_field(){
        return this.second_field;
    }
    //метод с возвращаемым типом int, реализующий над этими числами действие на
    //ваш выбор (умножение, вычитание и т.д.).
    //(Пусть будет умножение)

    public int multiply() {
        return this.first_field * this.second_field;
    }

}