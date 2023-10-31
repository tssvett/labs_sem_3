import functions.Function;
import functions.Functions;
import functions.TabulatedFunction;
import functions.TabulatedFunctions;
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


        System.out.println("[+] Проверка синуса [+]");
        Sin sin = new Sin();
        for (double x = 0; x <2 * Math.PI ; x +=0.1) {
            System.out.println("sin(" + x + ") = " + sin.getFunctionValue(x));
        }


        System.out.println(separator);


        System.out.println("[+] Проверка косинуса [+]");
        Cos cos = new Cos();
        for (double x = 0; x <2 * Math.PI ; x +=0.1) {
            System.out.println("cos(" + x + ") = " + cos.getFunctionValue(x));
        }


        System.out.println(separator);


        System.out.println("[+] Табулированная функция синуса [+]");
        TabulatedFunction tSin = TabulatedFunctions.tabulate(sin, 0, 2*Math.PI, 10);
                for (double x = 0; x < 2 * Math.PI; x += 0.1) {
            System.out.println("sin(" + x + ") = " + tSin.getFunctionValue(x));
        }


        System.out.println(separator);


        System.out.println("[+] Табулированная функция синуса [+]");
               TabulatedFunction tCos = TabulatedFunctions.tabulate(cos, 0, 2*Math.PI, 10);
        for (double x = 0; x< 2 * Math.PI; x += 0.1) {
            System.out.println("cos(" + x + ") = " + tCos.getFunctionValue(x));
        }


        System.out.println(separator);


        System.out.println("[+] Основное тригонометрическое по табулированным функциям [+]");
        Function sum = Functions.sum(Functions.mult(tSin, tSin), Functions.mult(tCos, tCos));
        for (double x = 0; x < 2 * Math.PI; x += 0.1) {
            System.out.println("sin^2(" + x + ") + cos^2(" + x + ") = " + sum.getFunctionValue(x));
        }


        System.out.println(separator);


        System.out.println("[+] Если увеличивать количество точек в изначальных функциях, все как надо. [+]");
        System.out.println("[!] Верьте мне. [!]");


        System.out.println(separator);


        System.out.println("[+] Записываем Экспоненту в файл [+]");
        Exp exp = new Exp();
        TabulatedFunction tExp = TabulatedFunctions.tabulate(exp, 0, 10, 11);
        FileWriter out = new FileWriter("exp.txt");
        TabulatedFunctions.writeTabulatedFunction(tExp, out);
        out.flush();
        out.close();


        System.out.println(separator);


        System.out.println("[+] Читаем обычную Экспоненту из файла [+]");
        for (int i = 0; i < 11; i++) {
            System.out.println("x = " + i + " y =  " + exp.getFunctionValue(i));
        }


        System.out.println(separator);


        System.out.println("[+] Табулированная экспонента [+]");
        FileReader in = new FileReader("exp.txt");
        TabulatedFunction tExp1 = TabulatedFunctions.readTabulatedFunction(in);
        for (int x = 0; x < 11; x++) {
            System.out.println("x = " + x + " y =  " + tExp1.getFunctionValue(x));
        }
        in.close();


        System.out.println(separator);


        System.out.println("[+] Запись логарифма в файл [+]");
        Log ln = new Log(Math.E);
        TabulatedFunction tLn = TabulatedFunctions.tabulate(ln, 0, 10, 11);
        OutputStream output = new FileOutputStream("log.txt");
        TabulatedFunctions.outputTabulatedFunction(tLn, output);
        output.flush();
        output.close();


        System.out.println(separator);


        System.out.println("[+] Чтение логарифма из файла [+]");
        InputStream in_ln = new FileInputStream("log.txt");
        for (int i = 0; i < 11; i++)
            System.out.println("x = " + i + " y =  " + ln.getFunctionValue(i));


        System.out.println(separator);


        System.out.println("[+] Табулированный логарифм [+]");
        TabulatedFunction tLn1 = TabulatedFunctions.inputTabulatedFunction(in_ln);
        in_ln.close();
        for (int x = 0; x < 11; x++)
            System.out.println("x = " + x + " y =  " + tLn1.getFunctionValue(x));


        System.out.println(separator);


        System.out.println("[+] Композиция логарифма и экспоненты [+]");
        Log ln2 = new Log(Math.E);
        Exp exp2 = new Exp();
        Function f = Functions.composition(ln2, exp2);
        TabulatedFunction tF = TabulatedFunctions.tabulate(f, 0, 10, 11);
        for (int x = 0; x < 11; x++)
            System.out.println("x = " + x + " y =  " + tF.getFunctionValue(x));


        System.out.println(separator);


        System.out.println("[+] Сериализация в файл [+]");
        FileOutputStream output2 = new FileOutputStream("ln_exp_composition.txt");
        ObjectOutputStream serial = new ObjectOutputStream(output2);
        serial.writeObject(tF);
        serial.close();


        System.out.println(separator);

        System.out.println("[+] Десериализация из файла [+]");
        FileInputStream input2 = new FileInputStream("ln_exp_composition.txt");
        ObjectInputStream deserial = new ObjectInputStream(input2);
        TabulatedFunction tF1 = (TabulatedFunction) deserial.readObject();
        deserial.close();
        for (int x = 0; x < 11; x++)
            System.out.println("x = " + x + " y =  " + tF1.getFunctionValue(x));


        System.out.println(separator);


    }
}