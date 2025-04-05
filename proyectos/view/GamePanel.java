package view;

import model.PuzzlePiece;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GamePanel extends JPanel {
    private List<PuzzlePiece> pieces;
    private int pieceWidth;
    private int pieceHeight;
    
    public GamePanel() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(800, 800));
    }
    
    public void setPieces(List<PuzzlePiece> pieces, int gridSize) {
        this.pieces = pieces;
        this.pieceWidth = getWidth() / gridSize;
        this.pieceHeight = getHeight() / gridSize;
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (pieces != null) {
            for (PuzzlePiece piece : pieces) {
                if (!piece.isEmpty()) {
                    g.drawImage(piece.getImage(), 
                               piece.getCurrentX(), 
                               piece.getCurrentY(), 
                               pieceWidth, 
                               pieceHeight, 
                               this);
                } else {
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(piece.getCurrentX(), 
                               piece.getCurrentY(), 
                               pieceWidth, 
                               pieceHeight);
                }
            }
        }
    }
    
    public int getPieceWidth() {
        return pieceWidth;
    }
    public int getPieceHeight() {
        return pieceHeight;
    }
}