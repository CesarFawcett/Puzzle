import model.GameState;
import view.*;
import controller.*;

import javax.swing.*;
import java.awt.BorderLayout;  // Importación añadida

public class PuzzleGameMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            //modelo
            GameState gameState = new GameState();

            //vistas
            MainMenuPanel menuPanel = new MainMenuPanel();
            GamePanel gamePanel = new GamePanel();
            ImageLoader imageLoader = new ImageLoader();

            //ventana principal
            JFrame frame = new JFrame("Juego de Rompecabezas Profesional");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 950);
            frame.setLayout(new BorderLayout());

            // Panel
            JLabel statusLabel = new JLabel("Estado: Esperando selección...", JLabel.CENTER);
            statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
            JLabel timerLabel = new JLabel("Tiempo: 0 segundos", JLabel.CENTER);
            JPanel controlPanel = new JPanel(new BorderLayout());
            controlPanel.add(menuPanel, BorderLayout.CENTER);
            controlPanel.add(timerLabel, BorderLayout.SOUTH);
            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.addTab("Menú Principal", controlPanel);
            tabbedPane.addTab("Juego", gamePanel);
            
            //ventana
            frame.add(tabbedPane, BorderLayout.CENTER);
            frame.add(statusLabel, BorderLayout.SOUTH);
            
            // controladores
            TimerController timerController = new TimerController(timerLabel);
            GameController gameController = new GameController(
                gameState, menuPanel, gamePanel, imageLoader, timerController);
            frame.setVisible(true);
        });
    }
}