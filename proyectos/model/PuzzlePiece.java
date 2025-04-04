package model;

import java.awt.image.BufferedImage;

public class PuzzlePiece {
    private final BufferedImage image;
    private final int originalPosition;
    private int currentX;
    private int currentY;
    private boolean isEmpty;
    
    public PuzzlePiece(BufferedImage image, int originalPosition, int x, int y) {
        this.image = image;
        this.originalPosition = originalPosition;
        this.currentX = x;
        this.currentY = y;
        this.isEmpty = false;
    }
    
    // Getters y setters
    public BufferedImage getImage() { return image; }
    public int getOriginalPosition() { return originalPosition; }
    public int getCurrentX() { return currentX; }
    public int getCurrentY() { return currentY; }
    public boolean isEmpty() { return isEmpty; }
    public void setCurrentX(int x) { this.currentX = x; }
    public void setCurrentY(int y) { this.currentY = y; }
    public void setEmpty(boolean empty) { this.isEmpty = empty; }
}