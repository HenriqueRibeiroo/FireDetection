import java.util.concurrent.locks.Lock;
import java.util.Random;

public class FireDetector implements Runnable {

    private static final char[][] monitoringArea = MainSimulation.simulationArea;
    private static final Lock gridLock = MainSimulation.areaLock;
    private volatile boolean operational = true;
    private static final Random random = new Random();

    public FireDetector(int row, int col) {
    }

    @Override
    public void run() {
        while (operational) {
            gridLock.lock();
            try {
                for (int r = 0; r < monitoringArea.length; r++) {
                    for (int c = 0; c < monitoringArea[0].length; c++) {
                        if (monitoringArea[r][c] == '@') {
                            try {
                                Thread.sleep(1000); 
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                operational = false;
                            }

                            if (isAdjacentToSensorNode(r, c)) {
                                extinguishAllFires();
                            } else {
                                spreadFire(r, c);
                            }
                            MainSimulation.updateDisplay();
                        }
                    }
                }
            } finally {
                gridLock.unlock();
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                operational = false;
            }
        }
    }

    private boolean isAdjacentToSensorNode(int row, int col) {
        int[][] directions = {
            {-1, 0}, 
            {1, 0},  
            {0, -1}, 
            {0, 1}   
        };

        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            if (newRow >= 0 && newRow < monitoringArea.length &&
                newCol >= 0 && newCol < monitoringArea[0].length) {
                if (monitoringArea[newRow][newCol] == 'T') {
                    return true; 
                }
            }
        }
        return false; 
    }

    private void extinguishAllFires() {
        for (int r = 0; r < monitoringArea.length; r++) {
            for (int c = 0; c < monitoringArea[0].length; c++) {
                if (monitoringArea[r][c] == '@') {
                    monitoringArea[r][c] = '/'; 
                }
            }
        }
    }

    private void spreadFire(int row, int col) {
        int[][] directions = {
            {-1, 0}, 
            {1, 0},  
            {0, -1}, 
            {0, 1}   
        };

        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            if (newRow >= 0 && newRow < monitoringArea.length &&
                newCol >= 0 && newCol < monitoringArea[0].length) {
                if (monitoringArea[newRow][newCol] == '-') {
                    monitoringArea[newRow][newCol] = '@';
                    break;
                }
            }
        }
    }
}