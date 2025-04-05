package view;

import model.Difficulty;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainMenuPanel extends JPanel {
    private JComboBox<String> imageCombo;
    private JComboBox<Difficulty> difficultyCombo;
    private JButton loadImageButton;
    private JButton startGameButton;
    
    public MainMenuPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        // Configuración
        imageCombo = new JComboBox<>(new String[]{"imag1.png", "imag2.png", "imag3.png"});
        difficultyCombo = new JComboBox<>(Difficulty.values());
        loadImageButton = new JButton("Cargar Imagen");
        startGameButton = new JButton("Iniciar Juego");
        // componentes
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Selecciona una imagen:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        add(imageCombo, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        add(loadImageButton, gbc);
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Selecciona dificultad:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        add(difficultyCombo, gbc);
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(startGameButton, gbc);
    }
    
    // Métodos
    public String getSelectedImage() {
        return (String) imageCombo.getSelectedItem();
    }
    public Difficulty getSelectedDifficulty() {
        return (Difficulty) difficultyCombo.getSelectedItem();
    }
    public void addLoadImageListener(ActionListener listener) {
        loadImageButton.addActionListener(listener);
    }
    public void addStartGameListener(ActionListener listener) {
        startGameButton.addActionListener(listener);
    }
}