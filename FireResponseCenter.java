import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class FireResponseCenter implements Runnable {

    private static final char[][] simulationArea = MainSimulation.simulationArea;
    private static final Lock areaLock = MainSimulation.areaLock;
    private static final Condition[][] cellConditions = MainSimulation.cellConditions;
    private static final Random rng = new Random();

    public FireResponseCenter() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this, 0, 3, TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        int row = rng.nextInt(simulationArea.length);
        int col = rng.nextInt(simulationArea[0].length);

        areaLock.lock();
        try {
            if (simulationArea[row][col] == '-') {
                simulationArea[row][col] = '@';
                cellConditions[row][col].signal();
                MainSimulation.updateDisplay();
            }
        } finally {
            areaLock.unlock();
        }
    }
}
