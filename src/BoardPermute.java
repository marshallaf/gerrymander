import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Andrew Marshall
 * 
 * Holds a possible permutation of district selections.
 *
 */
public class BoardPermute {
    private int[][] possibleBoard;
    private int rows, cols;
    private boolean checked[][];
    private HashMap<Integer, Integer> districts;
    
    public BoardPermute(int m, int n) {
        districts = new HashMap<>();
        rows = m;
        cols = n;
        possibleBoard = new int[rows][cols];
        checked = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                possibleBoard[i][j] = 0;
                checked[i][j] = false;
            }
        }
    }
    
    private BoardPermute(int[][] oldBoard, HashMap<Integer, Integer> oldDistricts, Square square, int activeDistrict, int addToScore) {
        districts = new HashMap<Integer, Integer>();
        districts.putAll(oldDistricts);
        if (districts.containsKey(activeDistrict)) {
            districts.put(activeDistrict, districts.get(activeDistrict)+addToScore);
        } else {
            districts.put(activeDistrict, addToScore);
        }
        
        rows = oldBoard.length;
        cols = oldBoard[0].length;
        possibleBoard = new int[rows][cols];
        checked = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == square.row && j == square.col) {
                    possibleBoard[i][j] = activeDistrict;
                    checked[i][j] = true;
                } else {
                    possibleBoard[i][j] = oldBoard[i][j];
                    if (oldBoard[i][j] != 0) checked[i][j] = true;
                    else checked[i][j] = false;
                }
            }
        }
    }
    
    public BoardPermute copyBoardWithChange(Square square, int activeDistrict, int addToScore) {
        return new BoardPermute(possibleBoard, districts, square, activeDistrict, addToScore);
    }
    
    public ArrayList<Square> expandDistrict(int activeDistrict) {
        ArrayList<Square> moves = new ArrayList<>();
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (possibleBoard[i][j] == activeDistrict) {
                    if (i != 0 && !checked[i-1][j]) {
                        moves.add(new Square(i-1, j));
                        checked[i-1][j] = true;
                    }
                    else if (i != rows-1 && !checked[i+1][j]) {
                        moves.add(new Square(i+1, j));
                        checked[i+1][j] = true;
                    }
                    if (j != 0 && !checked[i][j-1]) {
                        moves.add(new Square(i, j-1));
                        checked[i][j-1] = true;
                    }
                    else if (j != cols-1 && !checked[i][j+1]) {
                        moves.add(new Square(i, j+1));
                        checked[i][j+1] = true;
                    }
                }
            }
        }
        
        return moves;
    }
    
    public Square newDistrict() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!checked[i][j]) return new Square(i, j);
            }
        }
        return null;
    }
    
    public int districtScore(int district) {
        if (districts.containsKey(district)) {
            return districts.get(district);
        }
        return 0;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder("\n");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sb.append(possibleBoard[i][j] + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
