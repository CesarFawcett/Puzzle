package controller;

import model.*;
import view.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameController {
    private final GameState gameState;
    private final MainMenuPanel menuPanel;
    private final GamePanel gamePanel;
    private final ImageLoader imageLoader;
    private final TimerController timerController;
    
    public GameController(GameState gameState, MainMenuPanel menuPanel, 
                         GamePanel gamePanel, ImageLoader imageLoader,
                         TimerController timerController) {
        this.gameState = gameState;
        this.menuPanel = menuPanel;
        this.gamePanel = gamePanel;
        this.imageLoader = imageLoader;
        this.timerController = timerController;
        
        setupListeners();
    }
    
    private void setupListeners() {
        menuPanel.addLoadImageListener(e -> loadImage());
        menuPanel.addStartGameListener(e -> startGame());
        
        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (gameState.isGameStarted() && !gameState.isPuzzleSolved()) {
                    handlePieceClick(e.getX(), e.getY());
                }
            }
        });
    }
    
    private void loadImage() {
        try {
            String imagePath = menuPanel.getSelectedImage();
            BufferedImage image = imageLoader.loadImage(imagePath);
            BufferedImage scaledImage = imageLoader.scaleImage(image, 800, 800);
            
            gameState.setSelectedImage(imagePath);
            JOptionPane.showMessageDialog(null, "Imagen cargada correctamente", 
                                         "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar la imagen: " + ex.getMessage(), 
                                         "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void startGame() {
        if (gameState.getSelectedImage() == null) {
            JOptionPane.showMessageDialog(null, "Primero carga una imagen", 
                                         "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        gameState.setDifficulty(menuPanel.getSelectedDifficulty());
        preparePuzzle();
        showSolvedPuzzle();
    }
    
    private void preparePuzzle() {
        try {
            BufferedImage image = imageLoader.loadImage(gameState.getSelectedImage());
            BufferedImage scaledImage = imageLoader.scaleImage(image, 800, 800);
            
            int gridSize = gameState.getDifficulty().getSize();
            int pieceWidth = scaledImage.getWidth() / gridSize;
            int pieceHeight = scaledImage.getHeight() / gridSize;
            
            List<PuzzlePiece> pieces = new ArrayList<>();
            List<PuzzlePiece> solvedPieces = new ArrayList<>();
            
            for (int y = 0; y < gridSize; y++) {
                for (int x = 0; x < gridSize; x++) {
                    BufferedImage subImage = scaledImage.getSubimage(
                        x * pieceWidth, y * pieceHeight, pieceWidth, pieceHeight);
                    
                    PuzzlePiece piece = new PuzzlePiece(
                        subImage, x + y * gridSize, x * pieceWidth, y * pieceHeight);
                    
                    pieces.add(piece);
                    solvedPieces.add(piece);
                }
            }
            
            // La última pieza es el espacio vacío
            PuzzlePiece emptyPiece = pieces.get(pieces.size() - 1);
            emptyPiece.setEmpty(true);
            
            gameState.setPieces(pieces);
            gameState.setSolvedPieces(solvedPieces);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al preparar el puzzle: " + ex.getMessage(), 
                                         "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void showSolvedPuzzle() {
        gamePanel.setPieces(gameState.getPieces(), gameState.getDifficulty().getSize());
        
        Timer timer = new Timer(2000, e -> {
            scramblePuzzle();
            gameState.setGameStarted(true);
            timerController.startTimer();
        });
        timer.setRepeats(false);
        timer.start();
    }
    
    private void scramblePuzzle() {
        List<PuzzlePiece> pieces = gameState.getPieces();
        int gridSize = gameState.getDifficulty().getSize();
        
        for (int i = 0; i < 1000; i++) {
            PuzzlePiece emptyPiece = findEmptyPiece(pieces);
            List<PuzzlePiece> adjacentPieces = getAdjacentPieces(pieces, emptyPiece, gridSize);
            
            if (!adjacentPieces.isEmpty()) {
                PuzzlePiece randomPiece = adjacentPieces.get((int)(Math.random() * adjacentPieces.size()));
                swapPieces(randomPiece, emptyPiece);
            }
        }
        
        gamePanel.setPieces(pieces, gridSize);
    }
    
    private void handlePieceClick(int x, int y) {
        List<PuzzlePiece> pieces = gameState.getPieces();
        int gridSize = gameState.getDifficulty().getSize();
        int pieceWidth = gamePanel.getPieceWidth();
        int pieceHeight = gamePanel.getPieceHeight();
        
        for (PuzzlePiece piece : pieces) {
            if (!piece.isEmpty() && 
                x >= piece.getCurrentX() && x <= piece.getCurrentX() + pieceWidth &&
                y >= piece.getCurrentY() && y <= piece.getCurrentY() + pieceHeight) {
                
                PuzzlePiece emptyPiece = findEmptyPiece(pieces);
                if (isAdjacent(pieces, piece, emptyPiece, gridSize)) {
                    swapPieces(piece, emptyPiece);
                    gamePanel.setPieces(pieces, gridSize);
                    
                    if (checkSolved()) {
                        gameState.setPuzzleSolved(true);
                        gameState.setGameStarted(false);
                        timerController.stopTimer();
                        
                        int score = timerController.getSeconds();
                        JOptionPane.showMessageDialog(null, 
                            "¡Felicidades! Resolviste el puzzle en " + score + " segundos.");
                        saveScore(score);
                    }
                }
                break;
            }
        }
    }
    
    private PuzzlePiece findEmptyPiece(List<PuzzlePiece> pieces) {
        for (PuzzlePiece piece : pieces) {
            if (piece.isEmpty()) {
                return piece;
            }
        }
        return null;
    }
    
    private List<PuzzlePiece> getAdjacentPieces(List<PuzzlePiece> pieces, PuzzlePiece emptyPiece, int gridSize) {
        List<PuzzlePiece> adjacent = new ArrayList<>();
        int emptyIndex = pieces.indexOf(emptyPiece);
        int emptyRow = emptyIndex / gridSize;
        int emptyCol = emptyIndex % gridSize;
        
        // Arriba
        if (emptyRow > 0) adjacent.add(pieces.get((emptyRow - 1) * gridSize + emptyCol));
        // Abajo
        if (emptyRow < gridSize - 1) adjacent.add(pieces.get((emptyRow + 1) * gridSize + emptyCol));
        // Izquierda
        if (emptyCol > 0) adjacent.add(pieces.get(emptyRow * gridSize + (emptyCol - 1)));
        // Derecha
        if (emptyCol < gridSize - 1) adjacent.add(pieces.get(emptyRow * gridSize + (emptyCol + 1)));
        
        return adjacent;
    }
    
    private boolean isAdjacent(List<PuzzlePiece> pieces, PuzzlePiece p1, PuzzlePiece p2, int gridSize) {
        int index1 = pieces.indexOf(p1);
        int index2 = pieces.indexOf(p2);
        int row1 = index1 / gridSize;
        int col1 = index1 % gridSize;
        int row2 = index2 / gridSize;
        int col2 = index2 % gridSize;
        
        return (Math.abs(row1 - row2) == 1 && col1 == col2) || 
               (Math.abs(col1 - col2) == 1 && row1 == row2);
    }
    
    private void swapPieces(PuzzlePiece p1, PuzzlePiece p2) {
        int tempX = p1.getCurrentX();
        int tempY = p1.getCurrentY();
        p1.setCurrentX(p2.getCurrentX());
        p1.setCurrentY(p2.getCurrentY());
        p2.setCurrentX(tempX);
        p2.setCurrentY(tempY);
        
        Collections.swap(gameState.getPieces(), gameState.getPieces().indexOf(p1), gameState.getPieces().indexOf(p2));
    }
    
    private boolean checkSolved() {
        List<PuzzlePiece> pieces = gameState.getPieces();
        List<PuzzlePiece> solvedPieces = gameState.getSolvedPieces();
        
        for (int i = 0; i < pieces.size(); i++) {
            if (pieces.get(i).getOriginalPosition() != solvedPieces.get(i).getOriginalPosition()) {
                return false;
            }
        }
        return true;
    }
    
    private void saveScore(int score) {
        try (java.io.PrintWriter out = new java.io.PrintWriter(new java.io.FileWriter("puntuaciones.txt", true))) {
            out.println("Imagen: " + gameState.getSelectedImage() + 
                       ", Dificultad: " + gameState.getDifficulty() + 
                       ", Tiempo: " + score + " segundos");
        } catch (java.io.IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar la puntuación: " + ex.getMessage(), 
                                         "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}