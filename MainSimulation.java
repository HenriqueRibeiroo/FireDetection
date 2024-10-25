import javax.swing.*;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainSimulation {

    public static final int DIMENSION = 30;
    public static final char[][] simulationArea = new char[DIMENSION][DIMENSION];
    public static final Lock areaLock = new ReentrantLock();
    public static final Condition[][] cellConditions = new Condition[DIMENSION][DIMENSION];
    private static final Random randomizer = new Random();
    private static SimulationFrame simulationFrame;

    public static void main(String[] args) {
        setupSimulationGrid();

        SwingUtilities.invokeLater(() -> {
            simulationFrame = new SimulationFrame();
            simulationFrame.updateGrid(simulationArea);
        });

        for (int row = 0; row < DIMENSION; row++) {
            for (int col = 0; col < DIMENSION; col++) {
                if (simulationArea[row][col] == 'T') {
                    new Thread(new FireDetector(row, col)).start();
                }
            }
        }

        new FireResponseCenter();
    }

    public static void setupSimulationGrid() {
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                simulationArea[i][j] = (randomizer.nextInt(100) < 30) ? 'T' : '-';
                cellConditions[i][j] = areaLock.newCondition();
            }
        }
    }

    public static void updateDisplay() {
        if (simulationFrame != null) {
            simulationFrame.updateGrid(simulationArea);
        }
    }
}
