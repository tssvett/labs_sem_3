import myfirstpackage.*;
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
