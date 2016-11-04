import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph {
    // map of Nodes to adjacent Nodes
    private Map<Square, Set<Square>> map = new HashMap<>();
    
    public Graph(int rows, int cols) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                addNode(new Square(i, j));
            }
        }
    }
    
    public void addNode(Square newSquare) {
        map.put(newSquare, new HashSet<Square>());
    }
    
    public void addAdjacent(Square a, Square b) {
        map.get(a).add(b);
        map.get(b).add(a);
    }
    
    public void removeAdjacent(Square remove, Square removeFrom) {
        map.get(removeFrom).remove(remove);
    }
}
