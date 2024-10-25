import javax.swing.*;
import java.awt.*;

public class SimulationFrame extends JFrame {

    private static final int CELL_SIZE = 20;
    private JPanel gridPanel;

    public SimulationFrame() {
        setTitle("Simulação de Incêndio");
        setSize(MainSimulation.DIMENSION * CELL_SIZE, MainSimulation.DIMENSION * CELL_SIZE + 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel legendPanel = new JPanel();
        legendPanel.setLayout(new GridLayout(4, 2)); 
        legendPanel.add(new JLabel("-", SwingConstants.CENTER));
        legendPanel.add(new JLabel("Área livre", SwingConstants.LEFT));
        
        legendPanel.add(new JLabel("T", SwingConstants.CENTER));
        legendPanel.add(new JLabel("Célula monitorada por um nó sensor ativo", SwingConstants.LEFT));
        
        legendPanel.add(new JLabel("@", SwingConstants.CENTER));
        legendPanel.add(new JLabel("Célula em chamas (fogo ativo)", SwingConstants.LEFT));
        
        legendPanel.add(new JLabel("/", SwingConstants.CENTER));
        legendPanel.add(new JLabel("Célula queimada", SwingConstants.LEFT));

        gridPanel = new JPanel(new GridLayout(MainSimulation.DIMENSION, MainSimulation.DIMENSION));
        add(legendPanel, BorderLayout.NORTH); 
        add(gridPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public void updateGrid(char[][] simulationArea) {
        gridPanel.removeAll();
        for (int row = 0; row < MainSimulation.DIMENSION; row++) {
            for (int col = 0; col < MainSimulation.DIMENSION; col++) {
                JLabel cellLabel = new JLabel();
                cellLabel.setHorizontalAlignment(SwingConstants.CENTER); 
                cellLabel.setFont(new Font("Serif", Font.PLAIN, 16)); 

                switch (simulationArea[row][col]) {
                    case 'T': 
                        cellLabel.setText("T"); 
                        cellLabel.setBackground(Color.YELLOW);
                        break;
                    case '@': 
                        cellLabel.setText("@"); 
                        cellLabel.setBackground(Color.RED); 
                        break;
                    case '/': 
                        cellLabel.setText("/");
                        cellLabel.setBackground(Color.LIGHT_GRAY);
                        break;
                    default:
                        cellLabel.setText("-");
                        cellLabel.setBackground(Color.CYAN);
                        break;
                }

                cellLabel.setOpaque(true); 
                cellLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                gridPanel.add(cellLabel);
            }
        }
        gridPanel.revalidate();
        gridPanel.repaint();
    }
}
