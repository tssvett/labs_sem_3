package threads;
import functions.basic.Log;

import java.util.Random;

public class SimpleGenerator implements Runnable {
    private final Task task;

    public SimpleGenerator(Task task) {
        this.task = task;
    }

    public void run() {
        Random randomObj = new Random();
        synchronized (task) {
            for (int i = 0; i < this.task.getTasksCount(); ++i) {
                this.task.function = new Log(randomObj.nextInt(9) + 1);
                this.task.leftX = randomObj.nextInt(100);
                this.task.rightX = randomObj.nextInt(100) + 100;
                this.task.step = (double) 1 / (randomObj.nextDouble(1000000.) + 1.0);
                System.out.println(i + ": Source <" + this.task.leftX + "> <" + this.task.rightX + "> <" + this.task.step + ">");
            }
        }
    }
}