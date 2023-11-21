import functions.*;
import functions.basic.Cos;
import functions.basic.Exp;
import functions.basic.Log;
import functions.basic.Sin;
import functions.meta.Power;
import functions.meta.Sum;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String separator = "\n============================================================================\n";


        System.out.println(separator);


        // y = 3x
        System.out.println("[+] Проверка метода toString() для обоих функций[+] ");
        double[] values = {3, 6, 9, 12, 15, 18, 21, 24, 27, 30};
        ArrayTabulatedFunction f1 = new ArrayTabulatedFunction(1, 10, values);
        LinkedListTabulatedFunction f2 = new LinkedListTabulatedFunction(1, 10, values);
        System.out.println(f1.toString());
        System.out.println(f2.toString());


        System.out.println(separator);

        System.out.println("[+] Проверка метода equals() для одинаковых функий [+] ");
        System.out.println("Первая функция: " + f1.toString());
        System.out.println("Вторая функция: " + f2.toString());
        System.out.println("Равны ли функции? : " +  f1.equals(f2));


        System.out.println(separator);


        System.out.println("[+] Проверка метода equals() для разных функий [+] ");
        double[] values3 = {4, 6, 9, 12, 17, 18, 21, 27, 29, 30};
        double[] values4 = {3, 6, 9, 12, 15, 18, 21, 24, 27, 30};
        ArrayTabulatedFunction f3 = new ArrayTabulatedFunction(1, 10, values3);
        LinkedListTabulatedFunction f4 = new LinkedListTabulatedFunction(1, 10, values4);
        System.out.println("Первая функция: " + f3.toString());
        System.out.println("Вторая функция: " + f4.toString());
        System.out.println("Равны ли функции? : " +  f3.equals(f4));

        System.out.println(separator);


        System.out.println("[+] Проверка метода equals() для разных функций одинаковых объектов [+] ");
        double[] values5 = {4, 6, 9, 12, 17, 18, 21, 27, 29};
        double[] values6 = {3, 6, 9, 12, 15, 18, 21, 24, 27};
        LinkedListTabulatedFunction f5 = new LinkedListTabulatedFunction(1, 10, values3);
        LinkedListTabulatedFunction f6 = new LinkedListTabulatedFunction(1, 10, values4);
        System.out.println("Первая функция: " + f5.toString());
        System.out.println("Вторая функция: " + f6.toString());
        System.out.println("Равны ли функции? : " +  f5.equals(f6));

        System.out.println(separator);


        System.out.println("[+] Проверка метода hashCode() для разных функций использованных объектов [+] ");
        System.out.println("Первая функция: " + f1.toString() + ". Ее хеш код: " + f1.hashCode());
        System.out.println("Вторая функция: " + f2.toString() + ". Ее хеш код: " + f2.hashCode());
        System.out.println("Третья функция: " + f3.toString() + ". Ее хеш код: " + f3.hashCode());
        System.out.println("Четвертая функция: " + f4.toString() + ". Ее хеш код: " + f4.hashCode());
        System.out.println("Пятая функция: " + f5.toString() + ". Ее хеш код: " + f5.hashCode());
        System.out.println("Шестая функция: " + f6.toString() + ". Ее хеш код: " + f6.hashCode());
    }
}