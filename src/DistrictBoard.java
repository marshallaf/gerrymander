
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;

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
    
    public LinkedList<BoardPermute> solve(int districtSize) {
        // calculate the number of votes to win a district
        double votesToWin = districtSize / 2.0;
        
        int district = 0;
        LinkedList<BoardPermute> possBoards = new LinkedList<BoardPermute>();
        possBoards.add(new BoardPermute(boardRows, boardCols));
        
        // for each unassigned square
        int unassigned = boardRows*boardCols;
        for (int i = 0; i < unassigned; i++) {
            int currPossBoards = possBoards.size();
            int assignedToDistrict = i % districtSize;
            if (assignedToDistrict == 0) district++;
            for (int j = 0; j < currPossBoards; j++) {
                BoardPermute possBoard = possBoards.poll();
                // a new district needs to start
                if (assignedToDistrict == 0) {
                    Square move = possBoard.newDistrict();
                    possBoards.add(possBoard.copyBoardWithChange(move, district, getSquare(move)));
                }
                // or an old district needs to expand
                else {
                    ArrayList<Square> moves = possBoard.expandDistrict(district);
                    for (Square move : moves) {
                        // check if move would invalidate the district
                        int newScore = possBoard.districtScore(district) + getSquare(move);
                        int otherVotes = (assignedToDistrict+1) - newScore;
                        if (newScore == 0 || otherVotes < votesToWin) possBoards.add(possBoard.copyBoardWithChange(move, district, getSquare(move)));
                    }
                }
            }
            
        }
        
        return possBoards;
    }
    
    public int getSquare(Square p) {
        return board[(int) p.row][(int) p.col];
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
        DistrictBoard db = new DistrictBoard("test3x3a.txt");
        LinkedList<BoardPermute> solutions = db.solve(3);
        for (BoardPermute bp : solutions) {
            System.out.println(bp);
            System.out.println("==============");
        }
    }
}
