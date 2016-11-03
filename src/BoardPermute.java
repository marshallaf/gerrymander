import java.awt.Point;
import java.util.ArrayList;

/**
 * @author Andrew Marshall
 * 
 * Holds a possible permutation of district selections.
 *
 */
public class BoardPermute {
    private int[][] possibleBoard;
    private int rows, cols;
    
    public BoardPermute(int m, int n) {
        rows = m;
        cols = n;
        possibleBoard = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                possibleBoard[i][j] = 0;
            }
        }
    }
    
    private BoardPermute(int[][] oldBoard, Point square, int activeDistrict) {
        rows = oldBoard.length;
        cols = oldBoard[0].length;
        possibleBoard = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == square.getX() && j == square.getY()) {
                    possibleBoard[i][j] = activeDistrict;
                } else {
                    possibleBoard[i][j] = oldBoard[i][j];
                }
            }
        }
    }
    
    public BoardPermute copyBoardWithChange(Point square, int activeDistrict) {
        return new BoardPermute(possibleBoard, square, activeDistrict);
    }
    
    public ArrayList<Point> nextMoves(int activeDistrict) {
        
    }
}
