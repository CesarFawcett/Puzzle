package model;

import java.util.List;

public class GameState {
    private boolean gameStarted;
    private boolean puzzleSolved;
    private String selectedImage;
    private List<PuzzlePiece> pieces;
    private List<PuzzlePiece> solvedPieces;
    private Difficulty difficulty;
    
    //Getters y setters
    public boolean isGameStarted() { return gameStarted; }
    public void setGameStarted(boolean gameStarted) { this.gameStarted = gameStarted; }
    public boolean isPuzzleSolved() { return puzzleSolved; }
    public void setPuzzleSolved(boolean puzzleSolved) { this.puzzleSolved = puzzleSolved; }
    public String getSelectedImage() { return selectedImage; }
    public void setSelectedImage(String selectedImage) { this.selectedImage = selectedImage; }
    public Difficulty getDifficulty() { return difficulty; }
    public void setDifficulty(Difficulty difficulty) { this.difficulty = difficulty; }
    public List<PuzzlePiece> getPieces() { return pieces; }
    public void setPieces(List<PuzzlePiece> pieces) { this.pieces = pieces; }
    public List<PuzzlePiece> getSolvedPieces() { return solvedPieces; }
    public void setSolvedPieces(List<PuzzlePiece> solvedPieces) { this.solvedPieces = solvedPieces; }
}