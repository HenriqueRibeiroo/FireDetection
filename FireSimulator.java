import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FireSimulator implements Runnable {
    private int row, col;
    private static final Logger log = Logger.getLogger(FireSimulator.class.getName());
    private static final char[][] monitoringArea = MainSimulation.simulationArea;
    private static final Lock areaLock = MainSimulation.areaLock;
    private static final Condition[][] areaConditions = MainSimulation.cellConditions;
    private volatile boolean active = true;

    public FireSimulator(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public void run() {
        while (active) {
            areaLock.lock();
            try {
                if (monitoringArea[row][col] == '@') {
                    System.out.printf("Simulador de fogo em (%d, %d) detectou incêndio! Notificando áreas adjacentes...\n", row, col);
                    notifyAdjacentCells(row, col, "Localização: (" + row + ", " + col + ")");
                    monitoringArea[row][col] = '/';
                    MainSimulation.updateDisplay(); 
                }
                areaConditions[row][col].await();
            } catch (InterruptedException e) {
                log.log(Level.SEVERE, "Thread foi interrompida", e);
                active = false;
            } finally {
                areaLock.unlock();
            }
        }
    }

    private void notifyAdjacentCells(int row, int col, String alertMessage) {
        int[][] directions = {
            {-1, 0}, 
            {1, 0},  
            {0, -1}, 
            {0, 1}   
        };

        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            if (newRow >= 0 && newRow < MainSimulation.simulationArea.length &&
                newCol >= 0 && newCol < MainSimulation.simulationArea[0].length) {

                MainSimulation.areaLock.lock();
                try {
                    if (MainSimulation.simulationArea[newRow][newCol] == 'T') {
                        System.out.printf("Alerta enviado para o sensor em (%d, %d): %s\n", newRow, newCol, alertMessage);
                        MainSimulation.cellConditions[newRow][newCol].signal();
                    }
                } finally {
                    MainSimulation.areaLock.unlock();
                }
            }
        }
    }
}
