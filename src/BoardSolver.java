
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class BoardSolver {
    
    // holds the board
    public static int[] board;
    // rows and columns of the board
    public static int boardRows;
    public static int boardCols;
    
    /**
     * Initializes a random M x N board, with a random ratio of red/blue.
     * 
     * @param m height of board
     * @param n width of board
     */
    public BoardSolver(int m, int n) {
        
    }
    
    /**
     * Initializes a random M x N board, with the given ratio of red/blue.
     * 
     * @param m height of board
     * @param n width of board
     * @param ratio ratio of red/blue
     */
    public BoardSolver(int m, int n, float ratio) {
        
    }
    
    /**
     * Initializes a board from the given file.
     * 
     * @param filename
     */
    public BoardSolver(String filename) {
        Path file = FileSystems.getDefault().getPath("../Gerrymander/data/", filename);
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            // get the dimensions from the first line (M N)
            String line = reader.readLine();
            int[] dim = Arrays.stream(line.split(" ")).mapToInt(s -> Integer.parseInt(s)).toArray();
            boardRows = dim[0];
            boardCols = dim[1];
            board = new int[boardRows*boardCols];
            
            // read the remaining lines into the board array
            for (int i = 0; i < boardRows; i++) {
                line = reader.readLine();
                int lineArr[] = Arrays.stream(line.split(" ")).mapToInt(s -> Integer.parseInt(s)).toArray();
                for (int j = i * boardCols, k = 0; k < boardCols; j++, k++) {
                    board[j] = lineArr[k];
                }
            }
            System.out.println("Finished reading file!");
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }
    
    public LinkedList<BoardPermute> solve(int districtSize, int minScore) {
        LinkedList<BoardPermute> permutations = new LinkedList<>();
        if (board.length % districtSize != 0) throw new IllegalArgumentException("Board must be evenly divisible by districtSize.");
        int totalDistricts = board.length/districtSize;
        permutations.add(new BoardPermute(boardRows, boardCols, districtSize, minScore, board));
        
        for (int i = 0; i < totalDistricts; i++) {
            int currPermutations = permutations.size();
            System.out.println("Round " + i + ": " + currPermutations + " permutations to process.");
            for (int j = 0; j < currPermutations; j++) {
                BoardPermute currBoard = permutations.poll();
                for (Set<Integer> district : currBoard.possibleDistricts) {
                    permutations.addLast(new BoardPermute(currBoard, district));
                }
            }
        }
        
        return permutations; 
    }    
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < board.length; i++) {
            if (i % boardCols == 0) sb.append("\n");
            sb.append(board[i] + " ");
        }
        return sb.toString();
    }
    
    public static void main(String[] args) {
        BoardSolver db = new BoardSolver("colorado-blue.txt");
        
        int districtSize = 20;
        int minScore = 10;
        long start = System.nanoTime();
        LinkedList<BoardPermute> solutions = db.solve(districtSize, minScore);
        long solveTime = System.nanoTime() - start;
        for (BoardPermute bp : solutions) {
            System.out.println(bp);
            System.out.println();
        }
        System.out.println("Time to solve: " + (solveTime/1000000000.0));

    }
}
