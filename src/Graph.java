import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph {
    // map of Nodes to adjacent Nodes
    public Map<Square, Set<Square>> map = new HashMap<>();
    // map of assigned districts
    public Set<District> districts = new HashSet<>();
    
    public Graph(int rows, int cols) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                addNode(new Square(i, j));
            }
        }
    }
    
    public Graph(Graph toCopy) {
        for (Square key : toCopy.map.keySet()) {
            map.put(key, new HashSet<Square>());
            for (Square adj : toCopy.map.get(key)) {
                map.get(key).add(adj);
            }
        }
        
        for (District district : toCopy.districts) {
            districts.add(district);
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
    
    public void splitDistrict(Set<Square> squares) {
        Set<Square> adjacent = new HashSet<>();
        for (Square square : squares) {
            
        }
        
    }
}
