import functions.*;
import threads.*;
import java.util.Random;
import java.io.*;
import functions.basic.*;



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
        Thread integratorThread = new Thread(new SimpleIntegrator(task));
        generatorThread.start();
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
        String separator = "\n============================================================================\n";
        Exp exp = new Exp();
        System.out.println(separator);
        System.out.println("Интеграл экспоненты:");
        System.out.println(Functions.integral(exp, 0, 1, 0.000000000000001));
        System.out.println(separator);
        System.out.println("nonThread()");

        nonThread();

        System.out.println(separator);
        System.out.println("simpleThreads()");

        simpleThreads();

        System.out.println(separator);
        System.out.println("complicatedThreads()");

        complicatedThreads();

        System.out.println(separator);
    }
}