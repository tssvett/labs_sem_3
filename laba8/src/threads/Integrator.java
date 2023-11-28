package threads;
import functions.*;

public class Integrator extends Thread {
    Task task;
    Semaphore semaphore;
    boolean flag = true;

    public Integrator(Task task, Semaphore semaphore) {
        this.task = task;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
            for (int i = 0; i < this.task.getTasksCount() && flag; i++) {
                if (this.task.function == null) {
                    continue;
                }
                try {
                    semaphore.beginRead();
                    double res = Functions.integral(this.task.function, this.task.leftX, this.task.rightX, this.task.step);
                    System.out.println("   Result <" + this.task.leftX + "> <" + this.task.rightX + "> <" + this.task.step + "> <" + res + ">");
                    semaphore.endRead();
                } catch (Exception ex) {
                    System.out.println("Интегратор прервали и он корректно завершил свою работу");
                }
            }
    }
    public void interrupt() {
        super.interrupt();
        flag = false;
    }
}