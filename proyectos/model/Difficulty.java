package model;

public enum Difficulty {
    EASY(4, "4x4"),
    MEDIUM(6, "6x6"),
    HARD(8, "8x8"),
    EXPERT(16, "16x16");
    
    private final int size;
    private final String label;
    
    Difficulty(int size, String label) {
        this.size = size;
        this.label = label;
    }
    
    public int getSize() {
        return size;
    }
    
    @Override
    public String toString() {
        return label;
    }
}

