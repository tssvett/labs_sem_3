package threads;
import functions.*;

public class Task {
    public Function function;
    public double leftX, rightX;
    public double step;
    private int amount;

    public Task(int amount) {
        this.amount = amount;
    }

    public Task(Function function, double leftX, double rightX, double step, int tasksCount) {
        this.function = function;
        this.leftX = leftX;
        this.rightX = rightX;
        this.step = step;
        this.amount = tasksCount;
    }

    public int getTasksCount() {
        return this.amount;
    }
}