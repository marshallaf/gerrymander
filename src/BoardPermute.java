import java.awt.Point;

/**
 * @author Andrew Marshall
 * 
 * Holds a possible permutation of district selections.
 *
 */
public class BoardPermute {
    private int[][] possibleBoard;
    
    public BoardPermute(int m, int n) {
        possibleBoard = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                possibleBoard[i][j] = 0;
            }
        }
    }
    
    private BoardPermute(int[][] oldBoard, Point square, int activeDistrict) {
        int m = oldBoard.length;
        int n = oldBoard[0].length;
        possibleBoard = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
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
}
