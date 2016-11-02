import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;

public class DistrictBoard {
    
    // holds the board
    private int[][] board;
    // rows and columns of the board
    private int boardRows, boardCols;
    
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
            boardRows = dim[0];
            boardCols = dim[1];
            board = new int[boardRows][boardCols];
            
            // read the remaining lines into the board array
            for (int i = 0; i < boardRows; i++) {
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
    public boolean[][] validMoves(int startRow, int startCol) {
        boolean[][] moves = new boolean[3][3];
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (board[startRow + i][startCol + j] < 2) moves[i+1][j+1] = true;
                else moves[i+1][j+1] = false;
            }
        }
        return moves;
    }
    
    // I think this won't work for what I need, but I'll keep it for
    // now, just in case
    public Iterable<DistrictBoard> nextBoards(int activeDistrict) {
        HashSet<DistrictBoard> nextBoards = new HashSet<>();
        // array for keeping track of the squares we've checked
        boolean[][] checked = new boolean[boardRows][boardCols];
        for (int i = 0; i < boardRows; i++) {
            for (int j = 0; j < boardCols; j++) {
                checked[i][j] = false;
            }
        }
        // iterate through the board looking for the active district
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == activeDistrict) {
                    checked[i][j] = true;
                    // iterate over the possible moves
                    for (int m = -1; m <= 1; m++) {
                        for (int n = -1; n <= 1; n++) {
                            // if move is to a square that we haven't checked
                            if (!checked[i + m][j + n]) {
                                // if square is not already assigned
                                if (board[i + m][j + n] < 2) {
                                    // create new board and add to set
                                    nextBoards.add(newBoard(i+m, j+n, activeDistrict));
                                } else checked[i + m][j + n] = true;
                            }
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public boolean equals(Object o) {
        DistrictBoard other = (DistrictBoard) o;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] != other.getSquare(i, j)) return false;
            }
        }
        return true;
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
