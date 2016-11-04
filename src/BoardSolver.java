
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Set;

public class BoardSolver {
    
    // holds the board
    public static int[] board;
    // rows and columns of the board
    public int boardRows, boardCols;
    
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
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }
    
    public Graph<Integer> buildInitialGraph() {
        Graph<Integer> initial = new Graph<>();
        for (int i = 0; i < board.length; i++) {
            initial.addNode(i);
        }
        for (int i = 0; i < board.length; i++) {
            int col = i % boardCols;
            int row = i / boardCols;
            if (col != 0) initial.addEdge(i, i-1);
            if (col != boardCols-1) initial.addEdge(i, i+1);
            if (row != 0) initial.addEdge(i, i - boardCols);
            if (row != boardRows-1) initial.addEdge(i, i + boardCols);
        }
        return initial;
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
        BoardSolver db = new BoardSolver("test3x3a.txt");
        
        Graph<Integer> initial = db.buildInitialGraph();
        BoardPermute bp = new BoardPermute();
        bp.unassigned = initial;
        bp.districtSize = 3;
        bp.minScore = 2;
        bp.board = Arrays.copyOf(board, board.length);
        bp.possibleDistricts();
        for (Set<Integer> district : bp.possibleDistricts) {
            for (int square : district) {
                System.out.print(square + " ");
            }
            System.out.println();
        }
        
        
//        long start = System.nanoTime();
//        LinkedList<BoardPermute> solutions = db.solve(5);
//        long solveTime = System.nanoTime() - start;
//        for (BoardPermute bp : solutions) {
//            System.out.println(bp);
//            System.out.println("==============");
//        }
//        System.out.println("Time to solve: " + (solveTime/1000000000.0));
    }
}
