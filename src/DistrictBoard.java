import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class DistrictBoard {
    
    // holds the board
    private byte[][] board;
    
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
        //System.out.println(file.toString());
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String line = reader.readLine();
            Integer[] dim = Arrays.stream(line.split(" ")).map(s -> Integer.parseInt(s)).toArray(Integer[]::new);
            board = new byte[dim[0]][dim[1]];
//            System.out.println("Should be 5: " + board.length);
//            System.out.println("Should be 6: " + board[0].length);
            
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }
    
    public static void main(String[] args) {
        DistrictBoard db = new DistrictBoard("test.txt");
    }
}
