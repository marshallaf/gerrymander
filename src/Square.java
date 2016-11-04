
public class Square {
    public int row, col;
    
    public Square(int row, int col) {
        this.row = row;
        this.col = col;
    }
    
    public String toString() {
        return "[" + row + ", " + col + "]";
    }
    
    @Override
    public boolean equals(Object o) {
        Square other = (Square) o;
        return (row == other.row && col == other.col);
    }
}
