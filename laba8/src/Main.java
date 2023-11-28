import functions.*;
import functions.basic.*;
import threads.*;
import java.io.*;
import java.nio.channels.ScatteringByteChannel;
import java.util.Iterator;
import java.util.Random;



import java.io.*;

public class Main {

    public static void nonThread() {
        Task task = new Task(100);
        Random rand = new Random();

        try {
            for (int i = 0; i < task.getTasksCount(); ++i) {
                Log log = new Log(rand.nextInt(9) + 1);
                int leftX = rand.nextInt(100);
                int rightX = rand.nextInt(100) + 100;
                double step = (double) 1 / (rand.nextDouble(100000.) + 1.0);
                System.out.println(i + ": Source <" + leftX + "> <" + rightX + "> <" + step + ">");
                System.out.println("    Result <" + leftX + "> <" + rightX + "> <" + step + "> <" + Functions.integral(log, leftX, rightX, step) + ">");
            }
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public static void simpleThreads() throws InterruptedException{
        Task task = new Task(100);
        Thread generatorThread = new Thread(new SimpleGenerator(task));
        generatorThread.start();
        Thread integratorThread = new Thread(new SimpleIntegrator(task));
        integratorThread.start();
        Thread.sleep(50);
    }

    public static void complicatedThreads() throws InterruptedException {
        Task t = new Task(100);
        Semaphore semaphore = new Semaphore();
        Thread generator = new Generator(t, semaphore);
        generator.start();
        Thread integrator = new Integrator(t, semaphore);
        System.out.println("Generator priority = " + generator.getPriority());
        System.out.println("Integrator priority = " + integrator.getPriority());
        integrator.start();
        Thread.sleep(50);
        generator.interrupt();
        integrator.interrupt();
    }
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        double[] arr = {2, 4, 6, 8, 10, 12, 14, 16, 18, 20};
        ArrayTabulatedFunction function1 = new ArrayTabulatedFunction(1, 10, arr);
        LinkedListTabulatedFunction function2 = new LinkedListTabulatedFunction(1, 10, arr);

        System.out.println("======================================================================================");
        System.out.println("Функция массив:");

        for (FunctionPoint point: function1){
            System.out.println(point);
        }
        System.out.println("Тест итератора для массивной функции:");
        Iterator<FunctionPoint> iter1 = function1.iterator();
        try {
            System.out.println("Следующий элемент для итератора " + iter1.next());
        }
        catch (FunctionPointIndexOutOfBoundsException e){
            System.out.println(e.getMessage());
        }

        System.out.println("Функция список:\n" + function2.toString());
        try {

            for (FunctionPoint point : function2) {
                System.out.println(point);
            }
        }
        catch (FunctionPointIndexOutOfBoundsException e){
        }
        System.out.println("Итератор функции списка:");
        Iterator<FunctionPoint> iter2 = function2.iterator();
        System.out.println("Следующий элемент для итератора " + iter2.next());
        System.out.println("======================================================================================");


        System.out.println("======================================================================================");
        Function f = new Cos();
        TabulatedFunction tf;
        System.out.println("Test default constructor:");
        tf = TabulatedFunctions.tabulate(f, 0, Math.PI, 11);
        System.out.println(tf.getClass());

        System.out.println("Test Linked List factory:");
        TabulatedFunctions.setTabulatedFunctionFactory(new LinkedListTabulatedFunction.LinkedListTabulatedFunctionFactory());
        tf = TabulatedFunctions.tabulate(f, 0, Math.PI, 11);
        System.out.println(tf.getClass());
        System.out.println("Test Array factory:");
        TabulatedFunctions.setTabulatedFunctionFactory(new ArrayTabulatedFunction.ArrayTabulatedFunctionFactory());
        tf = TabulatedFunctions.tabulate(f, 0, Math.PI, 11);
        System.out.println(tf.getClass());

        System.out.println("======================================================================================");
        TabulatedFunction F;
        System.out.println("Проверка создания первым способом");
        F = TabulatedFunctions.createTabulatedFunction(ArrayTabulatedFunction.class, 0, 10 , 3);
        System.out.println(F.getClass());
        System.out.println(F);

        System.out.println("Проверка создания вторым способом");
        F = TabulatedFunctions.createTabulatedFunction(ArrayTabulatedFunction.class, 0, 10 , new double[] {0, 10});
        System.out.println(F.getClass());
        System.out.println(F);

        System.out.println("Проверка создания третьим способом");
        F = TabulatedFunctions.createTabulatedFunction(LinkedListTabulatedFunction.class, new FunctionPoint[]{
                        new FunctionPoint(0, 0),
                        new FunctionPoint(10, 10)
                }
        );
        System.out.println(F.getClass());
        System.out.println(F);

        System.out.println("Проверка создания четвертым способом");
        F = TabulatedFunctions.tabulate(LinkedListTabulatedFunction.class, new Sin(), 0, Math.PI, 11);
        System.out.println(F.getClass());
        System.out.println(F);

    }
}