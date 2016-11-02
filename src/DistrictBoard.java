import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class DistrictBoard {
    
    // holds the board
    private int[][] board;
    
    /**
     * Initializes a random M x N board, with a random ratio of red/blue.
     * 
     * @param m height of board
     * @param n width of board
     */
    public DistrictBoard(int m, int n) {
        
    }
    
    /**
     * Initializes a random M x N board, with the given ratio of red/blue.
     * 
     * @param m height of board
     * @param n width of board
     * @param ratio ratio of red/blue
     */
    public DistrictBoard(int m, int n, float ratio) {
        
    }
    
    /**
     * Initializes a board from the given file.
     * 
     * @param filename
     */
    public DistrictBoard(String filename) {
        Path file = FileSystems.getDefault().getPath("../Gerrymander/data/", filename);
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            // get the dimensions from the first line (M N)
            String line = reader.readLine();
            int[] dim = Arrays.stream(line.split(" ")).mapToInt(s -> Integer.parseInt(s)).toArray();
            board = new int[dim[0]][dim[1]];
            
            // read the remaining lines into the board array
            for (int i = 0; i < dim[0]; i++) {
                line = reader.readLine();
                board[i] = Arrays.stream(line.split(" ")).mapToInt(s -> Integer.parseInt(s)).toArray();
            }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }
    
    public int getSquare(int row, int col) {
        return board[row][col];
    }
    
    /**
     * Returns the valid moves from a given square.
     * 
     * @param startRow row index of start square
     * @param startCol column index of start square
     * @return 3x3 array of booleans, center representing start square, true if valid move
     */
    private boolean[][] validMoves(int startRow, int startCol) {
        boolean[][] moves = new boolean[3][3];
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (board[i][j] < 2) moves[i][j] = true;
                else moves[i][j] = false;
            }
        }
        return moves;
    }
    
    public Iterable<DistrictBoard> nextBoards(int activeDistrict) {
        
    }
    
    @Override
    public boolean equals(DistrictBoard other) {
        
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                sb.append(board[i][j] + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    
    public static void main(String[] args) {
        DistrictBoard db = new DistrictBoard("test.txt");
        System.out.println(db.toString());
    }
}
