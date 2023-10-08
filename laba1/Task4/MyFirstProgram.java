class MyFirstClass {
    public static void main(String[] s) {
        MySecondClass o = new MySecondClass(5, 6);
        int i, j;
        for (i = 1; i <= 8 ; i++){
            for (j = 1; j <= 8 ; j++){
                o.set_first_field(i);
                o.set_second_field(j);
                System.out.println(o.multiply());
                System.out.println(" ");
            }
            System.out.println();
        }
    }
}

class MySecondClass {
    //имеет два приватных поля типа int;
    private int first_field;
    private int second_field;

    //конструктор, создающий объект и инициализирующий значения полей;
    public MySecondClass(int first_field, int second_field) {
        this.first_field = first_field;
        this.second_field = second_field;
    }
    //методы для получения и модификации их значений;
    void set_first_field(int value){
        this.first_field = value;
    }

    void set_second_field(int value){
        this.second_field = value;
    }
    int get_first_field(){
        return this.first_field;
    }
    int get_second_field(){
        return this.second_field;
    }
    //метод с возвращаемым типом int, реализующий над этими числами действие на
    //ваш выбор (умножение, вычитание и т.д.).
    //(Пусть будет умножение)

    int multiply() {
        return this.first_field * this.second_field;
    }


}